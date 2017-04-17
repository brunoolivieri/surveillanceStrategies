/*
                    ETH Zurich
                    Switzerland
                    dcg.ethz.ch

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 - Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the
   distribution.

 - Neither the name 'Sinalgo' nor the names of its contributors may be
   used to endorse or promote products derived from this software
   without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package projects.UAV_Surveillance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;

import projects.UAV_Surveillance.nodes.nodeImplementations.GSnode;
import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Node;
import sinalgo.runtime.AbstractCustomGlobal;
import sinalgo.runtime.GUIRuntime;
import sinalgo.runtime.Global;
import sinalgo.runtime.Main;
import sinalgo.runtime.Runtime;
import sinalgo.tools.Tools; 


import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerXYDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;


/**
 * This class holds customized global state and methods for the framework. 
 * The only mandatory method to overwrite is 
 * <code>hasTerminated</code>
 * <br>
 * Optional methods to override are
 * <ul>
 * <li><code>customPaint</code></li>
 * <li><code>handleEmptyEventQueue</code></li>
 * <li><code>onExit</code></li>
 * <li><code>preRun</code></li>
 * <li><code>preRound</code></li>
 * <li><code>postRound</code></li>
 * <li><code>checkProjectRequirements</code></li>
 * </ul>
 * @see sinalgo.runtime.AbstractCustomGlobal for more details.
 * <br>
 * In addition, this class also provides the possibility to extend the framework with
 * custom methods that can be called either through the menu or via a button that is
 * added to the GUI. 
 */
public class CustomGlobal extends AbstractCustomGlobal{
	
	//Logging customGlobal_log = Logging.getLogger("UAV_Surveillance_log.txt");

	//@Oli: Our vars
	public int ctRounds = 0;
	
	//@Oli: not used yet
	public boolean hasTerminated() {
		
		return false;
	}

