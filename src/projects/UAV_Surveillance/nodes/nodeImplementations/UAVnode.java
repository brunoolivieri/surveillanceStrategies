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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import projects.UAV_Surveillance.nodes.messages.msgFOV;
import projects.UAV_Surveillance.nodes.messages.msgFromPOI;
import projects.UAV_Surveillance.nodes.messages.msgKingImp;
import projects.UAV_Surveillance.nodes.messages.msgPOIordered;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.io.eps.EPSOutputPrintStream;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.runtime.Global;
import sinalgo.runtime.Runtime;
import sinalgo.tools.Tools;
import sinalgo.tools.logging.Logging;
import sinalgo.nodes.messages.Message;


public class UAVnode extends Node implements Comparable<UAVnode> {

	//@Oli: Our vars
	public int nKnownPOIs = 0;
	public int nKnownUAVs = 0;	
	public POInode tmpPOI = new POInode(); 
	public long rounds = 0;
	POInode poiTmp = new POInode();
	//public int UAVnodeOrder = 0;
	private double FULLBATTERY = 16 * 3600;
	private double battery = FULLBATTERY; // mah? test to spend 1mah/ second? 

	
	// To control execution flow 
	public boolean visitedAllPOIs = false; // flag set to true when this node has most neighbors
	private boolean mappedPOIs = false;
	public String myMobilityModelName;
	public boolean receivedWayPoints = false;
	public boolean canImove = true; // if everything good to run...	
	public boolean justCountRoundsToMove= false; // trick to release canImove just after enough space between UAVs.	
	public double distToGS = 0;
	public boolean init = false;
	public int roundsToMove = 0;
	
	// The set of nodes this node has already seen
	public TreeSet<POInode> visitedPOIs = new TreeSet<POInode>();
	
	// To control path
	public ArrayList<POInode> pathPOIs = new ArrayList<POInode>();
	private  int pathIdx = 0 ; //this.ID -1;
	private int pathSize = 0;
	
	// [Kingston/ZigZag]To use with rendezvous and change paths
	public GSnode myGS = new GSnode();
	public boolean kingImpAllowed = false; // just to enable conditions to start changing, such as minimum visited POIs or UAVs
	public POInode lastPoi = new POInode();
	public POInode nextPoi = new POInode();
	public int knownUAVright = 0;
	public int knownUAVleft = 0;
	public ArrayList<POInode> pathOriginal = new ArrayList<POInode>();
	public KingImpReplanner replanner = new KingImpReplanner();
	public TreeSet<POInode> roundVisitedPOIs = new TreeSet<POInode>();
	public boolean roundVisitedAllPOIs = false; 
	public boolean shawResetMoviment = false;
	public int posOnSwarm = 0;
	public boolean adjustingPath = false;
	public POInode adjustingPoiTarget = new POInode(); 
	public int lastPoiOnMyPathPortion=0;
	public int firstPoiOnMyPathPortion=0;
	
	// To mark visits
	private msgFOV msgPOIseen;
	
	private ArrayList<msgFromPOI> poiMessages = new ArrayList<msgFromPOI>();

	public enum STATUS {ALLRIGHT, BROKEN, REFUELPROCESS};
	public STATUS myStatus = STATUS.ALLRIGHT;

	
	

	//Logging uav_log = Logging.getLogger("UAV_id_" + this.ID + ".txt");
	
