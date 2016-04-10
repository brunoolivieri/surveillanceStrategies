package projects.UAV_Surveillance.nodes.messages;

import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import sinalgo.nodes.messages.Message;

public class msgPOIordered extends Message {

	public ArrayList<POInode> data;

	public msgPOIordered(ArrayList<POInode> data) {
		this.data = data;
	}

	@Override
	public Message clone() {
		return new msgPOIordered(data);
	}
	
}
