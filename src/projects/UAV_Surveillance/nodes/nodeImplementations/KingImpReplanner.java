package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.messages.msgKingImp;

public class KingImpReplanner {

	private POInode lastPoi = new POInode();
	private POInode nextPoi = new POInode();
	private ArrayList<POInode> myPath = new ArrayList<POInode>();
	private ArrayList<POInode> hisPath = new ArrayList<POInode>();
	private ArrayList<POInode> originalPath = new ArrayList<POInode>();
	private ArrayList<POInode> myNewPath = new ArrayList<POInode>();
	private int totalPois = -10;
	private int myID = -10;
	private boolean amIrightUav = false;
	private boolean amIleftUav = false;


	
	
	public void getNewPath(ArrayList<POInode> path, msgKingImp msg, POInode last, POInode next, ArrayList<POInode> originalPath, int ID) {

		lastPoi = last;
		nextPoi = next;
		myPath = path;
		hisPath = msg.localPath;
		this.originalPath = originalPath;
		this.myID = ID;
		
		POInode poiTmp = new POInode();
		for (int i =0 ; i< this.originalPath.size(); i++){
			poiTmp = this.originalPath.get(i);
			
			if (poiTmp.ID == lastPoi.ID){
				amIrightUav = true;
				System.out.println("[uav] " + myID + " vem da DIREITA");
				break;
			} else if (poiTmp.ID == msg.fromID) {
				amIleftUav = true;
				System.out.println("[uav] " + myID + " vem da ESQUERDA");
				break;
			}
		}
		
		// return calculateNewPath();
	}
	
	private void calculateNewPath(){
		
	}
	

}