	/**
	 * Reset the list of neighbors of this node.
	 */
	public void reset() {

		//System.out.println("[UAV " + this.ID + "] recharging...");
		myStatus = STATUS.ALLRIGHT;     //good to work
		justCountRoundsToMove = true;   // because already receive the list of pois.
		roundsToMove = 0;				// rounds waiting
		canImove = false; 				// some exception relatead to randomWayPoint. Turns TRUE in init()
		battery = FULLBATTERY;				// new battery pack
		
		pathIdx = 0;					// start from begining, but need to prepare the orignal order.
		pathPOIs.clear();
		pathPOIs = (ArrayList<POInode>) pathOriginal.clone();
		
		//visitedPOIs.clear();
		//roundVisitedPOIs.clear();
		//nKnownPOIs = 0;					// load on init() 
		//nKnownUAVs = 0;					// load on init()
		//visitedAllPOIs = false; 		// flag set to true when this node has most neighbors
		//mappedPOIs = false;				// load on init()
		//distToGS = 0;					// because I am here
		//init = false;					// because it just reborn
		//pathSize = getPathSize(pathOriginal); // because I don´t know the size
		
		kingImpAllowed = false; 		// just to enable conditions to start changing, such as minimum visited POIs or UAVs
		lastPoi = pathOriginal.get(1);
		nextPoi = pathOriginal.get(0);
		knownUAVright = 0;
		knownUAVleft = 0;
		//roundVisitedAllPOIs = false; 	// this is important to KIMP and ZZ
		//shawResetMoviment = false;
		posOnSwarm = 0;
		adjustingPath = false;
		lastPoiOnMyPathPortion = 0;
		firstPoiOnMyPathPortion = 0;
		
		//init();
		//preStep();
		
		//System.out.println("[UAV " + this.ID + "] Finish to repack.");
		//System.out.print("[UAV " + this.ID + "] Good to go with Path: ");
		//for (int i = 0; i<pathPOIs.size(); i++){
		//	System.out.print(pathPOIs.get(i).ID + " -> ");
		//}
		

	}
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void handleMessages(Inbox inbox) {
		
		//@Oli: msg with path arrived.
		while(inbox.hasNext()) {
			Message msg = inbox.next();
			
			if (myStatus == STATUS.ALLRIGHT){   // means working properly
				// saving msg from POIs - collecting data
				if(msg instanceof msgFromPOI) {		
					msgFromPOI dataReceived = (msgFromPOI)msg;
					if (dataReceived.recipient == this.ID) {  // because POI sends a broadcast
						poiMessages.add((msgFromPOI) dataReceived.clone());
					}				
				}
				// msg which has ORIGINAL PATH
				if(msg instanceof msgPOIordered) {		
					justCountRoundsToMove = true; // releasing the trigger on legacyGetNextPos()
					msgPOIordered pathMsg = (msgPOIordered)msg;				
					pathPOIs = (ArrayList<POInode>) pathMsg.data.clone();	// .clone() THIS KEEP ME SOMETIME!  missing ansiC			
					pathOriginal = (ArrayList<POInode>)pathMsg.data.clone();
					
					pathSize = getPathSize(pathOriginal);
					
					System.out.print("[UAV " + this.ID + "] Good to go with original Path: ");
					for (int i = 0; i<pathPOIs.size(); i++){
						System.out.print(pathPOIs.get(i).ID + " - ");
					}
					System.out.println(" \n\n\n");
				}			
				// Rendezvous to balance paths // or ZZ rendezvous
				if (msg instanceof msgKingImp) {	
					
					if (kingImpAllowed){ // just to enable conditions to start changing, such as minimum visited POIs or UAVs (Originaly = 3)			
							
						msgKingImp tmpMsg = (msgKingImp)msg.clone();
						
						//System.out.println("[UAV " + this.ID + "] received msg from " + tmpMsg.fromID);
						
						if (((this.nextPoi.ID == tmpMsg.lastPoi.ID)&&(this.lastPoi.ID == tmpMsg.nextPoi.ID))
								|| (tmpMsg.nextPoi.ID == this.pathOriginal.get(0).ID)
								|| ((this.nextPoi.ID == tmpMsg.nextPoi.ID))// &&(this.lastPoi.ID == tmpMsg.lastPoi.ID))
								) { // So, it is comming from where I am going... // Would be better if inserted in that class
						
							//System.out.println("[UAV " + this.ID + "] could balance with " + tmpMsg.fromID);
																							
							// ZigZag
							if ((this.myMobilityModelName.endsWith("ZigZagOverNaiveMobility"))||(this.myMobilityModelName.endsWith("ZigZagOverNSNMobility"))){								
					
								meiaVoltaVolver();
								
								// just clonning from KIMP to test...
								replanner.updateData(this.pathPOIs, tmpMsg, this.lastPoi, this.nextPoi, pathOriginal, this.ID, knownUAVright, knownUAVleft);
								if ((replanner.amIrightUav)&&(Global.isV2V2GS)){
									sendDataToLeftPal(tmpMsg.fromID);
								}
								
								
							} else {
								// Not ZigZag, but KingstonImproved At all! 
								// KingstonImprovedOverNSNMobility
								
								replanner.updateData(this.pathPOIs, tmpMsg, this.lastPoi, this.nextPoi, pathOriginal, this.ID, knownUAVright, knownUAVleft);
								
								// geting my theorical position and update other UAV counter.
								posOnSwarm = replanner.myPositionOnSwarm; 
								knownUAVleft = replanner.myKnownLeft;   // debug more?
								knownUAVright = replanner.myKnownRight; // debug more?	
								this.lastPoiOnMyPathPortion = replanner.myLastPoiOnMyPathPortion;
								this.firstPoiOnMyPathPortion = replanner.myFirstPoiOnMyPathPortion;
								
								
								if (!adjustingPath){
																
									this.adjustingPath = true;
						
									// If I am from right, give my data to left uav deliver/ hand on. IFF is V2V case and not V2I
									if ((replanner.amIrightUav)&&(Global.isV2V2GS)){
										sendDataToLeftPal(tmpMsg.fromID);
									}
									
									
									//	replanner.rebalancePath();
																	
									if (replanner.amIleftUav){							
										if (lastPoiOnMyPathPortion < getIdxFromPoi(nextPoi, pathOriginal)) {
											adjustingPoiTarget = pathOriginal.get(firstPoiOnMyPathPortion);	
											//System.out.println("\n[UAV " + this.ID + "] RIGHT MVV() and setting adjustingPoiTarget =  " + adjustingPoiTarget.ID  + " last= " + pathOriginal.get(lastPoiOnMyPathPortion).ID + " next= " + nextPoi.ID);
											meiaVoltaVolver();
										} else {
											adjustingPoiTarget = pathOriginal.get(lastPoiOnMyPathPortion);	
											//System.out.println("\n[UAV " + this.ID + "] RIGHT Just setting adjustingPoiTarget =  " + adjustingPoiTarget.ID);
				     					}							
									} else {									
										if (replanner.amIrightUav){
											if (firstPoiOnMyPathPortion > getIdxFromPoi(nextPoi, pathOriginal)) {
												adjustingPoiTarget = pathOriginal.get(lastPoiOnMyPathPortion);	
											//	System.out.println("\n[UAV " + this.ID + "] RIGHT MVV() and setting adjustingPoiTarget =  " + adjustingPoiTarget.ID + " first= " + pathOriginal.get(firstPoiOnMyPathPortion) + " next= " + nextPoi.ID);
												meiaVoltaVolver();										
											} else {
												adjustingPoiTarget = pathOriginal.get(firstPoiOnMyPathPortion);	
												//System.out.println("\n[UAV " + this.ID + "] RIGHT Just setting adjustingPoiTarget =  " + adjustingPoiTarget.ID);
					     					}									
										}  else {
											//System.out.println("\n[UAV " + this.ID + "] I dont know how share paths in this rendezvous. Who am I? ");
										}
									}
								} else { 
									//System.out.println("\n[UAV " + this.ID + "] should NOT adjust because still adjusting...");	
								}
							}															
						} else {//else not crossing paths
	
							//System.out.println("[UAV " + this.ID + "] should NOT balance with " + tmpMsg.fromID + " myNext= " + this.nextPoi.ID + " his last=" + tmpMsg.lastPoi.ID + " myLast= " + this.lastPoi.ID + " his next= " + tmpMsg.nextPoi.ID);
	
							}
					}
				}
			}
		}
		
	}
	
