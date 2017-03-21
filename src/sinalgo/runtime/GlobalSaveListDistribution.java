package sinalgo.runtime;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;

import java.util.ArrayList;
import java.util.List;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;

public class GlobalSaveListDistribution {
	
	String folder = "";
	//ArrayList<POInode> POI = new ArrayList<POInode>();
	List<POInode> POIlist = new ArrayList<POInode>();

	
	public GlobalSaveListDistribution(String folder, List<POInode> POIlist) {
		
		this.folder = folder;
		this.POIlist = (List<POInode>) POIlist;
			
	}
	
	public void run()  throws IOException {
		
		
		int nextFileItarator = (int) Files.list(Paths.get(folder)).count();
		nextFileItarator++;
		
		// original working well for a single list on final file, erasing others
		FileOutputStream fout= new FileOutputStream (folder + nextFileItarator + ".txt");
		
		System.out.print("\n[Global] Trying SAVE a distribution on " + folder + nextFileItarator + ".txt" + ": ");

		
		ObjectOutputStream oos = new ObjectOutputStream(fout);		
		oos.writeObject(POIlist);
		oos.close();
		fout.close();
		
		POIlist.forEach((a)->System.out.print("POI " + a.ID + " @ (" + a.getPosition().xCoord +" , " + a.getPosition().yCoord + ") | " ));
		
		System.out.println("\n");

	}

}
