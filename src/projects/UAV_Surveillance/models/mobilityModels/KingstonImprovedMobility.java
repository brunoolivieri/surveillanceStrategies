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
			
			System.out.println("[UAV " + v.ID + "]\t pegando POI " + tmpPoi.ID);

			v.nextPoi = tmpPoi; // where shaw it go		
					
			// low level path planning
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
			
			v.setPathIdx(v.getPathIdx() + 1); // = v.pathIdx + v.ID;
	
			if (v.getPathIdx() >= v.pathPOIs.size()){
				v.roundVisitedAllPOIs  = true;				
			}
			v.setPathIdx(v.getPathIdx() % v.pathPOIs.size()); // just in case...

			return nextMapPoint;
			
		} else {
						
			//v.invertPathRoute();
			
			v.roundVisitedAllPOIs = false;
			v.roundVisitedPOIs.clear();
			v.roundVisitedPOIs.add(v.pathPOIs.get(v.pathPOIs.size()-1)); // re-adding just the last because we use the size as check.
					
			Collections.reverse(v.pathPOIs);			

			System.out.print("\n[UAV " + v.ID + "]\tvisitou todos - invertPathRoute() ");	
					
			tmpPoi = v.pathPOIs.get(1);

			System.out.print("[UAV " + v.ID + "]\t reverteu e apontou para " + tmpPoi.ID);

			System.out.print("\tNew Path: ");
			for (int i = 0; i<v.pathPOIs.size(); i++){
				System.out.print(v.pathPOIs.get(i).ID + " - ");
			}
			System.out.println("\tindo para " + tmpPoi.ID );
					
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
			
			v.setPathIdx(1);
	
			return nextMapPoint;
			
		}
	}
	
	
}
