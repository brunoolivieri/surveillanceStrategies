package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.messages.msgKingImp;

public class KingImpReplanner {

	private POInode myLastPoi = new POInode();
	private POInode myNextPoi = new POInode();
	private ArrayList<POInode> myCurrentPath = new ArrayList<POInode>();
	private ArrayList<POInode> myOriginalPath = new ArrayList<POInode>();
	private ArrayList<POInode> newFullPath = new ArrayList<POInode>();
	private ArrayList<POInode> newLeftPath = new ArrayList<POInode>();
	private ArrayList<POInode> newRightPath = new ArrayList<POInode>();
	private int totalPois = -10;
	private int myID;
	public boolean amIrightUav;
	public boolean amIleftUav;
	private int myKnownRight = 0;
	private int myKnownLeft = 0;
	private msgKingImp otherUAVmsg;
	private POInode poiTmp = new POInode();

	
	
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
			//System.out.println("[UAV " + myID + "] come from ");
			amIrightUav = true;
			myKnownLeft++;
		} else {
			//System.out.println("[UAV " + myID + "] Vem da esquerda ");
			amIleftUav = true;
			myKnownRight++;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	}
	
	public void calculateNewPaths(){
		
		mergePaths();
		int swarmSize = myKnownLeft + myKnownRight + 1;
		int nPois = newFullPath.size();
		
		int middle = swarmSize / 2;
		

		
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
	

}
