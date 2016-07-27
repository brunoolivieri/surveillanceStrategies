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
	private UAVnode tmpUAV = new UAVnode();

	public KingstonImprovedMobility() throws CorruptConfigurationEntryException {
		super();
	}

	@Override
	public Position getNextPos(Node n) {
			
		// This simply makes Kingston (Almost)
		// Overhide to reset position and recalc moviments 
		if (((UAVnode) n).shawResetMoviment){
			super.remaining_hops = 0;
			((UAVnode) n).shawResetMoviment = false;
		};		
		return super.getNextPos(n);	
	}
	

	//@Oli: set target as next POI in each list from each UAV
	@Override
	public synchronized Position GetNextWayPoint(UAVnode v) {
			
		if (!v.roundVisitedAllPOIs){
					
			tmpPoi = v.pathPOIs.get(v.getPathIdx());
		
			v.nextPoi = tmpPoi; // where shaw it go			
			
			if (v.getPathIdx() >= v.pathPOIs.size()-1 ){				
				if  (v.getPathIdx() > 0) 
				   v.lastPoi = v.pathPOIs.get(v.getPathIdx()-1);								
			} else {
				if  (v.getPathIdx() > 0) 
					v.lastPoi = v.pathPOIs.get(v.getPathIdx()-1);
			}
			
			//System.out.println("[UAV " + v.ID + "]\t setando LAST=" +  v.lastPoi.ID + "\tNEXT= " + v.nextPoi.ID );
					
			// low level path planning
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
			
			v.setPathIdx(v.getPathIdx() + 1); // = v.pathIdx + v.ID;
	
			if (v.getPathIdx() >= v.pathPOIs.size()){
				v.roundVisitedAllPOIs  = true;				
			}
			v.setPathIdx(v.getPathIdx() % v.pathPOIs.size()); // just in case...

			return nextMapPoint;
			
		} else {
					
			// fixing on 2016/07/27
//			v.nextPoi = v.pathPOIs.get(v.pathPOIs.size()-1); 
//			v.lastPoi = v.pathPOIs.get(v.pathPOIs.size()-2);
			
			v.lastPoi = v.pathPOIs.get(v.pathPOIs.size()-1); 
			v.nextPoi = v.pathPOIs.get(v.pathPOIs.size()-2);
			
			//System.out.println("[UAV " + v.ID + "]\t setando LAST=" +  v.lastPoi.ID + "\tNEXT= " + v.nextPoi.ID + "  | after round" );
			
			v.roundVisitedAllPOIs = false;
			v.roundVisitedPOIs.clear();
			v.roundVisitedPOIs.add(v.pathPOIs.get(v.pathPOIs.size()-1)); // re-adding just the last because we use the size as check.
					
			Collections.reverse(v.pathPOIs);			
					
			// Now the penultimate (last but one) should be the [1] in the list, just after the Last (now the [0])
			tmpPoi = v.pathPOIs.get(1);
			v.setPathIdx(1); 
			
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
	
			return nextMapPoint;
			
		}
	}
	
	
}
