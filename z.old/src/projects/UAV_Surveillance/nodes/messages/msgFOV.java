package projects.UAV_Surveillance.nodes.messages;


import sinalgo.nodes.messages.Message;

public class msgFOV extends Message {

	public int data;

	public msgFOV(int data) {
		this.data = data;
	}

	@Override
	public Message clone() {
		return new msgFOV(data);
	}

}