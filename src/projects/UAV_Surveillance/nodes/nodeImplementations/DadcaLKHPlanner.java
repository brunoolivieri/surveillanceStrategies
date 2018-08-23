package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import sinalgo.runtime.Global;

public class DadcaLKHPlanner {

	private static ArrayList<POInode> originalListOfPOIs = null;
	//private static ArrayList<POInode> leftPOIs = new ArrayList<POInode>();
	//private static ArrayList<POInode> rightPOIs = new ArrayList<POInode>();
	//private static ArrayList<POInode> jointPOIs = new ArrayList<POInode>();
	POInode tmpGS = null;
	
	private static ArrayList<POInode> lkhTour = new ArrayList<POInode>();
	private static ArrayList<POInode> dictionaryTour = new ArrayList<POInode>();

	
	public static String winPath = "lkh-tsp\\";
	public static String nixPath = "./lkh-tsp/";
	public static ArrayList<Integer> tour = new ArrayList<Integer>();
	public static int tourLenght =-1;
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static String uniqueProblem;	
	public static String reUseWinPath = "cachelkh\\";
	public static String reUseNixPath = "./cachelkh/"; 
	private static String backupFolder = "";
	
	public DadcaLKHPlanner(ArrayList<POInode> listOfPOIs) {
		originalListOfPOIs = listOfPOIs;
		POInode tmpPOI = null;
	}


	public ArrayList<POInode> getDadcaLKHSolution() {

		// save solutions by a unique name for a possible future use
		if (Global.shouldLoadPoiDistribution) { //
			String tmp = "lkh-" + originalListOfPOIs.size() + "-" + Global.distributionFile;
			uniqueProblem = tmp.replaceAll("/", "-");		
			//System.out.println("\n\n[LKHPlanner] o unique problem � " + uniqueProblem + "\n") ;
			if ((OS.indexOf("nux") >= 0)){
		    	backupFolder = reUseNixPath;
		    } else {// it the windows machine
		    	backupFolder = reUseWinPath;
		    }
			// System.out.println("\n[LKHPlanner] Armazenamento de NOVAS solu��es em " + backupFolder + "\n") ;
		}
		
		if (Global.RESUE) {			
			
			if (alreadyComputed(uniqueProblem)) {	
				System.out.println("\n\n\n\n\n[ConcordePlanner] J� TINHA ESSA SOLU��OOOOOOOO vou aproveitar \n\n\n\n") ;
				restoreSolution(uniqueProblem);				
			} else {
				System.out.println("\n\n[ConcordePlanner] n�o tinha essa solu��o, fazendo nova \n\n") ;
				genarateTSPfile();
				runLKH();					}
			
		} else {
			genarateTSPfile();
			runLKH();
		}
		importLKHtour();
		setSolution();
		
		//return originalListOfPOIs;
		return lkhTour;
	}
	