	private int getPathSize(ArrayList<POInode> path) {
		
		POInode thisPOI;
		POInode lastPOI;
		int size = 0;
	
		for (int i = 1; i< path.size(); i++){
			thisPOI = (POInode)path.get(i);
			lastPOI = (POInode)path.get(i-1);
			size += (int) Math.sqrt(
				             (thisPOI.getPosition().xCoord - lastPOI.getPosition().xCoord) *  (thisPOI.getPosition().xCoord - lastPOI.getPosition().xCoord) + 
				             (thisPOI.getPosition().yCoord - lastPOI.getPosition().yCoord) *  (thisPOI.getPosition().yCoord - lastPOI.getPosition().yCoord)
				              );
		}
		return size;
	}

	// does 180 degrees on linear path, turn, do a zig-zag
	public void meiaVoltaVolver() {
	
		Collections.reverse(pathPOIs);					
		
		int tmpPathIdx = pathIdx;							
		pathIdx = getIdxFromPoi(lastPoi, pathPOIs);
		this.shawResetMoviment = true;
	
		roundVisitedPOIs.clear();							
		for (int i=pathIdx-1; i>=0; i--) {
			poiTmp = pathPOIs.get(i);
			roundVisitedPOIs.add(poiTmp);
		}		
	}

	private int getIdxFromPoi(POInode testPoi, ArrayList<POInode> pathPOIs) {
		
		for (int i=0; i< pathPOIs.size(); i++) {
			poiTmp = pathPOIs.get(i);
			if (poiTmp.ID == testPoi.ID)				
				return i;
		}	
		System.out.print("[UAV " + this.ID + "]\t ERROR from getIdxFromPoi() - Inexistent POI on path.\n\n");
		return 0;
		
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
		if (!init){
			distToGS = (int) Math.sqrt(
		            (this.getPosition().xCoord - 0) *  (this.getPosition().xCoord - 0) + 
		            (this.getPosition().yCoord - 0) *  (this.getPosition().yCoord - 0)
		        );
			init = true;
			System.out.println("[UAV " + this.ID + "] dist to zero = " + this.distToGS);
		}		
	}

