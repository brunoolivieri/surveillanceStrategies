package projects.UAV_Surveillance.models.mobilityModels;

import java.util.Collections;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;

public class KingstonImprovedOverNSNMobility extends NaiveOrderedMobility{

	private POInode tmpPoi = new POInode();
	private UAVnode tmpUAV = new UAVnode();
	
	public KingstonImprovedOverNSNMobility() throws CorruptConfigurationEntryException {
		super();
		// TODO Auto-generated constructor stub
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
	

	//set target as next POI in each list from each UAV
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
							
			// low level path planning
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
			
			v.setPathIdx(v.getPathIdx() + 1); // = v.pathIdx + v.ID;
	
			if (v.getPathIdx() >= v.pathPOIs.size()){
				v.roundVisitedAllPOIs  = true;		
				v.shawResetMoviment = true;
			}
			v.setPathIdx(v.getPathIdx() % v.pathPOIs.size()); // just in case...

			return nextMapPoint;
			
		} else {
					
		
			v.lastPoi = v.pathPOIs.get(v.pathPOIs.size()-1); 
			v.nextPoi = v.pathPOIs.get(v.pathPOIs.size()-2);
					
			v.roundVisitedAllPOIs = false;
			v.roundVisitedPOIs.clear();
			v.roundVisitedPOIs.add(v.pathPOIs.get(v.pathPOIs.size()-1)); // re-adding just the last because we use the size as check.
					
			Collections.reverse(v.pathPOIs);			
					
			// Now the penultimate (last but one) should be the [1] in the list, just after the Last (now the [0])
			tmpPoi = v.pathPOIs.get(1);
			v.setPathIdx(1); 
			
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
	
			// Reseting information about how many right or left UAVs I Know
			if (tmpPoi.ID == v.pathOriginal.get(1).ID) {
				v.knownUAVleft = 0;
			} else {
				if (tmpPoi.ID == v.pathOriginal.get(v.pathOriginal.size()-2).ID) {
					v.knownUAVright = 0;
				}
			}
			v.posOnSwarm = v.knownUAVleft +1 ; 

			
			return nextMapPoint;
			
		}
	}
	
	
}