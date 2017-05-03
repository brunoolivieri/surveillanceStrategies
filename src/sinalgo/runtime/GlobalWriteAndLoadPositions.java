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


import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;


public class GlobalWriteAndLoadPositions {
	
	
	public GlobalWriteAndLoadPositions(){	
	}
	public void write(String folder, List<POInode> POIlist)  throws IOException {	
		
		int nextFileItarator = (int) Files.list(Paths.get(folder)).count();
		nextFileItarator++;
		FileOutputStream fout= new FileOutputStream(folder + nextFileItarator + ".txt");
		
		System.out.print("\n[Global] Trying SAVE a distribution on " + folder + nextFileItarator + ".txt" + ": ");
		
		ObjectOutputStream oos = new ObjectOutputStream(fout);		
		oos.writeObject(POIlist);
		oos.close();
	
		POIlist.forEach((a)->System.out.print("POI " + a.ID + " @ (" + a.getPosition().xCoord +" , " + a.getPosition().yCoord + ") | " ));
		System.out.println("\n");
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<POInode> load(String file)  throws IOException, ClassNotFoundException{
		System.out.print("\n[Global] Trying LOAD a distribution: " + Global.distributionFile + " ||>  ");
		
		
		
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fin);
		List<POInode> listOfLoadedPOIs = new ArrayList<POInode>();

		listOfLoadedPOIs = (ArrayList<POInode>) ois.readObject(); // THIS LOOK WRONG, I MEAN, Does not find the class POInode

		ois.close();	
	
		listOfLoadedPOIs.forEach((a)->System.out.print("POI " + a.myID + " @ (" + a.myPos.xCoord +" , " + a.myPos.yCoord + ") | " ));
		System.out.println("\n");
		return (ArrayList<POInode>) listOfLoadedPOIs;	
	}

	
}