	/**
	 * An example of a method that will be available through the menu of the GUI.
	 */
	//@Oli: not used yet
	@AbstractCustomGlobal.GlobalMethod(menuText="Echo")
	public void echo() {
		//@Oli: not used yet
		
		// Query the user for an input
		String answer = JOptionPane.showInputDialog(null, "This is an example.\nType in any text to echo.");
		// Show an information message 
		JOptionPane.showMessageDialog(null, "You typed '" + answer + "'", "Example Echo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//@Oli: better than native counter!
	public void postRound() {
		ctRounds++;
		
		//@Oli: break to get stats - REMEMBER TO REMOVE!!!
		
		
		
//		if(ctRounds>=1000000){
//			try {
//				this.summary();
//			} catch (CorruptConfigurationEntryException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			//System.out.println("\n Nodeslist: " + Tools.getNodeList().size());
//			
//		}
		
		
	}
	
	public void onExit() {
		try {
			this.summary();
		} catch (CorruptConfigurationEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void preRun() throws IOException {
		// A method called at startup, before the first round is executed.
		
		// Lets save distribution IFF asked to
		if (Global.shouldSavePoiDistribution){

			List<POInode> listOfPOIs = new ArrayList<POInode>();
			for(Node n : Runtime.nodes) {	
				if (n instanceof POInode){
					listOfPOIs.add((POInode) n);
					
				}
			}
			
			// chama a classe e manda!
			sinalgo.runtime.GlobalWriteAndLoadPositions saver = new sinalgo.runtime.GlobalWriteAndLoadPositions();
			saver.write(Global.distributionFolder,listOfPOIs);
			//GlobalWriteAndLoadPositions.savePatternJson(listOfPOIs, Global.distributionFolder);
		}
		
		// lets load a distribution IFF asked to
		// criar uma classe que fa�a tudo e retorne a lista para tacarmos nas vari�veis (iterador e lista lida)
		if (Global.shouldLoadPoiDistribution){

			
			
			// EVERYTHING REGADING READING WAS MIGRATED TO RUNTIME.JAVA just after parameter reading.
			
			Global.lastPOIloaded = 0;
//			sinalgo.runtime.GlobalWriteAndLoadPositions loader = new sinalgo.runtime.GlobalWriteAndLoadPositions();
//			try {
//				Global.listOfLoadedPOIs = loader.load(Global.distributionFile);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		

		}
				
	}
	
	/**
	 * Reset all nodes, s.t. they forget their history. 
	 */
	@GlobalMethod(menuText="reset")
	public void reset() {
		
		//@Oli: not used yet
		
//		for(Node n : Tools.getNodeList()) {
//			((S2Node) n).reset();
//		}
//		Tools.repaintGUI();
	}

	//@Oli: not used yet
	/* (non-Javadoc)
	 * @see sinalgo.runtime.AbstractCustomGlobal#includeGlobalMethodInMenu(java.lang.reflect.Method, java.lang.String)
	 */
	public String includeGlobalMethodInMenu(Method m, String defaultText) {
		if(m.getName().equals("reset")) {
			int size = Tools.getNodeList().size();
			if(size == 0) {
				return null; 
			} else {
				return "Reset all " + Tools.getNodeList().size() + " nodes"; // a context sensitive menu entry
			}
		}
		return defaultText; 
	}
	
	
	//@Oli: Results button with statistics
	@CustomButton(buttonText="OKButton", imageName="OK.gif", toolTipText="Prints out stats")
	public void summary() throws CorruptConfigurationEntryException{
		
		//Enumeration<?> nodeEnumer = Tools.getNodeList().getNodeEnumeration();
		int ctUAV = 0;
		int ctPOI = 0;
		int totalCost = 0;
		//String visitsStrategy = "error";
		int maxDataInPois = Integer.MIN_VALUE;
		int minDataInPois = Integer.MAX_VALUE;
		int tmp = 0;
		long globalAvgDelay = 0;
		ArrayList<Long> localMsgDelays = new ArrayList<Long>();	
		String strategyRunning = "error";
		long nMsgs = 0;
		
		for(Node n : Runtime.nodes) {			
			if (n instanceof UAVnode){
		    	ctUAV++;
		    	//visitsStrategy = (String)(((UAVnode)n).getMobilityModel().toString());
			}
			if (n instanceof POInode){
		    	ctPOI++;
		    	tmp = ((POInode) n).roundsNeglected;
		    	totalCost += tmp;
		    	
		    	if (tmp > maxDataInPois) {
		    		maxDataInPois = tmp;
		    	}
		    	if (tmp < minDataInPois) {
		    		minDataInPois = tmp;
		    	}		    	
			}
			
			if (n instanceof GSnode){
				strategyRunning = ((GSnode)n).strategyRunning;
				localMsgDelays = (ArrayList<Long>) ((GSnode) n).msgDelays;			
				globalAvgDelay =0;		
				
				for (int i = 0; i < localMsgDelays.size(); i++) {
					
					if (localMsgDelays.get(i)<=0)
						System.out.println("\n\n\n\n[CustomGlobal] ERROR IN MSG DELAY: " + i +  "\n\n\n\n\n");
					
					nMsgs++;

					globalAvgDelay = globalAvgDelay + localMsgDelays.get(i);					
				}

				System.out.println("\n\n\n[CustomGlobal] globalAvgDelay = " + globalAvgDelay + " n msg = " + ((GSnode) n).msgDelays.size());
				
				if (localMsgDelays.size()>0){
				  globalAvgDelay = globalAvgDelay / localMsgDelays.size();		
				} else {
					globalAvgDelay = -1;
				}				  
				System.out.println("[CustomGlobal] globalAvgDelay calculated = " + globalAvgDelay);				
			}			
		}
		
		
		//@Oli: native counter was not acurate from distinc execution modes (cmd line vs GUI)

		double surveillanceTax = 1 - ((double)(totalCost)/(ctRounds*ctPOI));
		surveillanceTax = surveillanceTax*100;
			
		String header = "Strategy;nPOIs;nUAV;nRounds;SucessTax;V2V_range;ctRounds;dimX;simumationTimeMS;TSP_threads;maxData;minData;globalAvgDelay;nMsgs;tourSize";
			
		double V2Vrange = Configuration.getDoubleParameter("GeometricNodeCollection/rMax");
		
		Date tem = new Date();
		long simumationTime = tem.getTime() - Global.startTime.getTime();
		
		int nThreads = (int) Configuration.getDoubleParameter("ThreadToRunWithTSP/threads");
		
		String logline = strategyRunning + ";" + ctPOI + ";" +  ctUAV + ";" + 
				String.format("%.5f", surveillanceTax) + "%;" + (int)V2Vrange + 
				";" + ctRounds + ";" + sinalgo.configuration.Configuration.dimX + ";" + 
				(simumationTime) +"MiliSegs;" + nThreads + "_TSP_thread;" + maxDataInPois + ";" + minDataInPois + ";" + globalAvgDelay + ";" + nMsgs + ";" +  Global.originalPathSize;
		
		System.out.println("\n[CustomGlobal] Final!\n");
		System.out.println(header);
		System.out.println(logline);
		
		if(Global.isGuiMode){
			if((ctUAV!=0)||(ctPOI!=0)){
				Tools.appendToOutput(strategyRunning);
				Tools.appendToOutput("\n\nPOIs = " + ctPOI);
				Tools.appendToOutput("\nUAVs = " + ctUAV);
				Tools.appendToOutput("\nV2Vrange = " + (int)V2Vrange);
				Tools.appendToOutput("\nRounds = " + ctRounds);
				Tools.appendToOutput("\nResult = " + String.format("%.3f", surveillanceTax) +"%");
				Tools.appendToOutput("\nmaxData = " + maxDataInPois);
				Tools.appendToOutput("\nminData = " + minDataInPois);
				Tools.appendToOutput("\nglobalAvgDelay = " + globalAvgDelay);	
				Tools.appendToOutput("\ntourSize = " + Global.originalPathSize);	
			}
			else{
				JOptionPane.showMessageDialog(((GUIRuntime)Main.getRuntime()).getGUI(), "There is no node.");
			}
		}
		try {
		    Files.write(Paths.get("stats_summary.txt"), logline.getBytes(), StandardOpenOption.APPEND);
		    Files.write(Paths.get("stats_summary.txt"), "\n".getBytes(), StandardOpenOption.APPEND);
		    System.out.println("\n\n[Summary] Saving file: " + Paths.get("~/stats_summary.txt"));
			
		}catch (IOException e) {
		    System.out.println("[Summary] ERRO: " + e);
		}
		
		
		
		BoxAndWhiskerCalculator statistics = null;  
		BoxAndWhiskerItem stats = statistics.calculateBoxAndWhiskerStatistics(localMsgDelays);
		logline = "";
		logline = strategyRunning + ";" +
				  ctPOI		+ ";" +
				  ctUAV		+ ";" +
				  stats.getMinRegularValue().toString() + ";" +
				  stats.getQ1().toString() + ";" +
				  stats.getMedian() + ";" +
				  stats.getQ3() + ";" +
				  stats.getMaxRegularValue() + ";" +
				  stats.getMean();
		
		// saving msg delays on by one in a file for each strategy
		//String filename = "stats_delays_" + strategyRunning + ".txt";		
		try {
		    Files.write(Paths.get("stats_delays_.txt"), logline.getBytes(), StandardOpenOption.APPEND);
		    Files.write(Paths.get("stats_delays_.txt"), "\n".getBytes(), StandardOpenOption.APPEND);
		    System.out.println("\n\n[Summary] Saving file: " + Paths.get("stats_delays_.txt"));		
		    System.out.println("\n\n[Summary] Saved: " + logline);		

		} catch (IOException e) {
			System.out.println("\n\n\n[Summary] ERRO nas estat�sticas: \n\n" + e);
			
		}
			
		
	}
	
}




