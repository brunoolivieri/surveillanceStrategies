package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.util.ArrayList;

import sinalgo.nodes.Position;

public class fppwrSQUARE {

	public enum RelationType {BEGINNER, LEFTRIGHT, UPDOWN, FINISHER}  
	public ArrayList<POInode> innerSquarePOIs = new ArrayList<POInode>();
	public RelationType relation;
	public Position anchor;  //Sinalgo POSITION TYPE
	public int jID;
	
	public double xMin = -1;
	public double yMin = -1;							
	public double xMax = -1;
	public double yMax = -1;

	public fppwrSQUARE(){
		// TO-DO - create it decently
		
	}
	
	
}
