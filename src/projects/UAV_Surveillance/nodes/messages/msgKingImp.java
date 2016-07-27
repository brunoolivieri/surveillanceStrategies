package projects.UAV_Surveillance.nodes.messages;

import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import sinalgo.nodes.messages.Message;

public class msgKingImp extends Message{

	public ArrayList<POInode> localPath = new ArrayList<POInode>();
	public int fromID = -1;
	public POInode lastPoi = new POInode();
	public POInode nextPoi = new POInode();
	public int knownUAVright = 0;
	public int knownUAVleft = 0;
	
	public msgKingImp(ArrayList<POInode> path, int fromUAV, POInode last, POInode next, int uavRight, int uavLeft) {
		this.localPath = path;
		this.fromID = fromUAV;
		this.lastPoi = last;
		this.nextPoi = next;	
		this.knownUAVright = uavRight;
		this.knownUAVleft = uavLeft;
	}

	@Override
	public Message clone() {
		// TODO Auto-generated method stub
		return this;
	}

}
