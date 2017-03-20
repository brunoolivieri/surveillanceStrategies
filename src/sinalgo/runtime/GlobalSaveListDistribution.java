package sinalgo.runtime;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;

public class GlobalSaveListDistribution {
	
	String file = "";
	ArrayList<POInode> POI;
	
	public GlobalSaveListDistribution(String filename,ArrayList<POInode> POIlist) {
		
		this.file = filename;
		this.POI = POIlist;
			
	}
	
	public void run()  throws IOException {
		FileOutputStream fout= new FileOutputStream (file);
		
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		
		oos.writeObject(POI);
		
		fout.close();
		
	}

}
