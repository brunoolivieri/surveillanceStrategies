package projects.UAV_Surveillance.nodes.nodeImplementations;

import org.chocosolver.solver.ResolutionPolicy;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.ICF;
import org.chocosolver.solver.search.loop.monitors.SMF;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VF;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import projects.UAV_Surveillance.nodes.messages.msgFromPOI;
//import projects.UAV_Surveillance.TSP_BB_mono.TSP;
//import projects.UAV_Surveillance.TSPtools.TabuSearch;
import projects.UAV_Surveillance.nodes.messages.msgPOIordered;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.io.eps.EPSOutputPrintStream;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;
import sinalgo.runtime.Global;
import sinalgo.runtime.Runtime;
import sinalgo.tools.Tools;
import sinalgo.tools.logging.Logging;



public class GSnode extends Node implements Comparable<GSnode> {

	//@Oli: our vars
	private ArrayList<POInode> listOfPOIs = new ArrayList<POInode>();
	private ArrayList<POInode> listOfPOIsTSP = new ArrayList<POInode>();

	private TreeSet<UAVnode> setOfUAVs = new TreeSet<UAVnode>();
	
	private msgPOIordered msgPOIorder;
	private boolean cmdsSent = false;
	private boolean mappedPOIs = false;

	// TSP
	//private ArrayList<Integer> bestTour = new ArrayList<Integer>();
	private int bestCost = 999999999;
	private IntVar[] VS_bestTour = null; //VF.enumeratedArray("VS", listOfPOIs.size(), 0, listOfPOIs.size()-1, solvers.get(1));
	
	//Logging uav_log = Logging.getLogger("UAV_GS_id_" + this.ID + ".txt");

	public double distToGS = 0;
	public boolean init =false;
	
	public int roundsRunning = 0;
	public long startPathProcessingTime = 0; 
	public long totalPathProcessingTime = 0; 

	
	public int globalAvgDelay = 0 ;
	public ArrayList<Long> msgDelays = new ArrayList<Long>();	
	private ArrayList<msgFromPOI> poiMessages = new ArrayList<msgFromPOI>();

	public String strategyRunning;
	
	public BufferedImage img = null; // to draw minivan

	
	public void reset() {
	
	}
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void handleMessages(Inbox inbox) {
		
		//@Oli: msg with path arrived.
		while(inbox.hasNext()) {
			Message msg = inbox.next();

			// saving msg from POIs
			if(msg instanceof msgFromPOI) {		
				msgFromPOI dataReceived = (msgFromPOI)msg;
				if (dataReceived.recipient == this.ID) {  // because POI sends a broadcast
					
					poiMessages.add((msgFromPOI) dataReceived.clone());		
					
					long delay = this.roundsRunning - poiMessages.get(poiMessages.size()-1).timeStamp; //dataReceived.timeStamp;
					
					if (delay<=0) {
						System.out.println("\n\n\n\n[GS] ERROR IN MSG DELAY\n\n\n\n\n");
					} else {
						//System.out.print(":"+ delay +":");
						msgDelays.add(delay);  // TO SAVE ALL DELAYS
						
						//simpler avg:
						globalAvgDelay = (int) ((globalAvgDelay + delay) / 2) ;
					}
				}				
			}
		}
					
	}

