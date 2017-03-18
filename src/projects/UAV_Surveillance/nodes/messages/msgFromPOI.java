package projects.UAV_Surveillance.nodes.messages;


import sinalgo.nodes.messages.Message;


public class msgFromPOI  extends Message {

	public int data = 0;
	public int timeStamp = 0;
	public int recipient = 0;
	
	public msgFromPOI(int data, int timeStamp, int rcpt) {
		this.data = data;
		this.timeStamp = timeStamp;
		this.recipient = rcpt;
	}

	@Override
	public Message clone() {
		return new msgFromPOI(data, timeStamp, recipient);
	}

}