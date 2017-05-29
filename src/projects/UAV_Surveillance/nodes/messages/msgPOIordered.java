package projects.UAV_Surveillance.nodes.messages;

import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import sinalgo.nodes.messages.Message;

public class msgPOIordered extends Message {

	public ArrayList<POInode> data;
	public int idxPathStart = 0;

	public msgPOIordered(ArrayList<POInode> data, int idx2go) {
		this.data = data;
		this.idxPathStart = idx2go;
	}

	@Override
	public Message clone() {
		return new msgPOIordered(data, idxPathStart);
	}
	
}
