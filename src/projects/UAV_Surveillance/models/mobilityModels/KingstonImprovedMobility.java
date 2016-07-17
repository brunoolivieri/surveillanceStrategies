package projects.UAV_Surveillance.models.mobilityModels;

import java.util.ArrayList;
import java.util.Collections;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;

public class KingstonImprovedMobility<syncronized> extends NaiveOrderedMobility{

	public KingstonImprovedMobility() throws CorruptConfigurationEntryException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Position getNextPos(Node n) {
		return super.getNextPos(n);
		
	}

	
	//@Oli: set target as next POI in each list from each UAV
	@Override
	protected synchronized Position GetNextWayPoint(UAVnode v) {
			
		System.out.println("_uav_" + v.ID + " idx = " + v.getPathIdx());
		
		POInode p = v.pathPOIs.get(v.getPathIdx());
	
		v.nextPoi = p; // where shaw it go		
				
		// low level path planning
		Position nextMapPoint = new Position(p.getPosition().xCoord, p.getPosition().yCoord, p.getPosition().zCoord); 
		
		POInode fakePoi = new POInode();
		fakePoi.setPosition(v.getPosition());
		v.lastPoi = getNearestPoi(fakePoi, v.pathPOIs); // just a shortcut. Since this UAV just reaches a POI, it is the nearest one.
		
		v.setPathIdx(v.getPathIdx() + 1); 
		
		System.out.print("_uav_" + v.ID + " checando ID " + v.getPathIdx() + " vs " + (v.pathPOIs.size()-1));
		
		if ( v.getPathIdx() >= (v.pathOriginal.size()-1) ) { // second time pointing to first, means full POIs covered.
			
			//System.out.print("_uav_" + v.ID + " ori = " + v.lastPoi.ID + " to = " + v.pathPOIs.get((v.pathPOIs.size()-1)).ID + " invertendo path... ");
			
			Collections.reverse(v.pathPOIs);
			
			for (int i = 0 ; i< v.pathPOIs.size(); i++)
				System.out.print(v.pathPOIs.get(i).ID + " - ");	
			
			System.out.println();
			v.setPathIdx(0);
		}
		///////	
		
		return nextMapPoint;
	}
	
	
	private POInode getNearestPoi(POInode poiFrom, ArrayList<POInode> poiList){
		int dist2Last = Integer.MAX_VALUE;
		int i = 0;
		POInode poiA = new POInode();
		POInode poiTo = new POInode();
		
		// looking for the nearest POI from the GS
		for (i=0; i<poiList.size(); i++){			
			poiA = poiList.get(i);	
			if (poiA.ID != poiFrom.ID) {	
				// need to change it to use distance matrix created to TSP
				double distance = Math.hypot(poiFrom.getPosition().xCoord - poiA.getPosition().xCoord, poiFrom.getPosition().yCoord - poiA.getPosition().yCoord);
				if (distance <= dist2Last){
					dist2Last = (int) distance;
					poiTo = poiA;
				}
			}
		}		
		return poiTo;
		
	}
	
}
