package sinalgo.runtime;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;


import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;

public class GlobalLoadListDistribution {

	String file = "";
	
	public GlobalLoadListDistribution(String file) {
		
		this.file = file;
		
			
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<POInode> run()  throws IOException {
		
		System.out.print("\n[Global] Trying LOAD a distribution: " + Global.distributionFile + " ||>  ");

		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fin);

		ArrayList<POInode> listOfLoadedPOIs = new ArrayList<POInode>();

		try {
			listOfLoadedPOIs = (ArrayList<POInode>) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fin.close();
		ois.close();
		
		listOfLoadedPOIs.forEach((a)->System.out.print("POI " + a.ID + " @ (" + a.getPosition().xCoord +" , " + a.getPosition().yCoord + ") | " ));
		System.out.println("\n");
		
		return (ArrayList<POInode>) listOfLoadedPOIs;
		
		
//		ArrayList<POInode> listOfLoadedPOIs = new ArrayList<POInode>();
//
//	    FileInputStream fis = new FileInputStream(file);
//	    ObjectInputStream ois = new ObjectInputStream(fis);
//
//		    try {
//		        while (true) {
//		        	listOfLoadedPOIs.add((POInode) ois.readObject());
//		        }
//		    } catch (OptionalDataException e) {
//		        if (!e.eof) throw e;
//		    } finally {
//		        ois.close();
//		    }
//		
			
		
		
	
	}

}