	@Override
	public void init() {
		//@Oli: Starting stuff.
		this.setPosition(5.0, 5.0, 0.0);
		
		if (!init){
			distToGS = (int) Math.sqrt(
		            (this.getPosition().xCoord - 0) *  (this.getPosition().xCoord - 0) + 
		            (this.getPosition().yCoord - 0) *  (this.getPosition().yCoord - 0)
		        );
			init = true;
			System.out.println("[GS " + this.ID + "] dist to zero = " + this.distToGS);
			
			
			//super.init();
			try {
				InputStream in = null;
				in = new FileInputStream("src/" + Configuration.userProjectDir
						+ "/" + Global.projectName + "/" + "images/smallMinivan.png");
				if ((img = ImageIO.read(in)) == null) {
					throw new FileNotFoundException(
							"\n 'map.bmp' - This image format is not supported.");
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		

	}

	@Override
	public void neighborhoodChange() {
		
	}

	
	
	
	@Override
	public void preStep() {
		roundsRunning++;		

		
		//@Oli: we can not do it on Init() because does not works... BUG?
		if (!mappedPOIs){
			for(Node n : Runtime.nodes) {	
				if (n instanceof POInode){
					listOfPOIs.add((POInode) n);
				}
				if (n instanceof UAVnode){
					setOfUAVs.add((UAVnode) n);
				}
				if (n instanceof GSnode) {
					// bellow
				}		
			}					
			// if (V2V instead of V2I) // so, UAVs need to go to GS to return data
			// this is a kind of phantom, but set as phantom. Why? <-- just added
			if (Global.isV2V2GS){
				POInode gsPOI = new POInode();
				gsPOI.setPosition(this.getPosition());
				gsPOI.ID = this.ID; // this is necessary because is a fake POI | and it is used to (re)mount TSP best tour
				//gsPOI.amIphantom = true;
				listOfPOIs.add((POInode) gsPOI); 
			}
			mappedPOIs = true;			
			
			// Choose your flavor ... z does not matter. sort() helps debug operations
			Collections.sort(listOfPOIs); 
			//Collections.shuffle(listOfPOIs);
			
			System.out.print("[GSnode] Default route: ");
			for (POInode n: listOfPOIs){
				System.out.print(n.ID + " - ");
			}
			System.out.println("\n");
		}	
		
		if (!cmdsSent)
			prepareMessageWithPathToUAVs(); // prepare and send the msg
		
		
	
	}
	
	
	private void prepareMessageWithPathToUAVs(){
		
		System.out.println("[GS " + this.ID + "] invoked to dispatch process paths/tours and dispatch UAVs" );

		startPathProcessingTime = System.currentTimeMillis();  

		boolean needCompensateExternalProcessingTime = false; // price for external PID generation
		
		// Sent once to inform UAVs the visit order... Naive, TSP & Anti-TSP cases
		// Random Safe Strategy does not wait for this step, because does not have an order
				
		if ((!cmdsSent) && (!setOfUAVs.first().myMobilityModelName.endsWith("RandomSafeMobility"))){		
			if (setOfUAVs.first().myMobilityModelName.endsWith("TSPbasedMobility")){
				System.out.println("[TSPbasedMobility] ");
				msgPOIorder = new msgPOIordered(createTSPbasedPaths(),0);		//@Oli: ChocoSolver to TSP		
				strategyRunning = "TSPbased";
			}
			else if ((setOfUAVs.first().myMobilityModelName.endsWith("NotSoNaiveOrderedMobility"))){			
					System.out.println("[NotSoNaiveOrderedMobility] ");
					// Does O(n2) path path and populates "msgPOIorder"	
					msgPOIorder = new msgPOIordered(createNotSoNaiveBestPath(),0);
					strategyRunning = "NSNOrdered";
					needCompensateExternalProcessingTime = true;
			} 
			else if ((setOfUAVs.first().myMobilityModelName.endsWith("ZigZagOverNaiveMobility"))){
					System.out.println("[ZigZagOverNaiveMobility] ");
					msgPOIorder = new msgPOIordered(listOfPOIs,0);
					strategyRunning = "DADCA_NAIVE";
					needCompensateExternalProcessingTime = true;
			} 
			else if ((setOfUAVs.first().myMobilityModelName.endsWith("NaiveOrderedMobility"))&&(!setOfUAVs.first().myMobilityModelName.endsWith("NotSoNaiveOrderedMobility"))){				
					System.out.println("[NaiveOrderedMobility] ");
					msgPOIorder = new msgPOIordered(listOfPOIs,0);
					strategyRunning = "Naive";
					needCompensateExternalProcessingTime = true;

			} 
			else if ((setOfUAVs.first().myMobilityModelName.endsWith("ZigZagOverNSNMobility"))){
					System.out.println("[ZigZagOverNSNMobility] ");
					msgPOIorder = new msgPOIordered(createNotSoNaiveBestPath(),0);// same as NotSoNaiveOrderedMobility
					strategyRunning = "DADCA-NSN";
					needCompensateExternalProcessingTime = true;

			} 
			else if ((setOfUAVs.first().myMobilityModelName.endsWith("KingstonImprovedOverNSNMobility"))){
					System.out.println("[KingstonImprovedOverNSNMobility] ");
					msgPOIorder = new msgPOIordered(createNotSoNaiveBestPath(),0);// same as NotSoNaiveOrderedMobility
					strategyRunning = "KIMP-NSN";
					needCompensateExternalProcessingTime = true;

			} 
			else if ((setOfUAVs.first().myMobilityModelName.endsWith("KingstonImprovedOverNaiveMobility"))){
					System.out.print("[KingstonImprovedOverNaiveMobility] ");
					msgPOIorder = new msgPOIordered(listOfPOIs,0);
					strategyRunning = "KIMP-Naive";
					needCompensateExternalProcessingTime = true;

			}							
			else if (((setOfUAVs.first().myMobilityModelName.endsWith("FPPWRMobility")))) {
				System.out.println("[FPPWRMobility] ");
				strategyRunning = "FPPWR";
				fppwrPlanner planner = new fppwrPlanner();
				msgPOIorder = new msgPOIordered(planner.fppwrPlathPlanner(listOfPOIs),0);	
				needCompensateExternalProcessingTime = true;

			}
			else if (((setOfUAVs.first().myMobilityModelName.endsWith("TSPConcordeMobility")))) {
				System.out.println("[TSPConcordeMobility] ");
				strategyRunning = "TSPConcorde";
				ConcordePlanner planner = new ConcordePlanner();
				msgPOIorder = new msgPOIordered(planner.getTSPsolution(listOfPOIs),0);	
			}
			else if (((setOfUAVs.first().myMobilityModelName.endsWith("ZigZagPartedOverNaiveMobility")))) {
				System.out.println("[ZigZagPartedOverNaiveMobility] ");
				strategyRunning = "DADCA-parted-naive";
				DadcaPartedPlanner planner = new DadcaPartedPlanner(this.ID, listOfPOIs);
				ArrayList<POInode> solution = new ArrayList<POInode>();
				solution = planner.getDadcaPartedNaiveSolution();					
				//msgPOIorder = new msgPOIordered(solution, getIdxFromPoiByID(this.ID, solution)+1);	
				msgPOIorder = new msgPOIordered(solution, 0);	
				needCompensateExternalProcessingTime = true;

			}			
			else if (((setOfUAVs.first().myMobilityModelName.endsWith("ZigZagPartedOverNSNMobility")))) {
				System.out.println("[ZigZagPartedOverNSNMobility] ");
				strategyRunning = "DADCA-parted-nsn";
				DadcaPartedPlanner planner = new DadcaPartedPlanner(this.ID, listOfPOIs);
				ArrayList<POInode> solution = new ArrayList<POInode>();
				solution = planner.getDadcaPartedNSNSolution();					
				//msgPOIorder = new msgPOIordered(solution, getIdxFromPoiByID(this.ID, solution)+1);	
				msgPOIorder = new msgPOIordered(solution, 0);	
				needCompensateExternalProcessingTime = true;
			}
			
			else if (((setOfUAVs.first().myMobilityModelName.endsWith("ZigZagOverLKHMobility")))) {
				System.out.println("[ZigZagOverLKHMobility] ");
				strategyRunning = "DADCA-lkh";
				DadcaLKHPlanner planner = new DadcaLKHPlanner(listOfPOIs);
				ArrayList<POInode> solution = new ArrayList<POInode>();
				solution = planner.getDadcaLKHSolution();					
				//msgPOIorder = new msgPOIordered(solution, getIdxFromPoiByID(this.ID, solution)+1);	
				msgPOIorder = new msgPOIordered(solution, 0);	
			}
			
			else if (((setOfUAVs.first().myMobilityModelName.endsWith("ZigZagOverLKHCuttedMobility")))) {
				System.out.println("[ZigZagOverLKH-Cutted-Mobility] ");
				strategyRunning = "DADCA-lkh-cutted";
				DadcaLKHPlanner planner = new DadcaLKHPlanner(listOfPOIs);
				ArrayList<POInode> solution = new ArrayList<POInode>();
				//solution = planner.getDadcaLKHSolution();	
				solution = cutter(planner.getDadcaLKHSolution());
				//msgPOIorder = new msgPOIordered(solution, getIdxFromPoiByID(this.ID, solution)+1);	
				msgPOIorder = new msgPOIordered(solution, 0);	
			}
			
			
			
			
			totalPathProcessingTime += System.currentTimeMillis() - startPathProcessingTime;
			broadcast(msgPOIorder);			
			Global.originalPathSize = getPathSize(msgPOIorder.data);
			cmdsSent = true;	
			
			// This must be tunned machine by machine and set of POIS to set of POIS.
			// This tries to consider the external processing time and I/O stuff
			if (needCompensateExternalProcessingTime) {
				
				try {
					totalPathProcessingTime += Configuration.getDoubleParameter("MachineExternalProcessToDesconsider/timeInMS");
				} catch (CorruptConfigurationEntryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		
			
		}
		
		
	}
	
	public ArrayList<POInode> cutter(ArrayList<POInode> list2cut){
		
		ArrayList<POInode> cutted = new ArrayList<POInode>();		
		
		int mid = list2cut.size() / 2;
		
		System.out.println("[GS " + this.ID + "] CutPOS =  " + mid +  " value = " + list2cut.get(mid).ID );

		System.out.print("[GS " + this.ID + "] Pre-cutted tour: " );
		list2cut.forEach((a)->System.out.print(a.ID + " -> "));
		System.out.println();


		for (int i = mid; i < list2cut.size(); i++){
			cutted.add(list2cut.get(i));
		}
		
		
		for (int i = 0; i < mid; i++){
			cutted.add(list2cut.get(i));
		}

		
		System.out.print("[GS " + this.ID + "] Cutted tour: " );
		cutted.forEach((a)->System.out.print(a.ID + " -> "));
		System.out.println();
		
		return cutted;
	}
	
	@Override
	public void postStep() {
	}
		
	@Override
	public String toString() {
		return "GS";
	}
	
	private static int radius;
	{
		try {
			radius = (int) Configuration.getDoubleParameter("GeometricNodeCollection/rMax"); // mine
					//.getIntegerParameter("AntennaConnection/rMax"); //pink
					
		} catch (CorruptConfigurationEntryException e) {
			Tools.fatalError(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#draw(java.awt.Graphics, sinalgo.gui.transformation.PositionTransformation, boolean)
	 */
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		
		this.setColor(Color.DARK_GRAY);
		this.drawingSizeInPixels = 10 ; 

		//super.drawNodeAsDiskWithText(g, pt, highlight, "GS", 10, Color.RED);
		
		//String text = Integer.toString(this.ID) + ":GS"; 
		
		//String text = Integer.toString(this.ID) + "|" + poiMessages.size() ; 
		
		String text = Integer.toString(nodeCreationOrder) + "|" + poiMessages.size() ; 
		
		
		//super.drawNodeAsDiskWithText(g, pt, highlight, text, 15, Color.YELLOW);
		
		// pink style
		Color bckup = g.getColor();
		g.setColor(Color.BLACK);
		this.drawingSizeInPixels = (int) (defaultDrawingSizeInPixels * pt
				.getZoomFactor());
		// pink: super.drawAsDisk(g, pt, highlight, drawingSizeInPixels);
		//super.drawNodeAsDiskWithText(g, pt, highlight, text, 15, Color.YELLOW); // mine
		
		g.setColor(Color.GRAY);
		pt.translateToGUIPosition(this.getPosition());
		//int r = (int) (radius * pt.getZoomFactor());
		//g.drawOval(pt.guiX - r, pt.guiY - r, r * 2, r * 2);
		//g.setColor(bckup);

		int imgWidth = 0;
		int imgHeight = 0;
		int[][] grid = null;
		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		grid = new int[imgWidth][imgHeight];
		// copy the image data
		for (int i = 0; i < imgWidth; i++) {
			for (int j = 0; j < imgHeight; j++) {
				grid[i][j] = img.getRGB(i, j);
			}
		}

		int iniX = (int) this.getPosition().xCoord - (imgWidth / 2);
		int iniY = (int) this.getPosition().yCoord - (imgHeight / 2);

		for (int i = iniX; i < imgWidth + iniX; i++) {
			for (int j = iniY; j < imgHeight + iniY; j++) {
				pt.translateToGUIPosition(i, j, 0); // top left corner of cell
				int topLeftX = pt.guiX, topLeftY = pt.guiY;
				pt.translateToGUIPosition((i + 1), (j + 1), 0); // bottom right
																// corner of
																// cell
				Color col = new Color(grid[i - iniX][j - iniY]);
				g.setColor(col);
				g.fillRect(topLeftX, topLeftY, pt.guiX - topLeftX, pt.guiY
						- topLeftY);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#drawToPostScript(sinalgo.io.eps.EPSOutputPrintStream, sinalgo.gui.transformation.PositionTransformation)
	 */
	public void drawToPostScript(EPSOutputPrintStream pw, PositionTransformation pt) {
		// the size and color should still be set from the GUI draw method
		drawToPostScriptAsDisk(pw, pt, drawingSizeInPixels/2, getColor());
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(GSnode tmp) {
		if(this.ID < tmp.ID) {
			return -1;
		} else {
			if(this.ID == tmp.ID) {
				return 0;
			} else {
				return 1;
			}
		}
	}
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Tools
	
	private int getPathSize(ArrayList<POInode> path) {
		
		POInode thisPOI;
		POInode lastPOI;
		int size = 0;
	
		for (int i = 1; i< path.size(); i++){
			thisPOI = (POInode)path.get(i);
			lastPOI = (POInode)path.get(i-1);
			size += (int) Math.sqrt(
				             (thisPOI.getPosition().xCoord - lastPOI.getPosition().xCoord) *  (thisPOI.getPosition().xCoord - lastPOI.getPosition().xCoord) + 
				             (thisPOI.getPosition().yCoord - lastPOI.getPosition().yCoord) *  (thisPOI.getPosition().yCoord - lastPOI.getPosition().yCoord)
				              );
		}
		return size;
	}
	
	private boolean isPoiInList(POInode poiFrom, ArrayList<POInode> poiList){
		POInode poiInList = new POInode();
		for (int i=0; i<poiList.size(); i++){
			poiInList = poiList.get(i);
			if (poiInList.ID == poiFrom.ID)
				return true;
		}		
		return false;	
	}
	
	
	private void removePoiFromList(POInode poiFrom, ArrayList<POInode> poiList){
		POInode poiA = new POInode();
		for (int i = 0; i< poiList.size(); i++) {		
			poiA = poiList.get(i);
			if (poiA.ID == poiFrom.ID){
				poiList.remove(i);
				//break;
			}
		}
	}
	
	

	private POInode getNearestPoi(POInode poiFrom, ArrayList<POInode> poiList){
		int dist2Last = Integer.MAX_VALUE;
		int i = 0;
		POInode poiA = new POInode();
		POInode poiTo = new POInode();
		
		// looking for the nearest POI from the GS
		for (i=0; i< poiList.size(); i++){			
			poiA = poiList.get(i);	
			if (poiA.ID != poiFrom.ID) {	
				// need to change it to use distance matrix created to TSP
				double distance = Math.hypot(poiFrom.getPosition().xCoord - poiA.getPosition().xCoord, poiFrom.getPosition().yCoord - poiA.getPosition().yCoord);
				if (distance <= dist2Last){
					dist2Last = (int) distance;
					poiTo = poiA;
				}
			}
		}		
		return poiTo;
		
	}
	
	
	
	

	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// NSN path planning
	
	// Creates a path by choosing the nearest POI to nearest POI
private ArrayList<POInode> createNotSoNaiveBestPath() {
			
			System.out.println("[GS " + this.ID + "] invoked" );
			
			// getting first and nearest
			POInode poiFrom = new POInode();		
			poiFrom.setPosition(this.getPosition()); // GroundStation
			POInode poiA = new POInode();
			poiA = getNearestPoi(poiFrom, listOfPOIs);
			poiFrom = poiA;
			
			// create the answerList
			ArrayList<POInode> poiOrder = new ArrayList<POInode>();
			poiOrder.add(poiFrom);

			ArrayList<POInode> poiListTemp = new ArrayList<POInode>();
			//poiListTemp = listOfPOIs;
			poiListTemp = (ArrayList<POInode>)listOfPOIs.clone();

			//removing the first one from the list
			removePoiFromList(poiFrom, poiListTemp);
		
			//creating the chain of nearest POI to prosecute
			while (poiOrder.size() < listOfPOIs.size()){
				poiA = getNearestPoi((poiFrom), poiListTemp);
				poiOrder.add(poiA);
				poiFrom = poiA;
				removePoiFromList(poiFrom, poiListTemp);
			}
			
			
			for (int i =0; i< poiOrder.size(); i++){
				poiA = poiOrder.get(i);
				System.out.print(poiA.ID + " - ");
			}
			System.out.println();
			
			//msgPOIorder = new msgPOIordered(poiOrder);
			return poiOrder;
				
}
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	
	
	////////////////// CHOCOSOLVER MESSY & OLD PART  /////////////////////////
	

	public int[][] prepareDistMatrix(ArrayList<POInode> pointsList, boolean toPrint){

		POInode poiA = new POInode();
		POInode poiB = new POInode();		
		int[][] myDist = new int[pointsList.size()][pointsList.size()];			
		
		if (toPrint) System.out.println("[TSP][Prep Adj] List size: " + pointsList.size());
		
		if (toPrint) {
			for (int i = 0; i < pointsList.size(); i++) {
				poiA = pointsList.get(i);
				System.out.println("[TSP][Prep Adj] Point " + i + " ID " + poiA.ID + " @ ( " + poiA.getPosition().xCoord + " , " + poiA.getPosition().yCoord + " )");
			}
		}
		
		if (toPrint) System.out.println("\n [TSP][Prep Adj] Matrix:\n");
		
			for (int i = 0; i < pointsList.size(); i++) { 					
				poiA = pointsList.get(i);
				for (int j = 0; j < pointsList.size(); j++) {
					poiB = pointsList.get(j);
					myDist[i][j] =  (int) Math.sqrt(
				            (poiA.getPosition().xCoord - poiB.getPosition().xCoord) *  (poiA.getPosition().xCoord - poiB.getPosition().xCoord) + 
				            (poiA.getPosition().yCoord - poiB.getPosition().yCoord) *  (poiA.getPosition().yCoord - poiB.getPosition().yCoord)
				        );
					if (toPrint) System.out.print(myDist[i][j] + "\t");
				}
				if (toPrint) System.out.println();
			 }
		if (toPrint) System.out.println("\n");	
		
		System.out.println("\n[PrepMatrix]Returning matrix...");	
		
		
		return myDist;
		
	}
	
	
	private void prepareSolver(Solver solver,  int[][] myDistMatrix){
		
		IntVar[] VS = VF.enumeratedArray("VS", listOfPOIs.size(), 0, listOfPOIs.size()-1, solver);		        		  
		IntVar totalCost = VF.enumerated("obj", 0, 99999999, solver);	
		solver.post(ICF.tsp(VS, totalCost, myDistMatrix)); 				
		solver.setObjectives(totalCost);		
			
		String useTimer = "false";
		
		try {
			useTimer = Configuration.getStringParameter("ThreadToRunWithTSPTimeEnabler/enabled");
			if (useTimer.equals("true")){			
				long maxTimeMs = Integer.MAX_VALUE;
				System.out.println("[TSP] [" + solver.getName() + "] timeout created as " + (maxTimeMs/1000) + " seconds..." );
				try {
					maxTimeMs = (int) Configuration.getIntegerParameter("ThreadToRunWithTSPTimer/maxduration");
					SMF.limitTime(solver, maxTimeMs);
					System.out.println("[TSP] [" + solver.getName() + "] timeout changed for  " + (maxTimeMs/1000) + "seconds..." );

				} catch (CorruptConfigurationEntryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}			
		} catch (CorruptConfigurationEntryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	private ArrayList<POInode> createTSPbasedPaths(){
		
		
		/*
		 *  PERFECT VERSION WITH CHOCO SOLVER SINGLE THREAD!!!!!!!!!! 
		 * 
		*/
		/*
		Solver solver = new Solver(); 													
        IntVar[] VS = VF.enumeratedArray("VS", listOfPOIs.size(), 0, listOfPOIs.size()-1, solver);		        		  
        IntVar totalCost  = VF.enumerated("obj", 0, 99999999, solver);					 		        	        
        		        
        solver.post(ICF.tsp(VS, totalCost, prepareDistMatrix(listOfPOIs, true))); 		
        //solver.findAllSolutions(); 
        
        // One size, fit both TSP usages
        if (setOfUAVs.first().myMobilityModelName.endsWith("AntiTSPbasedMobility")){       
        	solver.findOptimalSolution(ResolutionPolicy.MAXIMIZE, totalCost); 	
        } else {
        	solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, totalCost); 	
        }
		*/
        /*
		 *@Oli: Choco Solver does not return TSP ordered path, but returns (a,b) where 'a' goes to 'b', an edge not vertices order (documentation SUCCS[i]=j)
		 * 
		 * So, as VS contains the solution, we have:  VS[a]=b for each edge (a,b)
		*/
		/*
		int idx  = setOfUAVs.size()+2; //@Oli: To ignore  GS and UAVs 
		ArrayList<POInode> TSPlistOfPOIs = new ArrayList<POInode>();
		POInode p = new POInode();		        
		int lastPoi = 0;				
		p = listOfPOIs.get(lastPoi);
		TSPlistOfPOIs.add(p);
		while (TSPlistOfPOIs.size() < listOfPOIs.size()){
			for (int i=0; i< VS.length ;i++){						
				if ( ( i + idx) ==  TSPlistOfPOIs.get(lastPoi).ID ){

					for (int j = 0; j < (listOfPOIs.size()); j++) {		
						p = listOfPOIs.get(j);		
						if ( p.ID == (   VS[i].getValue() + idx )    ){									
							TSPlistOfPOIs.add(p);
						}							
					}
					lastPoi++;							
					break;
				}
			}
		}
		*/
		/* ---------------------------------------------------------------------------	 */
		/*
		 *  END OF PERFECT VERSION WITH CHOCO SOLVER SINGLE THREAD!!!!!!!!! 
		 * 
		*/									

		///////////////////////////////////////////////////////////////////////////
		// evalutaion removing some precision from Chocosolver (maybe in the matrix would be better
//		ArrayList<POInode> NonExactlistOfPOIs = listOfPOIs;
//		Position posOriginal;
//		Position posRelaxed = new Position(0,0,0);
//		Double x=0.0;
//		Double y=0.0;
//		for (int i=0; i< NonExactlistOfPOIs.size(); i++){
//			
//			posOriginal = NonExactlistOfPOIs.get(i).getPosition();
//			x = posOriginal.xCoord;
//			y = posOriginal.yCoord;
//			posRelaxed.assign(x.intValue(), y, 0);
//			NonExactlistOfPOIs.get(i).getPosition().assign(posRelaxed);
//			
//		}
		
		///////////////////////////////////////////////////////////////////////////
		
		
		// Choco Solver multithread approach 
		final int[][] myDistMatrix = prepareDistMatrix(listOfPOIs, true);
		//final int[][] myDistMatrix = prepareDistMatrix(NonExactlistOfPOIs, true);
        
		
		
		int THREADS = 1; //default threads
		try {
			THREADS = (int) Configuration.getIntegerParameter("ThreadToRunWithTSP/threads");
		} catch (CorruptConfigurationEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\n[TSP] " + THREADS + " threads created... for " + listOfPOIs.size() + " POIs");	

		
		List<Solver> solvers = new ArrayList<>();
		for(int i = 0 ; i < THREADS; i++){
		   Solver solver = new Solver();
		   solvers.add(solver);
		   prepareSolver(solver, myDistMatrix); // a dedicated method that declares variables and constraints
		   //the search should also be declared within that method
		} 			
		
		System.out.println("\n[TSP] " + THREADS + " solvers created... for " + listOfPOIs.size() + " POIs");	
		
		SMF.prepareForParallelResolution(solvers);		
				
		solvers.parallelStream().forEach(s -> {

    		System.out.println("\n[TSP] Sending " + s.getName());	

		    //syncSolutions(s) takes care of race condition. 

	        if (setOfUAVs.first().myMobilityModelName.endsWith("AntiTSPbasedMobility")){       
	        	s.findOptimalSolution(ResolutionPolicy.MAXIMIZE);
	        	syncSolutions(s, "max");
	        } else {
	        	s.findOptimalSolution(ResolutionPolicy.MINIMIZE);
	        	syncSolutions(s, "min");
	        }		    
		});
                			
		System.out.println("[GS " + this.ID + "]\tBest path found: ");
		
		
		System.out.println("[GS " + this.ID + "]\tFormatting path\n");
		// Re-formating the best result
		// Is necessary follow the path to recreate that
		//
		//int idx  = setOfUAVs.size()+2; //@Oli: To ignore  GS and UAVs 
		int idx = listOfPOIs.get(0).ID; // Modification on 2016-07-late | something just comes to make Node.ID almost random! :-(
		
		
		ArrayList<POInode> TSPlistOfPOIs = new ArrayList<POInode>();
		POInode p = new POInode();		        
		int lastPoi = 0;				
		p = listOfPOIs.get(lastPoi);
		TSPlistOfPOIs.add(p);
		
		while (TSPlistOfPOIs.size() < listOfPOIs.size()){
			for (int i=0; i< listOfPOIs.size() ;i++){	// listOfPois instead VS_bestTour because there may have garbage inside. 		
			
				if ( ( i + idx) ==  TSPlistOfPOIs.get(lastPoi).ID ){
					
					for (int j = 0; j < (listOfPOIs.size()); j++) {		
						p = listOfPOIs.get(j);		
						if ( p.ID == ( VS_bestTour[i].getValue() + idx )    ){									
							TSPlistOfPOIs.add(p);
						}							
					}
					//System.out.println();
					lastPoi ++;							
					break;
				}
			}			
		}//////////////////////////
	    
		//System.out.println("[GS " + this.ID + "]\tFilling msg to UAVs with best path");
						    
		//msgPOIorder = new msgPOIordered(TSPlistOfPOIs);		
		
		System.out.print("\n[TSP] Original path:");
		for (int k = 0; k < (listOfPOIs.size()); k++) {						
			p = listOfPOIs.get(k);
			System.out.print(" " + p.ID);
		}
		
		System.out.println();			
		
		System.out.print("\n[TSP] TSP path:");		
		for (int k = 0; k < (TSPlistOfPOIs.size()); k++) {						
			p = TSPlistOfPOIs.get(k);
			System.out.print(" " + p.ID);
		}
		
		System.out.println("\n");
		
		return TSPlistOfPOIs;
		
		
	}
	

	private synchronized void syncSolutions(Solver s, String policy){
		// This method choose the best result from each thread. 
		
		System.out.println("\n[TSP] syncSolutions called...");	

		
	    IntVar[] VS_local = s.retrieveIntVars().clone();				    			    
   
	    if (policy.equals("min")){
	    	if (VS_local[listOfPOIs.size()].getValue()  <  bestCost){
			    System.out.println("[TSP " + s.getName() + "] bestCost found  -> " +  VS_local[listOfPOIs.size()] + " =-> \n");
			    bestCost = VS_local[listOfPOIs.size()].getValue();
			    for (int i=0; i< listOfPOIs.size() ;i++){ // without multithread shoudl be VS.lenght()
				    System.out.print(VS_local[i]  + " -> ");
			    }
		    }
	    } else {
	    	if (VS_local[listOfPOIs.size()].getValue()  >  bestCost){
			    System.out.println("[TSP " + s.getName() + "] bestCost found  -> " +  VS_local[listOfPOIs.size()] + " =-> \n");
			    bestCost = VS_local[listOfPOIs.size()].getValue();
			    for (int i=0; i< listOfPOIs.size() ;i++){
				    System.out.print(VS_local[i]  + " -> ");
			    }
		    }	    	
	    }       
	    
	    VS_bestTour = VS_local;
	    for (int i=0; i< listOfPOIs.size() ;i++){
		    System.out.print(VS_local[i]  + " -> ");
	    }
	    
	    System.out.println("\n[TSP] syncSolutions() done\n");
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	private int getIdxFromPoiByID(int testPoi, ArrayList<POInode> pathPOIs) {
		POInode poiTmp = null;
		for (int i=0; i< pathPOIs.size(); i++) {
			poiTmp = pathPOIs.get(i);
			if (poiTmp.ID == testPoi)				
				return i;
		}	
		System.out.println("[GS " + this.ID + "] ERROR from getIdxFromPoi() - Inexistent POI = " + testPoi + " on path...");
		return 0;
		
	}

}
