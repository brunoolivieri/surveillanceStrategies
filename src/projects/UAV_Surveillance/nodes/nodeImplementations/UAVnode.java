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
	private boolean visitedAllPOIs = false; // flag set to true when this node has most neighbors
	private boolean mappedPOIs = false;
	public String myMobilityModelName;
	
	// The set of nodes this node has already seen
	private TreeSet<POInode> visitedPOIs = new TreeSet<POInode>();
	
	public ArrayList<POInode> pathPOIs = new ArrayList<POInode>();
	public int pathIdx = this.ID -1; 
	
	private msgFOV msgPOIseen;
	
	public boolean receivedWayPoints = false;
	public boolean canImove = true;

	//Logging uav_log = Logging.getLogger("UAV_id_" + this.ID + ".txt");
	
	/**
	 * Reset the list of neighbors of this node.
	 */
	public void reset() {
		visitedPOIs.clear();
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
		
		
		// hammer to KingstonImproved, inverting PATH
		
		// if did all POIs, invert POIs order
		if ((this.pathIdx == 0)&& this.visitedAllPOIs) {
			ArrayList<POInode> poiListTemp = new ArrayList<POInode>();
			poiListTemp = (ArrayList<POInode>)this.pathPOIs.clone();
			
			System.out.println("\n\n\n Removendo: ");
			
			POInode poiTmp = new POInode();
			
			//this.pathPOIs.clear();
			for (int i = poiListTemp.size() -1 ; i <= 0 ; i-- ){
				poiTmp = this.pathPOIs.get(i);
				System.out.println(" removing " + poiTmp.ID);
				this.pathPOIs.remove(i);
			}

			System.out.println("\n\n\n Adicionando: ");
			
			for (int i = poiListTemp.size() -1 ; i <= 0 ; i-- ){
				poiTmp = this.pathPOIs.get(i);
				System.out.println(" adding " + poiTmp.ID);
				this.pathPOIs.add(poiListTemp.get(i));
			}
			this.visitedAllPOIs = false;
			
		}
		///////
		
		
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
			//this.drawingSizeInPixels = 10; 
		} else {
			this.setColor(Color.BLUE);
		}
	
		drawAsDisk(g, pt, highlight, this.drawingSizeInPixels);
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
