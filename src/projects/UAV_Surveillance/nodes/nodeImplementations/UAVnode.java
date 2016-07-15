/*
 Copyright (c) 2007, Distributed Computing Group (DCG)
                    ETH Zurich
                    Switzerland
                    dcg.ethz.ch

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 - Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the
   distribution.

 - Neither the name 'Sinalgo' nor the names of its contributors may be
   used to endorse or promote products derived from this software
   without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package projects.UAV_Surveillance.nodes.nodeImplementations;


import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.TreeSet;

import projects.UAV_Surveillance.nodes.messages.msgFOV;
import projects.UAV_Surveillance.nodes.messages.msgKingImp;
import projects.UAV_Surveillance.nodes.messages.msgPOIordered;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.io.eps.EPSOutputPrintStream;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.runtime.Runtime;
import sinalgo.tools.Tools;
import sinalgo.tools.logging.Logging;
import sinalgo.nodes.messages.Message;


public class UAVnode extends Node implements Comparable<UAVnode> {

	//@Oli: Our vars
	public int nKnownPOIs = 0;
	public int nKnownUAVs = 0;	
	
	// To control execution flow 
	private boolean visitedAllPOIs = false; // flag set to true when this node has most neighbors
	private boolean mappedPOIs = false;
	public String myMobilityModelName;
	public boolean receivedWayPoints = false;
	public boolean canImove = true;
	
	// The set of nodes this node has already seen
	private TreeSet<POInode> visitedPOIs = new TreeSet<POInode>();
	
	// To control path
	public ArrayList<POInode> pathPOIs = new ArrayList<POInode>();
	public int pathIdx = 0 ; //this.ID -1;
	
	// To use with rendezvous and change paths
	public POInode lastPoi = new POInode();
	public POInode nextPoi = new POInode();
	public ArrayList<POInode> knownPOIs = new ArrayList<POInode>();

	
	// To mark visits
	private msgFOV msgPOIseen;

		

	//Logging uav_log = Logging.getLogger("UAV_id_" + this.ID + ".txt");
	
	/**
	 * Reset the list of neighbors of this node.
	 */
	public void reset() {
		visitedPOIs.clear();
		knownPOIs.clear();
		nKnownUAVs = 0;
		nKnownPOIs = 0;
		visitedAllPOIs = false;
	}
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void handleMessages(Inbox inbox) {
		
		//@Oli: msg with path arrived.
		while(inbox.hasNext()) {
			Message msg = inbox.next();
			if(msg instanceof msgPOIordered) {
				canImove = true; // releasing the trigger on legacyGetNextPos()
				msgPOIordered pathMsg = (msgPOIordered)msg;
				pathPOIs = pathMsg.data;		
				knownPOIs = pathMsg.data;
			}
			
			// Rendezvous to balance paths
			
			if (msg instanceof msgKingImp) {
				
				msgKingImp tmpMsg = (msgKingImp) msg;				
				System.out.println( this.ID + " recebeu msg de " + tmpMsg.fromID);
				
				boolean doTheBalance = true;
				
				if (doTheBalance){
					
					if ((this.nextPoi == tmpMsg.lastPoi)&&(this.lastPoi == tmpMsg.nextPoi)){ // So, it is comming from where I am going... 
						System.out.println("deveria haver balanceamento");
						
						// PAREI AQUI
						// cria objeto só para isso...
						// envia tudo que sabe
						// recebe novas orientações
						
						
						
					} //else
						//System.out.println(">>>NÃO<<< deveria haver balanceamento");				
				}
			}
			
		}
		
	}

	@Override
	public void init() {		
			
		msgPOIseen = new msgFOV(this.ID);
		
		//@Oli: all strategies but Random one.
		if (!receivedWayPoints) {
			// Waiting GS instructions
			myMobilityModelName = this.mobilityModel.toString();		
			if (!myMobilityModelName.endsWith("RandomSafeMobility")){
				canImove = false;
			}
		}
	}

	@Override
	public void neighborhoodChange() {
		for(Edge e : this.outgoingConnections){
			if (e.endNode instanceof POInode){
				if (!visitedPOIs.contains((POInode) e.endNode)){
					visitedPOIs.add((POInode) e.endNode); // only adds really new POIs
				}
			} 
			
			// that is other UAV, rendezvous
			if (e.endNode instanceof UAVnode){		
				msgKingImp msgKing2send = new msgKingImp(pathPOIs, this.ID, lastPoi, nextPoi);
				sendDirect(msgKing2send, e.endNode);
			}
		}
	}

	@Override
	public void preStep() {			
			
		broadcast(msgPOIseen);	
		
		//@Oli: we can not do it on Init() because does not works... BUG?
		if (!mappedPOIs){		
			for(Node n : Runtime.nodes) {
				if (n instanceof POInode)
					nKnownPOIs++;
			}						
			mappedPOIs = true;
		}
		
		if((visitedPOIs.size() >= nKnownPOIs)  && (nKnownPOIs !=0) ) { 
			visitedAllPOIs = true;
		} else {
			visitedAllPOIs = false;
		}	
	}

	@Override
	public void postStep() {
		

	}
	
	@Override
	public String toString() {
		return "This node has seen "+visitedPOIs.size()+" neighbors during its life.";
	}
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#draw(java.awt.Graphics, sinalgo.gui.transformation.PositionTransformation, boolean)
	 */
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		// Set the color of this node depending on its state
		
		this.drawingSizeInPixels = 5 ; 
		
		if(visitedAllPOIs) {
			this.setColor(Color.GREEN);
		} else {
			this.setColor(Color.BLUE);
		}
	
		// default:
		drawAsDisk(g, pt, highlight, this.drawingSizeInPixels);
		
		// debug: 
		//this.setColor(Color.BLACK);
		//String text = this.ID + "|" + Integer.toString(pathIdx);
		//this.drawingSizeInPixels = 10 ; 
		//super.drawNodeAsDiskWithText(g, pt, highlight, text, 10, Color.YELLOW);
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
	public int compareTo(UAVnode tmp) {
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
