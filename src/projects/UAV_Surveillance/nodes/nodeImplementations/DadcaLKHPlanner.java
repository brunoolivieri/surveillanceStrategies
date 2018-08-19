package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

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
	
	public DadcaLKHPlanner(ArrayList<POInode> listOfPOIs) {
		originalListOfPOIs = listOfPOIs;
		POInode tmpPOI = null;
	}


	public ArrayList<POInode> getDadcaLKHSolution() {
		// TODO Auto-generated method stub
		
		genarateTSPfile();
		runLKH();
		importLKHtour();
		setSolution();
		
		//return originalListOfPOIs;
		return lkhTour;
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