	@Override
	public void neighborhoodChange() {
		for(Edge e : this.outgoingConnections){			
			if (myStatus == STATUS.ALLRIGHT){  // means working properly
					if (e.endNode instanceof POInode){
						// This is for all bases
						if (!visitedPOIs.contains((POInode) e.endNode)){
							visitedPOIs.add((POInode) e.endNode); // only adds really new POIs
						}				
						// This is for KingStonImproved & ZigZag
						if (!roundVisitedPOIs.contains((POInode) e.endNode)){
							roundVisitedPOIs.add((POInode) e.endNode); // only adds really new POIs
								kingImpAllowed = true;	
						}					
					} if (e.endNode instanceof GSnode){ // && (V2Venable instead V2I) 
										
						myGS = (GSnode) e.endNode;
						
						POInode fakePOI = new POInode();
						fakePOI.setPosition(((GSnode) myGS).getPosition());
						
						if (!roundVisitedPOIs.contains((POInode) fakePOI)){
							roundVisitedPOIs.add((POInode) fakePOI); // only adds really new POIs
							if (roundVisitedPOIs.size() > 3)
								kingImpAllowed = true;	
						}
						// delivery accumulated data
						if (Global.isV2V2GS){
							sendDataToGS(myGS);			
						}				
					}
					// that is other UAV, rendezvous
					if (e.endNode instanceof UAVnode){		
						
						if (((this.myMobilityModelName.endsWith("ZigZagOverNaiveMobility"))||
								(this.myMobilityModelName.endsWith("ZigZagOverNSNMobility"))||
								(this.myMobilityModelName.endsWith("KingstonImprovedOverNSNMobility"))||
								(this.myMobilityModelName.endsWith("KingstonImprovedOverNaiveMobility"))
								)&&(!adjustingPath)){		
		
							msgKingImp msgKing2send = new msgKingImp(pathPOIs, this.ID, lastPoi, nextPoi, knownUAVright, knownUAVleft);
							sendDirect(msgKing2send, e.endNode);						
						}
					}
			}
		}
	}

	private void sendDataToGS(GSnode myGS){

		// quick setting each msg recipient from the UAV to the GS
		poiMessages.forEach((a)->a.recipient=myGS.ID);
		// quick sending all of them to the GS
		poiMessages.forEach((a)->broadcast(a));
		// erasing the UAV stored message
		poiMessages.clear();
	}
	
	private void sendDataToLeftPal(int leftPal){

		// quick setting each msg recipient from the UAV to the GS
		poiMessages.forEach((a)->a.recipient=leftPal);
		// quick sending all of them to the GS
		poiMessages.forEach((a)->broadcast(a));
		// erasing the UAV stored message
		poiMessages.clear();
	}
	
	
	
	
	@Override
	public void preStep() {			
			
		if (myStatus == STATUS.ALLRIGHT)   // means working properly
			broadcast(msgPOIseen);	// to POIs discover if they are under visit

		//@Oli: we can not do it on Init() because does not works... BUG?
		if (!mappedPOIs){		
			for(Node n : Runtime.nodes) {
				if (n instanceof POInode)
					nKnownPOIs++;
				if (n instanceof UAVnode)
					nKnownUAVs++;
			}						
			mappedPOIs = true;
			
			// IF V2V do that: (because GS is in the list to be visited.
			nKnownPOIs++;

		}
		
		if((visitedPOIs.size() >= nKnownPOIs)  && (nKnownPOIs !=0) ) { 
			visitedAllPOIs = true;
		} else {
			visitedAllPOIs = false;
		}	
			
		if((roundVisitedPOIs.size() >= pathPOIs.size())  && (nKnownPOIs !=0) && canImove) { 
			roundVisitedAllPOIs = true;
			//System.out.println("\n\n CHEGUEI em todos ainda");
		} 
		 else {
			roundVisitedAllPOIs = false;
		}

		
	}

