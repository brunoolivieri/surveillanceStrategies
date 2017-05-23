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
import java.util.Arrays;

public class ConcordePlanner {

	private static ArrayList<POInode> originalListOfPOIs = null;
	private static ArrayList<POInode> concordeTour = new ArrayList<POInode>();
	
	public static String winPath = "concorde\\";
	public static String nixPath = "./concorde/";
	public static String formatedPath = "-1";
	public static ArrayList<Integer> tour = new ArrayList<Integer>();
	public static int tourLenght =-1;
	private static String OS = System.getProperty("os.name").toLowerCase();

	
	public ArrayList<POInode> getTSPsolution(ArrayList<POInode> listOfPOIs) {

		originalListOfPOIs = listOfPOIs;
		
		genarateTSPfile();
		runConcorde();
		getConcordeResults();
		setSolution();
		
	
		return concordeTour;
	}
	
	private void setSolution() {
		System.out.println("\n[ConcordePlanner] Adjusting Concorde Tour with orignal POIs IDs... ");
	
		for (int i=0; i < tour.size(); i++ ){
			concordeTour.add(originalListOfPOIs.get(tour.get(i)));		
		}
				
		System.out.print("\n[ConcordePlanner] Tour: ");
		concordeTour.forEach((a)->System.out.print(a.ID + " -> "));
		System.out.println("\n");

		
	}

	private void getConcordeResults() {
		System.out.println("\n[ConcordePlanner] Importing Concorde Tour");

		try {
			// Open the file
			FileInputStream fstream = null;
			
			if ((OS.indexOf("nux") >= 0)){
		    	fstream = new FileInputStream(nixPath + "resultadoConcorde.txt");
		    } else {// it the windows machine
		    	fstream = new FileInputStream(winPath + "resultadoConcorde.txt");
		    } 			
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			int lineCounter = -1;
			int nPOIs = -1;
			
			//Read File Line By Line
			strLine = br.readLine();
			while ((strLine = br.readLine()) != null)   {
							
				int[] arr = Arrays.stream(strLine.split(" ")).map(String::trim).mapToInt(Integer::parseInt).toArray();
				//System.out.println(Arrays.toString(arr));
				//System.out.println(strLine);
				for (int i=0; i< arr.length; i++)
					tour.add(arr[i]);
				
			}
			br.close(); 		//Close the input stream
			
		} catch (IOException e) {
			System.out.println("\n\n\n[ConcordePlanner] Error trying to read Concorde Solution");
			e.printStackTrace();
			System.exit(-1);
		} 		
		System.out.print("\n[ConcordePlanner] Condorde tour order from file(" + tour.size() + "): ");
		tour.forEach((a)->System.out.print(a + " -> "));
		System.out.println("");
		
	}

	private void runConcorde() {

		System.out.println("[ConcordePlanner] Calling Concorde to Solve TSP Problem...\n");
		
		try {
			ProcessBuilder builder = null;
			
		    if ((OS.indexOf("nux") >= 0)){
				builder = new ProcessBuilder(
						nixPath + "concorde", "-o ", " resultadoConcorde.txt", " tsp2solve.txt");
		    } else {// it the windows machine
				builder = new ProcessBuilder(
						"cmd.exe", "/c", winPath + "runConcorde.bat");
				
		    }
		
			
	        builder.redirectErrorStream(true);   
	    	System.out.println("Command: " + builder.command().toString());
	        Process p = builder.start();
	        
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));        
	        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
	        
	        String line;
	        int i=0;
	        while (true) {
	            line = r.readLine();
	            System.out.println(line);

//	            i++;
//	            if ( i>= 500)//Integer.MAX_VALUE*0.3)
//	            	break;
	            
	            if ((line.contains("Total Running Time"))) { // last screen line from LKH solver
	            	System.out.println("\n\n[ConcordePlanner] Finished tour generation phase.\n");
	            	break; 
	            }
	        }
	        
	        w.write("foo"); // on windows a pause() function is used, it release the execution flow. Need to verify in *nix
	        	        
	    	System.out.println("[ConcordePlanner] Looks like finish well...\n");

			} catch (IOException e) {
				System.out.println("\n\n\n[ConcordePlanner] Error trying to execute Concorde solver\n\n\n");
				e.printStackTrace();
				System.exit(-1);
			}
		
	}

	private void genarateTSPfile(){
		
    	System.out.println("[ConcordePlanner] Creating TSP file...");
		
		String logline = "";
		logline += "Name sim" + originalListOfPOIs.size() + "\n";
		logline += "COMMENT : UAV simulation (olivieri)" + "\n";
		logline += "TYPE : TSP" + "\n";
		logline += "DIMENSION: " + originalListOfPOIs.size() + "\n";
		logline += "EDGE_WEIGHT_TYPE : EUC_2D" + "\n";
		logline += "NODE_COORD_SECTION" + "\n";
		
		for (int i=0; i<originalListOfPOIs.size(); i++)
			logline += originalListOfPOIs.get(i).ID + " " + 
					originalListOfPOIs.get(i).getPosition().xCoord + " " + 
					originalListOfPOIs.get(i).getPosition().yCoord + "\n";

		logline += "EOF" + "\n";
		
		
		try {
		    Files.write(Paths.get(winPath + "tsp2solve.txt"), logline.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
		    //Files.write(Paths.get("tsp2solve.txt"), "\n".getBytes(), StandardOpenOption.CREATE_NEW);
		    //System.out.println("\n\n[ConcordePlanner] Saving file: " + Paths.get("concorde/tsp2solve.txt"));
			
		}catch (IOException e) {
		    System.out.println("[ConcordePlanner] ERRO: " + e);
		}		
		
		
	}

}