	private boolean alreadyComputed(String uniqueProblem) {
			
			List<String> preComputedResults = new ArrayList<String>();
			File[] files = null;
			try {				
				if ((OS.indexOf("nux") >= 0)){
					files = new File(backupFolder).listFiles();
			    } else {// it the windows machine
					files = new File(".\\"+ backupFolder).listFiles();
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//If this pathname does not denote a directory, then listFiles() returns null. 
	
			//System.out.println("\n\n[ConcordePlanner] Solu��es J� computadas :" ) ;
			for (File file : files) {
			    if (file.isFile()) {
			    	preComputedResults.add(file.getName());
					//System.out.println("[ConcordePlanner] Sol: " + file.getName()) ;
			    }
			}
			System.out.println("") ;
			
			String search = uniqueProblem;
			search = search.toLowerCase(); // outside loop
			
			for(String str: preComputedResults) {
				if(str.trim().toLowerCase().contains(search))
			       return true;
			}
			return false;		
		}
	
	private void restoreSolution(String uniqueProblem) {

		String solutionRestored ="";
		
	    if ((OS.indexOf("nux") >= 0)){
	    	solutionRestored = nixPath + "LKH-output.txt";
	    } else {// it the windows machine
	    	solutionRestored = winPath + "LKH-output.txt";
	    }
				
		FileChannel src;
		FileChannel dest;
		try {
			src = new FileInputStream(backupFolder + uniqueProblem).getChannel();
			dest = new FileOutputStream(solutionRestored).getChannel();
		
			System.out.println("\n[LKHPlanner] backupFolder + uniqueProblem: " + backupFolder + uniqueProblem);
			System.out.println("\n[LKHPlanner] solutionRestored: " + solutionRestored);

			
			dest.transferFrom(src, 0, src.size());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("\n\n[LKHPlanner] ERROR restoring a solution - NEW File*tStream\n\n" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("\n\n[LKHPlanner] ERROR restoring a solution - dest.transferFrom\n\n" );
		}
		
	}

private static void backupSolution() {
		
		String result ="";
	
	    if ((OS.indexOf("nux") >= 0)){
	    	result = nixPath + "LKH-output.txt";
	    } else {// it the windows machine
	    	result = winPath + "LKH-output.txt";
	    }
				
		FileChannel src;
		FileChannel dest;
		try {
			src = new FileInputStream(result).getChannel();
			dest = new FileOutputStream(backupFolder + uniqueProblem).getChannel();
			dest.transferFrom(src, 0, src.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("\n\n[LKHPlanner] ERROR creating a solution backup - NEW File*tStream\n\n" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("\n\n[LKHPlanner] ERROR creating a solution backup - dest.transferFrom\n\n" );
		}
		
		
	}
	

	private void setSolution() {
		System.out.println("\n[DadcaLKHPlanner] Adjusting LKH Tour with orignal POIs IDs... ");
	
		lkhTour.clear();
		//System.out.print("\n[DadcaLKHPlanner] Tour order: ");
		for (int i=0; i < tour.size(); i++ ){  // -1 is because lkh.exe uses a -1 to present the loop, despite Concorde
			
			//System.out.print("["+ i +"] " + (tour.get(i) -1) + " -> ");

			lkhTour.add(originalListOfPOIs.get(tour.get(i) -1));		
			
		}
		
//		for (int i=0; i < tour.size(); i++ ){ // ugly n^2!!! Is better on ConcordeTSP! 
//			for (int j=0; j < originalListOfPOIs.size(); j++ ){	
//				if (tour.get(i) == originalListOfPOIs.get(j).ID){
//					lkhTour.add(originalListOfPOIs.get(j));
//					break;
//				}
//			}
//		}
		
				
		System.out.print("\n[DadcaLKHPlanner] Tour: ");
		lkhTour.forEach((a)->System.out.print(a.ID + " -> "));
		System.out.println("\n");

		
	}
	
	private static void runLKH(){
		// TO-DO: finish the MAC version later
		
		try {
		ProcessBuilder builder = null;
		
	    if ((OS.indexOf("nux") >= 0)){
			builder = new ProcessBuilder(
					nixPath + "LKH", 
					nixPath + "parametrosNix.txt");
	    } else {// it the windows machine
			builder = new ProcessBuilder(
					winPath + "lkh", 
					winPath + "parametrosWin.txt");
			// works great for internal commands:
			//ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"C:\\Program Files\" && dir");
	    }
			
        builder.redirectErrorStream(true);       
        Process p = builder.start();
        
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));        
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        
        String line;
        
        while (true) {
            line = r.readLine();
            if ((line.contains("Time.avg"))&&(line.contains("Time.max"))) { // last screen line from LKH solver
            	System.out.println("\n\n[DadcaLKHPlanner] Finished tour generation phase....\n");
            	break; 
            }
            System.out.println(line);
        }
        
        w.write("oi"); // on windows a pause() function is used, it release the execution flow. Need to verify in *nix
        
        backupSolution();
        
		} catch (IOException e) {
			System.out.println("\n\n\n[DadcaLKHPlanner] Error trying to execute LKH solver\n\n\n");
			e.printStackTrace();
			System.exit(-1);
		}
	}

		
	private void genarateTSPfile(){
			
	    	System.out.println("[DadcaLKHPlanner] Creating TSP file...");
			
			String logline = "";
			logline += "Name sim" + originalListOfPOIs.size() + "\n";
			logline += "COMMENT : UAV simulation (olivieri)" + "\n";
			logline += "TYPE : TSP" + "\n";
			logline += "DIMENSION: " + originalListOfPOIs.size() + "\n";
			logline += "EDGE_WEIGHT_TYPE : EUC_2D" + "\n";
			logline += "NODE_COORD_SECTION" + "\n";
			for (int i=0; i<originalListOfPOIs.size(); i++){
				//logline += originalListOfPOIs.get(i).ID + " " + 
				logline += (i+1) + " " + 
				//logline += (originalListOfPOIs.get(i).ID - originalListOfPOIs.size()) + " " + 
						originalListOfPOIs.get(i).getPosition().xCoord + " " + 
						originalListOfPOIs.get(i).getPosition().yCoord + "\n";
				//dictionaryTour.add(originalListOfPOIs.get(i)); // note: adding starting in ZERO and saved in the file as ONE!!! 
			}
			logline += "EOF" + "\n";
			
			
			try {
			   
				
				if ((OS.indexOf("nux") >= 0)){
					Files.write(Paths.get(nixPath + "tsp2solve.txt"), logline.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
	
	
			    } else {// it the windows machine
					Files.write(Paths.get(winPath + "tsp2solve.txt"), logline.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
					//Files.write(Paths.get("tsp2solve.txt"), "\n".getBytes(), StandardOpenOption.CREATE_NEW);
				    //System.out.println("\n\n[ConcordePlanner] Saving file: " + Paths.get("concorde/tsp2solve.txt"));
					
			    }
				
				
			}catch (IOException e) {
			    System.out.println("[DadcaLKHPlanner] ERRO: " + e);
			}		
			
			
		}
	
	
	
	
	private static void importLKHtour(){
		
		System.out.println("\n[DadcaLKHPlanner] Importing LKH tour");
	
		try {
			// Open the file
			FileInputStream fstream = null;
			
			if ((OS.indexOf("nux") >= 0)){
		    	fstream = new FileInputStream(nixPath + "LKH-output.txt");
		    } else {// it the windows machine
		    	fstream = new FileInputStream(winPath + "LKH-output.txt");
		    } 			
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			int lineCounter = -1;
			int nPOIs = -1;
			boolean readingHeader = true;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  if (!readingHeader){  // 5 is because the TSP header
				  if (lineCounter < nPOIs -1){
					  lineCounter++;
					  tour.add(Integer.parseInt(strLine));  
				  }
			  } else {
				  if (strLine.contains("Length")){
					  tourLenght = Integer.parseInt(strLine.substring(strLine.lastIndexOf("=") + 2)); 
					  System.out.println("Tour lenght = " + tourLenght );			  
				  }
				  if (strLine.contains("DIMENSION")){
					  nPOIs = Integer.parseInt(strLine.substring(strLine.lastIndexOf(":") + 2)); 
					  System.out.println("nPOIs = " + nPOIs );			  
				  }
				  if (strLine.contains("TOUR_SECTION")){
					  readingHeader = false;
				  }
			  }		 
			}
			br.close(); 		//Close the input stream
		} catch (IOException e) {
			System.out.println("\n\n\n[DadcaLKHPlanner]Error trying to read LKH solution");
			e.printStackTrace();
			System.exit(-1);
		} 		
		System.out.print("\n[DadcaLKHPlanner] LKH tour order: ");
		tour.forEach((a)->System.out.print(a + "->"));
		System.out.println("");
	}

}