	@Override
	public void postStep() {
		rounds++; // FEIO!!!
		
		if (myStatus == STATUS.ALLRIGHT){   // means working properly
			
			if (justCountRoundsToMove){ 
				roundsToMove++;
				if (roundsToMove >= (this.nodeCreationOrder-1)*((int)(this.pathSize/nKnownUAVs)) ) { // This works with with SIZEOFPATH_DIVIDED_BY_ALL_UAV
				//if (roundsToMove >= 500)  { // This works with with SIZEOFPATH_DIVIDED_BY_ALL_UAV

					justCountRoundsToMove = false;
					canImove = true;
				}	
			}	
			// releasing UAV from Kingston adjustment
			if ((adjustingPath)&(lastPoi.ID == adjustingPoiTarget.ID)){
				adjustingPath = false;
				meiaVoltaVolver();
				roundVisitedPOIs.add(lastPoi);
				//System.out.println("[UAV " + ID + "] released from adjustment and doing Meia-Volta-Voler & adjustingPath= " + adjustingPath);
			} else {
				//System.out.println("[UAV " + v.ID + "] NOT released from adjustment: last= " + v.lastPoi.ID + " adjustingPoiTarget= " + v.adjustingPoiTarget.ID + " & adjustingPath= " + v.adjustingPath);
			}
			
			
			
			if (Global.UAVsShouldBreak){
				// break UAV code:
				double failTax = 0.2; // 20%
				int failPeriod = 20    *3600; // MTBF (mean time between failures) -- USED JUST FOR FAILURES // 20 horas
				// failTax % of all UAVs fail with intervals of failPeriod from the start of simulation... || could be better
				if ((rounds >= failPeriod*this.nodeCreationOrder)&&(this.nodeCreationOrder <= (nKnownUAVs*failTax))){
					System.out.println("[UAV " + this.ID + "] is Disabled. OUT OF WORK FOR GOOD.");
					myStatus = STATUS.BROKEN;
					this.canImove = false;
				}
			}
		}
		
		if (Global.UAVneedsRecharges) {
			if ((myStatus == STATUS.ALLRIGHT)&&(canImove)&&(!justCountRoundsToMove))		
				battery -= 1; // a second of battery
			
			if ((battery <= 0)&&(myStatus != STATUS.REFUELPROCESS)){ // going to emergengy battery...
				myStatus = STATUS.REFUELPROCESS;
				//System.out.println("[UAV " + this.ID + "] is without Battery. Going to repack with a new battery.");
			}
			
			// recharge stuff...
			if ((myStatus == STATUS.REFUELPROCESS)&&(this.getPosition().equals(myGS.getPosition()))){ // going to emergengy battery...
				//System.out.println("[UAV " + this.ID + "] Arrived on GS repack with a new battery.");
				this.reset();
			}
			
		}
		
	}
	
	@Override
	public String toString() {
		return "This node has seen "+visitedPOIs.size()+" neighbors during its life.";
	}
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#draw(java.awt.Graphics, sinalgo.gui.transformation.PositionTransformation, boolean)
	 */
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {

		this.drawingSizeInPixels = 15 ; 
		
		if (myStatus == STATUS.ALLRIGHT){   // means working properly
			
			if(visitedAllPOIs) {
				this.setColor(Color.GREEN);
			} else {
				this.setColor(Color.BLUE);
			}	
			//String text = Integer.toString(this.ID) + "|" + poiMessages.size() ; 	
			String text = Integer.toString(this.nodeCreationOrder) + "|" + poiMessages.size() ; 
			
			super.drawNodeAsDiskWithText(g, pt, highlight, text, 15, Color.YELLOW);
		} else if (myStatus == STATUS.BROKEN){   // means not working
			
			//String text = Integer.toString(this.ID) + "|" + poiMessages.size() ; 	
			String text = Integer.toString(this.nodeCreationOrder) + "|" + poiMessages.size() ; 
			
			this.setColor(Color.RED);
			super.drawNodeAsDiskWithText(g, pt, highlight, text, 15, Color.BLACK);
			
		}else if (myStatus == STATUS.REFUELPROCESS){   // means not working
			
			//String text = Integer.toString(this.ID) + "|" + poiMessages.size() ; 	
			String text = Integer.toString(this.nodeCreationOrder) + "|" + poiMessages.size() ; 
			
			this.setColor(Color.YELLOW);
			super.drawNodeAsDiskWithText(g, pt, highlight, text, 15, Color.BLACK);
			
		}
		

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

	public synchronized int getPathIdx() {
		return pathIdx;
	}

	public synchronized void setPathIdx(int pathIdx) {
		this.pathIdx = pathIdx;
	}

		
}

