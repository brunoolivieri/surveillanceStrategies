package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.messages.msgKingImp;

public class KingImpReplanner {

	public POInode myLastPoi = new POInode();
	public POInode myNextPoi = new POInode();
	public ArrayList<POInode> myCurrentPath = new ArrayList<POInode>();
	public ArrayList<POInode> myOriginalPath = new ArrayList<POInode>();
	public ArrayList<POInode> newFullPath = new ArrayList<POInode>();
	public ArrayList<POInode> newLeftPath = new ArrayList<POInode>();
	public ArrayList<POInode> newRightPath = new ArrayList<POInode>();
	public int totalPois = -10;
	public int myID;
	public boolean amIrightUav;
	public boolean amIleftUav;
	public int myKnownRight = 0;
	public int myKnownLeft = 0;
	public int myKnownUAVs = 0 ;
	public msgKingImp otherUAVmsg;
	public POInode poiTmp = new POInode();
	public int myPositionOnSwarm = 0;
	public int myPathPortionSize = 0;
	public int myLastPoiOnMyPathPortion = 0;
	public int myFirstPoiOnMyPathPortion = 0;

	
	
	public void updateData(ArrayList<POInode> path, msgKingImp otherUAVmsg, POInode last, POInode next, ArrayList<POInode> originalPath, int ID, int uavRight, int uavLeft) {

		//updating
		this.myLastPoi = last;
		this.myNextPoi = next;
		this.myCurrentPath = path;
		this.myOriginalPath = originalPath;
		this.myID = ID;
		this.myKnownRight = uavRight;
		this.myKnownLeft = uavLeft;
		this.otherUAVmsg = otherUAVmsg;
		
		//this.hisPath = msg.localPath;		
		
		// reseting
		amIrightUav = false;
		amIleftUav = false;
		
		// looking my relative position
		int posMyLastPoi = -1;
		int otherUavLastPoi = -1;
		for (int i =0 ; i< this.myOriginalPath.size(); i++){
			poiTmp = this.myOriginalPath.get(i);
			if (poiTmp.ID == myLastPoi.ID){
				posMyLastPoi = i;
				//System.out.println("[UAV " + myID + "] posMyLastPoi=  " + posMyLastPoi);
			}
			if (poiTmp.ID == otherUAVmsg.lastPoi.ID){
				otherUavLastPoi = i;
				//System.out.println("[UAV " + otherUAVmsg.fromID + "] otherUavLastPoi=  " + otherUavLastPoi);
			}
		} 
		if (posMyLastPoi > otherUavLastPoi) { 
			//System.out.println("[UAV " + myID + "] comes from right");
			amIrightUav = true;
			myKnownLeft = otherUAVmsg.knownUAVleft + 1;
		} else {
			//System.out.println("[UAV " + myID + "] comes from left ");
			amIleftUav = true;
			myKnownRight = otherUAVmsg.knownUAVright + 1;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		myKnownUAVs = myKnownRight + myKnownLeft +1 ; 
		
		myPositionOnSwarm = myKnownLeft +1 ; 
		
		myPathPortionSize = (int) Math.floor(myOriginalPath.size() / myKnownUAVs);
		
		myLastPoiOnMyPathPortion = myPositionOnSwarm * myPathPortionSize;
		
		myFirstPoiOnMyPathPortion = myLastPoiOnMyPathPortion - myPathPortionSize;
				
		if (myLastPoiOnMyPathPortion >= myOriginalPath.size()) {
			myLastPoiOnMyPathPortion = myOriginalPath.size() -1;
		}	
		if (myFirstPoiOnMyPathPortion < 0 ) {
			myFirstPoiOnMyPathPortion = 0;
		}
		
		
	}
	
	public boolean rebalancePath(){
		


		return true;

		
	}
	
	private void mergePaths(){
		
		newFullPath.clear();
		newLeftPath.clear();
		newRightPath.clear();
		
		// Merging segments //////////////////////////////////////////////////////////
		if (amIleftUav) {
			for (int i=0; i< myCurrentPath.size(); i++) {
				poiTmp = myCurrentPath.get(i);
				newFullPath.add(poiTmp);
			}
			for (int i=0; i< otherUAVmsg.localPath.size(); i++) {
				poiTmp = otherUAVmsg.localPath.get(i);				
				if (!isPoiInList(poiTmp,newFullPath))				
					newFullPath.add(poiTmp);
			}			
		} else {
			for (int i=0; i< otherUAVmsg.localPath.size(); i++) {
				poiTmp = otherUAVmsg.localPath.get(i);
				newFullPath.add(poiTmp);
			}
			for (int i=0; i< myCurrentPath.size(); i++) {
				poiTmp = myCurrentPath.get(i);				
				if (!isPoiInList(poiTmp,newFullPath))				
					newFullPath.add(poiTmp);
			}				
		}
		//System.out.print("[UAV " + myID + "] Full path: ");
		for (int i=0; i< newFullPath.size(); i++) {
			poiTmp = newFullPath.get(i);
			//System.out.print( poiTmp.ID + " - " );
		}
		//System.out.println();
		//////////////////////////////////////////////////////////////////////////////
		
	}
	
	
	private boolean isPoiInList(POInode poiFrom, ArrayList<POInode> poiList){
		POInode poiInList = new POInode();
		for (int i=0; i<poiList.size(); i++){
			poiInList = poiList.get(i);
			if (poiInList.ID == poiFrom.ID)
				return true;
		}		
		return false;	
	}
	
	private int getIdxFromPoi(POInode testPoi, ArrayList<POInode> pathPOIs) {
		

		for (int i=0; i< pathPOIs.size(); i++) {
			poiTmp = pathPOIs.get(i);
			if (poiTmp.ID == testPoi.ID)				
				return i;
		}	
		System.out.print("[UAV " + myID + "]\t ERROR from getIdxFromPoi() - Inexistent POI on path.");
		return 0;
		
	}

}
