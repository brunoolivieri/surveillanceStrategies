package projects.UAV_Surveillance.models.mobilityModels;

import java.util.Random;

import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode;
import projects.UAV_Surveillance.nodes.nodeImplementations.UAVnode.STATUS;
import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.io.mapIO.Map;
import sinalgo.models.MobilityModel;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;
import sinalgo.runtime.Main;
import sinalgo.tools.Tools;
import sinalgo.tools.logging.Logging;
import sinalgo.tools.statistics.Distribution;


public class NaiveOrderedMobility  extends MobilityModel{

	//@Oli: Legacy code from sinalgo
	// we assume that these distributions are the same for all nodes
	protected static Distribution speedDistribution;
	protected static Distribution waitingTimeDistribution;

	private static boolean initialized = false; // a flag set to true after initialization of the static vars of this class has been done.
	protected static Random random = Distribution.getRandom(); // a random generator of the framework 
		
	protected Position nextDestination = new Position(); // The point where this node is moving to
	protected Position moveVector = new Position(); // The vector that is added in each step to the current position of this node
	protected Position currentPosition = null; // the current position, to detect if the node has been moved by other means than this mobility model between successive calls to getNextPos()
	protected int remaining_hops = 0; // the remaining hops until a new path has to be determined
	protected int remaining_waitingTime = 0;
	//-------------
	
	//Logging uav_log = Logging.getLogger("UAV_NaiveOrderedMobility.txt");

	

	
	
	//@Oli: Legacy constructor
	public NaiveOrderedMobility() throws CorruptConfigurationEntryException {
		if(!initialized) {
			speedDistribution = Distribution.getDistributionFromConfigFile("RandomWayPoint/Speed");
			waitingTimeDistribution = Distribution.getDistributionFromConfigFile("RandomWayPoint/WaitingTime");
			initialized = true;
		}
	}

	//@Oli: Trick
	public String toString(){
		return this.getClass().getCanonicalName();
	};
	
	//@oli: Get next position avoiding obstacles.
	// NOT TESTED WITH OBSTACLES YET
	public synchronized Position getNextPos(Node n){
		Map map = Tools.getBackgroundMap();
		Position newPos = new Position();
		boolean nonFlyZone = false;
		if(Configuration.useMap){
			nonFlyZone = !map.isWhite(n.getPosition());  //we are already standing in the lake
		}
		if(nonFlyZone){
			Main.fatalError("A node is standing in a non flying zone. Cannot find a step outside.");
		}
		do{
			nonFlyZone = false;
			newPos = legacyGetNextPos(n);
			if(Configuration.useMap){
				if(!map.isWhite(newPos)) {
					nonFlyZone = true;
					remaining_hops = 0;//this foces the node to search for an other target...
				}
			}
		}	while(nonFlyZone);
		
		return newPos;
	}
	
	
	public synchronized Position legacyGetNextPos(Node n) {
		
		//@Oli: RUDE and temporary way to do not move until a condition
		UAVnode v = (UAVnode)n;		
		if (!v.canImove){
			return v.getPosition();
		} 		
				
		// restart a new move to a new destination if the node was moved by another means than this mobility model
		if(currentPosition != null) {
			if(!currentPosition.equals(n.getPosition())) {
				remaining_waitingTime = 0;
				remaining_hops = 0;
			}
		} else {
			currentPosition = new Position(0, 0, 0);
		}
		
		Position nextPosition = new Position();
		
		// execute the waiting loop
		if(remaining_waitingTime > 0) {
			remaining_waitingTime --;
			return n.getPosition();
		}

		if(remaining_hops == 0) {
			// determine the speed at which this node moves
			double speed = Math.abs(speedDistribution.nextSample()); // units per round

			// determine the next point where this node moves to
			//nextDestination = legacyGetNextWayPoint();   // <***************************************************************************
			nextDestination = GetNextWayPoint(v); // new one. HERE IS WHERE NAVIGATES BY UAV POIs LIST
			
			// determine the number of rounds needed to reach the target
			double dist = nextDestination.distanceTo(n.getPosition());
			double rounds = dist / speed;
			remaining_hops = (int) Math.ceil(rounds);
			// determine the moveVector which is added in each round to the position of this node
			double dx = nextDestination.xCoord - n.getPosition().xCoord;
			double dy = nextDestination.yCoord - n.getPosition().yCoord;
			double dz = nextDestination.zCoord - n.getPosition().zCoord;
			moveVector.xCoord = dx / rounds;
			moveVector.yCoord = dy / rounds;
			moveVector.zCoord = dz / rounds;
		}
		if(remaining_hops <= 1) { // don't add the moveVector, as this may move over the destination.
			nextPosition.xCoord = nextDestination.xCoord;
			nextPosition.yCoord = nextDestination.yCoord;
			nextPosition.zCoord = nextDestination.zCoord;
			// set the next waiting time that executes after this mobility phase
			remaining_waitingTime = (int) Math.ceil(waitingTimeDistribution.nextSample());
			remaining_hops = 0;
		} else {
			double newx = n.getPosition().xCoord + moveVector.xCoord; 
			double newy = n.getPosition().yCoord + moveVector.yCoord; 
			double newz = n.getPosition().zCoord + moveVector.zCoord; 
			nextPosition.xCoord = newx;
			nextPosition.yCoord = newy;
			nextPosition.zCoord = newz;
			remaining_hops --;
		}
		currentPosition.assign(nextPosition);
		return nextPosition;
		
	}
	
	//@Oli: set target as next POI in each list from each UAV
	protected synchronized Position GetNextWayPoint(UAVnode v) {
			
		if (v.myStatus == STATUS.REFUELPROCESS){ // going to refuel
			
			return v.myGS.getPosition();
			
		} else {		
			POInode p = v.pathPOIs.get(v.getPathIdx());
			
			//System.out.println("v " + v.ID + " indo para p " + p.ID);
			
			double randx = p.getPosition().xCoord; 
			double randy = p.getPosition().yCoord; 
			double randz = 0;
		
			v.setPathIdx(v.getPathIdx() + 1); // = v.pathIdx + v.ID;
			v.setPathIdx(v.getPathIdx() % v.pathPOIs.size());
	
			return new Position(randx, randy, randz);
		}
	}
	
	
}

