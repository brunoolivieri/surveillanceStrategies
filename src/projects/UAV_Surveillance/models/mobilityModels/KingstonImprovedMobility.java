	package projects.UAV_Surveillance.models.mobilityModels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;
import sinalgo.runtime.Runtime;

public class KingstonImprovedMobility<syncronized> extends NaiveOrderedMobility{
	
	private POInode tmpPoi = new POInode();

	public KingstonImprovedMobility() throws CorruptConfigurationEntryException {
		super();
	}

	@Override
	public Position getNextPos(Node n) {
		return super.getNextPos(n);
		
	}

	//@Oli: set target as next POI in each list from each UAV
	@Override
	public synchronized Position GetNextWayPoint(UAVnode v) {
			
		if (!v.roundVisitedAllPOIs){
			
			tmpPoi = v.pathPOIs.get(v.getPathIdx());
		
			v.nextPoi = tmpPoi; // where shaw it go		
					
			// low level path planning
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
			
			v.setPathIdx(v.getPathIdx() + 1); // = v.pathIdx + v.ID;
			v.setPathIdx(v.getPathIdx() % v.pathPOIs.size());
	
			return nextMapPoint;
			
		} else {
			
			tmpPoi = v.pathPOIs.get(v.getPathIdx());
			
			// PAREI AQUI
			System.out.println("invertendo " + v.ID + " tmpPOI = "+ tmpPoi.ID + " idx = " + v.getPathIdx());
			v.roundVisitedAllPOIs = false;
			v.roundVisitedPOIs.clear();
			Collections.reverse(v.pathPOIs);
			
			v.nextPoi = tmpPoi; // where shaw it go		
			
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
			
			v.setPathIdx(v.getPathIdx() + 1); // = v.pathIdx + v.ID;
			v.setPathIdx(v.getPathIdx() % v.pathPOIs.size());
	
			return nextMapPoint;
			
		}
		
		
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
