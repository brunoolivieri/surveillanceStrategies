package projects.UAV_Surveillance.nodes.nodeImplementations;


import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import projects.UAV_Surveillance.nodes.messages.msgFOV;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.io.eps.EPSOutputPrintStream;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.runtime.Global;
import sinalgo.runtime.Runtime;
import sinalgo.tools.Tools;
import sinalgo.nodes.messages.Message;
import projects.UAV_Surveillance.nodes.messages.msgFromPOI;



public class POInode extends Node implements Comparable<POInode>, Serializable {

    private static final long serialVersionUID = -4012494570376706631L; // trick: No idea why: After first version of FPPWR  its had problems regarding serialization and read from file...

	//private static int maxNeighbors = 0; // global field containing the max number of neighbors any node ever had
	
	//private boolean isMaxNode = false; // flag set to true when this node has most neighbors
	//private boolean drawAsNeighbor = false; // flag set by a neighbor to color specially
	
	public int roundsNeglected = 0;
	public int roundsRunning = 0;
	//public int lastUAVvisit = 0;

	public int distToGS = 0;
	private boolean init = false;
	public boolean amIphantom = false;
	// The set of nodes this node has already seen
	private TreeSet<UAVnode> neighbors = new TreeSet<UAVnode>();
	public boolean drawAsNeighbor;
	
	// To mark visits
	private msgFromPOI msgToUAV;
	
	
	// duplicate fields because it is Serializable but NODE doesnt. These field belong to NODE.
	public int myID;
	public Position myPos;
	public boolean flagFPPWR = false;
	public int fppwrDistToSquareRoot = -1;
	
	public transient BufferedImage img = null; // to draw POI/sensor   //transient because for the videos, I put an image
	
	
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
		if (!amIphantom){
			while(inbox.hasNext()) {
				Message msg = inbox.next();
				if(msg instanceof msgFOV) {
					if (!this.amIphantom)
						sendDataToUAV(msg);
				}
			}
		}
	}
	
	private void sendDataToUAV(Message msg){
		//System.out.println("[POI " + this.ID + "] received msg from " + ((msgFOV) msg).data);

		// manda dados pro UAV
		
		// por agora só mando SSS encher uma msg inteira, se não.. nem vai.
		if (roundsNeglected>=5) {			
			roundsNeglected -= 5; // upload tax			
			msgToUAV = new msgFromPOI(5, roundsRunning, ((msgFOV) msg).data);	
			broadcast(msgToUAV);	// to POIs discover if they are under visit
		} 	
		
	}

	@Override
	public void init() {
		// calcs distance to GS in order to be sorted
		if (!init){
			
			myID = this.ID;
			myPos = this.getPosition();
			
			
			distToGS = (int) Math.sqrt(
		            (this.getPosition().xCoord - 0) *  (this.getPosition().xCoord - 0) + 
		            (this.getPosition().yCoord - 0) *  (this.getPosition().yCoord - 0)
		        );
			init = true;
			System.out.println("[POI " + this.ID + "] " + "dist to GS = " + this.distToGS);
			
			//super.init();
			try {
				InputStream in = null;
				in = new FileInputStream("src/" + Configuration.userProjectDir
						+ "/" + Global.projectName + "/" + "images/smallCH.png");
				if ((img = ImageIO.read(in)) == null) {
					throw new FileNotFoundException(
							"\n 'map.bmp' - This image format is not supported.");
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
			
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
		roundsRunning++;		
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
	
	private static int radius;
	{
		try {
			radius = (int) Configuration.getDoubleParameter("GeometricNodeCollection/rMax"); // mine
					//.getIntegerParameter("AntennaConnection/rMax"); //pink
					
		} catch (CorruptConfigurationEntryException e) {
			Tools.fatalError(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see sinalgo.nodes.Node#draw(java.awt.Graphics, sinalgo.gui.transformation.PositionTransformation, boolean)
	 */
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		
		this.setColor(Color.BLACK);
		//debug
		this.drawingSizeInPixels = 15 ; // (int) (fraction * pt.getZoomFactor() * this.defaultDrawingSizeInPixels);
		String text = Integer.toString(this.ID) ; // + "|" + Integer.toString(roundsNeglected);// + "|" + msgSentInThisRound;
		//String text = Integer.toString(this.nodeCreationOrder) ; // + "|" + Integer.toString(roundsNeglected);// + "|" + msgSentInThisRound;
		
		if (!amIphantom){
			//super.drawNodeAsDiskWithText(g, pt, highlight, text, 15, Color.YELLOW);	

			
			Color bckup = g.getColor();
			g.setColor(Color.BLACK);
			this.drawingSizeInPixels = (int) (defaultDrawingSizeInPixels * pt
					.getZoomFactor());
			// pink: super.drawAsDisk(g, pt, highlight, drawingSizeInPixels);
			//super.drawNodeAsDiskWithText(g, pt, highlight, text, 15, Color.YELLOW); // mine
			
			g.setColor(Color.GRAY);
			pt.translateToGUIPosition(this.getPosition());
			//int r = (int) (radius * pt.getZoomFactor());
			//g.drawOval(pt.guiX - r, pt.guiY - r, r * 2, r * 2);
			//g.setColor(bckup);

			/*// REMOVED FOR VIDEOS
			int imgWidth = 0;
			int imgHeight = 0;
			int[][] grid = null;
			imgWidth = img.getWidth();
			imgHeight = img.getHeight();
			grid = new int[imgWidth][imgHeight];
			// copy the image data
			for (int i = 0; i < imgWidth; i++) {
				for (int j = 0; j < imgHeight; j++) {
					grid[i][j] = img.getRGB(i, j);
				}
			}

			int iniX = (int) this.getPosition().xCoord - (imgWidth / 2);
			int iniY = (int) this.getPosition().yCoord - (imgHeight / 2);

			for (int i = iniX; i < imgWidth + iniX; i++) {
				for (int j = iniY; j < imgHeight + iniY; j++) {
					pt.translateToGUIPosition(i, j, 0); // top left corner of cell
					int topLeftX = pt.guiX, topLeftY = pt.guiY;
					pt.translateToGUIPosition((i + 1), (j + 1), 0); // bottom right
																	// corner of
																	// cell
					Color col = new Color(grid[i - iniX][j - iniY]);
					g.setColor(col);
					g.fillRect(topLeftX, topLeftY, pt.guiX - topLeftX, pt.guiY
							- topLeftY);
				}
			}
			*/
			
			
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
