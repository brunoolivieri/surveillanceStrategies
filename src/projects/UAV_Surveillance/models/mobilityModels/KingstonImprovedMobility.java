package projects.UAV_Surveillance.models.mobilityModels;

import java.util.ArrayList;

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
			
		POInode p = v.pathPOIs.get(v.pathIdx);
				
//		String txt = "";
//		if (((Integer)v.ID % 4)==0) {
//			txt += "\t";
//		}
//		txt += "_uav_" +  v.ID + " -> " + p.ID + " com pathIdx: " + v.pathIdx + "\n";
//		System.out.println(txt);
//		
		
		// Setting origin and destination to use with rendezvous
		v.nextPoi = p; // where I am going		
		
		POInode fakePoi = new POInode();
		fakePoi.setPosition(v.getPosition());
		v.lastPoi = getNearestPoi(fakePoi, v.pathPOIs); // just a shortcut. Since this UAV just reaches a POI, it is the nearest one.
		
		// low level path planning
		double nextX = p.getPosition().xCoord; 
		double nextY = p.getPosition().yCoord; 
		double nextZ = 0;
		Position nextMapPoint = new Position(nextX, nextY, nextZ); 
		
		// next POI pointer, after that exactaly path
		v.pathIdx++; 
		// if its bigger, shot back on first
		v.pathIdx = v.pathIdx % v.pathPOIs.size();
		
		// if did all POIs, invert POIs order
		if (v.pathIdx == 0) {
			//System.out.println("_uav_" + v.ID + " invertendo path... \n");
			ArrayList<POInode> poiListTemp = new ArrayList<POInode>();
			poiListTemp = (ArrayList<POInode>)v.pathPOIs.clone();		
			POInode poiTmp = new POInode();		
			v.pathPOIs.clear();		
			for (int i = poiListTemp.size() -1 ; i >= 0 ; i-- ){
				poiTmp = poiListTemp.get(i);
				v.pathPOIs.add(poiTmp);
			}
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
