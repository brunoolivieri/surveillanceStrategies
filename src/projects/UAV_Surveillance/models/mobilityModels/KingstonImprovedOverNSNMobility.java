package projects.UAV_Surveillance.models.mobilityModels;

import java.util.Collections;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode.STATUS;
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
	
		// SAME AS ZIGZAG
		// This simply makes Kingston (Almost)
		// Overhide to reset position and recalc moviments 
		if (((UAVnode) n).shawResetMoviment){
			super.remaining_hops = 0;
			((UAVnode) n).shawResetMoviment = false;
			//System.out.println("[UAV " + ((UAVnode) n).ID + "] remaining_hops set to zero ");

		};		
		
		return super.getNextPos(n);	
	}
	

	//set target as next POI in each list from each UAV
	@Override
	public synchronized Position GetNextWayPoint(UAVnode v) {

		
		if (v.myStatus == STATUS.REFUELPROCESS){ // going to refuel
			
			return v.myGS.getPosition();
			
		} else if (!v.roundVisitedAllPOIs){
			
			tmpPoi = v.pathPOIs.get(v.getPathIdx());
		
			v.nextPoi = tmpPoi; // where shaw it go		
			
			//System.out.println("[UAV " + v.ID + "] setting next POI to  " + tmpPoi.ID);

			
			if (v.getPathIdx() >= v.pathPOIs.size()-1 ){				
				if  (v.getPathIdx() > 0) 
				   v.lastPoi = v.pathPOIs.get(v.getPathIdx()-1);								
			} else {
				if  (v.getPathIdx() > 0) 
					v.lastPoi = v.pathPOIs.get(v.getPathIdx()-1);
			}
							
			// low level path planning
			Position nextMapPoint = new Position(tmpPoi.getPosition().xCoord, tmpPoi.getPosition().yCoord, tmpPoi.getPosition().zCoord); 
			
			v.setPathIdx(v.getPathIdx() + 1); 
			
			v.setPathIdx(v.getPathIdx() % v.pathPOIs.size()); // just in case...
	
			// reseting known UAVs right or left
			if (tmpPoi.ID == v.pathOriginal.get(0).ID){
				v.knownUAVleft = 0;
			}
			if (tmpPoi.ID == v.pathOriginal.get(v.pathOriginal.size()-1).ID){
				v.knownUAVright = 0;
			}
//						
//			// releasing UAV from Kingston adjustment
//			if ((v.adjustingPath)&(v.lastPoi.ID == v.adjustingPoiTarget.ID)){
//				v.adjustingPath = false;
//				v.meiaVoltaVolver();
//				v.roundVisitedPOIs.add(v.lastPoi);
//				System.out.println("[UAV " + v.ID + "] released from adjustment and doing Meia-Volta-Voler & adjustingPath= " + v.adjustingPath);
//			} else {
//				//System.out.println("[UAV " + v.ID + "] NOT released from adjustment: last= " + v.lastPoi.ID + " adjustingPoiTarget= " + v.adjustingPoiTarget.ID + " & adjustingPath= " + v.adjustingPath);
//
//			}
			

			return nextMapPoint;
			
		} else 
						
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
			
			//System.out.println("[UAV " + v.ID + "] reverting hole path and going to " + tmpPoi.ID);
			
			// reseting known UAVs right or left
			if (v.lastPoi.ID == v.pathOriginal.get(0).ID){
				v.knownUAVleft = 0;
				v.adjustingPath = false;
			}
			if (v.lastPoi.ID == v.pathOriginal.get(v.pathOriginal.size()-1).ID){
				v.knownUAVright = 0;
				v.adjustingPath = false;
			}
			
			return nextMapPoint;
			
		}
	}
	
	


