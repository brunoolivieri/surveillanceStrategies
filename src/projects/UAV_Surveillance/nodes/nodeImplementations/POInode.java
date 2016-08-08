package projects.UAV_Surveillance.nodes.nodeImplementations;


import java.awt.Color;

import java.awt.Graphics;
import java.lang.reflect.Method;
import java.util.TreeSet;

import projects.UAV_Surveillance.nodes.messages.msgFOV;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.io.eps.EPSOutputPrintStream;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.runtime.Runtime;
import sinalgo.tools.Tools;
import sinalgo.nodes.messages.Message;



public class POInode extends Node implements Comparable<POInode> {

	//private static int maxNeighbors = 0; // global field containing the max number of neighbors any node ever had
	
	//private boolean isMaxNode = false; // flag set to true when this node has most neighbors
	//private boolean drawAsNeighbor = false; // flag set by a neighbor to color specially
	
	public int roundsNeglected = 0;
	public int distToGS = 0;
	private boolean init = false;
	
	// The set of nodes this node has already seen
	private TreeSet<UAVnode> neighbors = new TreeSet<UAVnode>();
	public boolean drawAsNeighbor;
	
	/**
	 * Reset the list of neighbors of this node.
	 */
	public void reset() {
		neighbors.clear();
		roundsNeglected = 0;
	}
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void handleMessages(Inbox inbox) {
		
		while(inbox.hasNext()) {
			Message msg = inbox.next();
			if(msg instanceof msgFOV) {
				//roundsNeglected--;
				roundsNeglected -= 5;
			}
		}
	}

	@Override
	public void init() {
		// calcs distance to GS in order to be sorted
		if (!init){
			distToGS = (int) Math.sqrt(
		            (this.getPosition().xCoord - 0) *  (this.getPosition().xCoord - 0) + 
		            (this.getPosition().yCoord - 0) *  (this.getPosition().yCoord - 0)
		        );
			init = true;
			System.out.println("[POI " + this.ID + "] " + "dist to GS = " + this.distToGS);
		}
		
//		if (this.ID % 2 != 0){
//		 this.ID = this.ID * 2;	
//		}
		
	}

	@Override
	public void neighborhoodChange() {

//		for(Edge e : this.outgoingConnections){
//			if (e.endNode instanceof UAVnode)
//				roundsNeglected--;
//		}
		
	}

	@Override
	public void preStep() {
		roundsNeglected++;
		
	
		
	}

	@Override
	public void postStep() {
	}
	
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#includeMethodInPopupMenu(java.lang.reflect.Method, java.lang.String)
	 */


	@Override
	public String toString() {
		return this.getClass().getCanonicalName();
	}
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#draw(java.awt.Graphics, sinalgo.gui.transformation.PositionTransformation, boolean)
	 */
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		
		this.setColor(Color.BLACK);

		//default
//		this.drawingSizeInPixels = 10 ; // (int) (fraction * pt.getZoomFactor() * this.defaultDrawingSizeInPixels);
//		
//		String text = this.ID + "|" + Integer.toString(roundsNeglected);// + "|" + msgSentInThisRound;
//		super.drawNodeAsDiskWithText(g, pt, highlight, text, 10, Color.YELLOW);
		
		//debug
		this.drawingSizeInPixels = 5 ; // (int) (fraction * pt.getZoomFactor() * this.defaultDrawingSizeInPixels);
		
		String text = Integer.toString(this.ID) ; // + "|" + Integer.toString(roundsNeglected);// + "|" + msgSentInThisRound;
		super.drawNodeAsDiskWithText(g, pt, highlight, text, 5, Color.YELLOW);
		
		
	}
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#drawToPostScript(sinalgo.io.eps.EPSOutputPrintStream, sinalgo.gui.transformation.PositionTransformation)
	 */
	public void drawToPostScript(EPSOutputPrintStream pw, PositionTransformation pt) {
		// the size and color should still be set from the GUI draw method
		drawToPostScriptAsDisk(pw, pt, drawingSizeInPixels/2, getColor());
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(POInode tmp) {
		
		// You can change ID to distToGS to improve debug operations
		if(this.ID < tmp.ID) {
			return -1;
		} else {
			if(this.ID == tmp.ID) {
				return 0;
			} else {
				return 1;
			}
		}
	}

}
