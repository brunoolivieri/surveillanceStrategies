package projects.UAV_Surveillance.models.mobilityModels;

import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;

public class KingstonImproved extends NaiveOrderedMobility{

	public KingstonImproved() throws CorruptConfigurationEntryException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Position getNextPos(Node n) {
		return super.getNextPos(n);
		
	}

	
	//@Oli: set target as next POI in each list from each UAV
	@Override
	public Position GetNextWayPoint(UAVnode v) {
			
		POInode p = v.pathPOIs.get(v.pathIdx);
		
		//System.out.println("v " + v.ID + " indo para p " + p.ID);
		
		double randx = p.getPosition().xCoord; 
		double randy = p.getPosition().yCoord; 
		double randz = 0;
		
		// next POI
		v.pathIdx++; // = v.pathIdx + v.ID;
		// if its bigger, shot back on first
		v.pathIdx = v.pathIdx % v.pathPOIs.size();

//		// if did all POIs, invert POIs order
//		if (v.pathIdx == 0) {
//			ArrayList<POInode> poiListTemp = new ArrayList<POInode>();
//			poiListTemp = (ArrayList<POInode>)v.pathPOIs.clone();
//			
//			System.out.println("\n\n\naqui\n\n\n");
//			
//			v.pathPOIs.clear();
//			
//			for (int i = poiListTemp.size() -1 ; i <= 0 ; i-- ){
//				v.pathPOIs.add(poiListTemp.get(i));
//			}
//			
//		}
//		///////
		
		return new Position(randx, randy, randz);
	}
	
}
