package sinalgo.runtime;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;


public class GlobalWriteAndLoadPositions {
	
	
	public GlobalWriteAndLoadPositions(){	
	}
	public void write(String folder, List<POInode> POIlist)  throws IOException {	
		int nextFileItarator = (int) Files.list(Paths.get(folder)).count();
		nextFileItarator++;
		FileOutputStream fout= new FileOutputStream (folder + nextFileItarator + ".txt");
		System.out.print("\n[Global] Trying SAVE a distribution on " + folder + nextFileItarator + ".txt" + ": ");
		ObjectOutputStream oos = new ObjectOutputStream(fout);		
		oos.writeObject(POIlist);
		oos.close();
		fout.close();
		POIlist.forEach((a)->System.out.print("POI " + a.ID + " @ (" + a.getPosition().xCoord +" , " + a.getPosition().yCoord + ") | " ));
		System.out.println("\n");
	}
	public ArrayList<POInode> load(String file)  throws IOException, ClassNotFoundException{
		System.out.print("\n[Global] Trying LOAD a distribution: " + Global.distributionFile + " ||>  ");
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fin);
		List<POInode> listOfLoadedPOIs = new ArrayList<POInode>();

		listOfLoadedPOIs = (ArrayList<POInode>) ois.readObject(); // THIS LOOK WRONG, I MEAN, Does not find the class POInode

		ois.close();	
		fin.close();
		listOfLoadedPOIs.forEach((a)->System.out.print("POI " + a.ID + " @ (" + a.getPosition().xCoord +" , " + a.getPosition().yCoord + ") | " ));
		System.out.println("\n");
		return (ArrayList<POInode>) listOfLoadedPOIs;	
	}
	
	
	
	
	
	
	
	
	
	public static void savePatternJson(ArrayList<POInode> p,String folder) throws IOException{
		
		int nextFileItarator = (int) Files.list(Paths.get(folder)).count();
		nextFileItarator++;
		String file = (folder + nextFileItarator + ".txt");
		
		String fn;
	    Gson gson = new Gson();
	    JsonElement element = gson.toJsonTree(p, new TypeToken<ArrayList<POInode>>() {}.getType());
	    JsonArray jsonArray = element.getAsJsonArray();
	    String json = gson.toJson(jsonArray);
	    try {
	        //write converted json data to a file named "file.json"
	        if(file != null){
	            fn = "JsonObjects/objects.json";
	        }
	        else{
	            //fn = "JsonObjects/" + filename + ".json";
	        }
	        FileWriter writer = new FileWriter("fn");
	        writer.write(json);
	        writer.close();
	    } 
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
//	public static List<POInode> loadPatternJson(){
//	    ArrayList<POInode> patterns = new ArrayList<>();
//	    Gson gson = new Gson();
//	    JsonParser jsonParser = new JsonParser();
//	    try {
//	        BufferedReader br = new BufferedReader(new FileReader("JsonObjects/objects.json"));
//	        JsonElement jsonElement = jsonParser.parse(br);
//
//	        //Create generic type
//	        Type type = new TypeToken<List<POInode>() {}.getType();
//	        return gson.fromJson(jsonElement, type);
//
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    return patterns;        
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
