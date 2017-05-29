package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DadcaPartedPlanner {
	
	private static ArrayList<POInode> originalListOfPOIs = null;
	private static ArrayList<POInode> leftPOIs = new ArrayList<POInode>();
	private static ArrayList<POInode> rightPOIs = new ArrayList<POInode>();
	private static ArrayList<POInode> jointPOIs = new ArrayList<POInode>();
	POInode tmpGS = null;

	
	public DadcaPartedPlanner(int gsID, ArrayList<POInode> listOfPOIs) {
		originalListOfPOIs = listOfPOIs;
		POInode tmpPOI = null;
		for (int i=0; i < originalListOfPOIs.size(); i++) {
			tmpPOI = originalListOfPOIs.get(i);
			if (tmpPOI.ID != gsID){ //ignores GS
				if (tmpPOI.getPosition().xCoord >= tmpPOI.getPosition().yCoord) { //upper right side
					rightPOIs.add(tmpPOI);
				} else{ // down left side
					leftPOIs.add(tmpPOI);
				}
			} else {
				tmpGS = tmpPOI;
			}
			
		}
	}

	public ArrayList<POInode> getDadcaPartedNaiveSolution() {
		
		System.out.print("[DADCA-parted left side messy: ");		
		// show
		for (int i=0; i < leftPOIs.size(); i++) {
			System.out.print(leftPOIs.get(i).ID + " -> ");
		}
		System.out.println();
		leftPOIs = orderByDistGS(leftPOIs);
		System.out.print("[DADCA-parted left side ordered Naive: ");		
		for (int i=0; i < leftPOIs.size(); i++) {
			jointPOIs.add(leftPOIs.get(i));	
			System.out.print(leftPOIs.get(i).ID + " -> ");
		}
		System.out.println();
		// insert GS
		jointPOIs.add(tmpGS);		
		System.out.println("[DADCA-parted inserting GS id = " + tmpGS.ID);

		
		
		System.out.print("[DADCA-parted right side messy: ");		
		// show
		for (int i=0; i < rightPOIs.size(); i++) {
			System.out.print(rightPOIs.get(i).ID + " -> ");
		}
		System.out.println();		
		rightPOIs = orderByDistGS(rightPOIs);

		System.out.print("[DADCA-parted right side ordered Naive: ");		
		for (int i=0; i < rightPOIs.size(); i++) {
			System.out.print(rightPOIs.get(i).ID + " -> ");
		}
		System.out.println();

		Collections.reverse(rightPOIs);
		for (int i=0; i < rightPOIs.size(); i++) {
			jointPOIs.add(rightPOIs.get(i));	
		}
				
		System.out.print("[DADCA-parted right side ordered Naive: ");		
		for (int i=0; i < jointPOIs.size(); i++) {
			System.out.print(jointPOIs.get(i).ID + " -> ");
		}
		System.out.println();
		
		
		return jointPOIs;
	}

	public ArrayList<POInode> getDadcaPartedNSNSolution() {
		POInode tmpPOI2add = null;
		POInode tmpPOI4loops = null;
		ArrayList<POInode> tmpListOfPOIs = new ArrayList<POInode>();


		///////////////////////////////////////////////////////////////////////////////////////////
		System.out.print("[DADCA-parted left side messy: ");		
		// show
		for (int i=0; i < leftPOIs.size(); i++) {
			System.out.print(leftPOIs.get(i).ID + " -> ");
		}
		System.out.println();

		leftPOIs = orderByDistGS(leftPOIs);
		Collections.reverse(leftPOIs);
		
		tmpPOI2add = leftPOIs.get(0);
		tmpListOfPOIs.add(tmpPOI2add);
		leftPOIs.remove(0);
		
		double lastDistance = Integer.MAX_VALUE;
		int arrayIdx = -1;
		while (leftPOIs.size() > 0 ){
			for (int i =0; i < leftPOIs.size(); i++){
				tmpPOI4loops = leftPOIs.get(i);
				if (tmpPOI4loops.getPosition().distanceTo(tmpListOfPOIs.get(tmpListOfPOIs.size()-1).getPosition()) < lastDistance){
					arrayIdx = i;
					tmpPOI2add = leftPOIs.get(i);
					lastDistance = tmpPOI4loops.getPosition().distanceTo(tmpListOfPOIs.get(tmpListOfPOIs.size()-1).getPosition());
				}
			}			
			tmpListOfPOIs.add(tmpPOI2add);
			leftPOIs.remove(arrayIdx);
			lastDistance = Integer.MAX_VALUE;
			arrayIdx = -1;
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////
		Collections.reverse(tmpListOfPOIs);

		System.out.print("[DADCA-parted left side ordered NSN: ");				
		for (int i=0; i < tmpListOfPOIs.size(); i++) {
			jointPOIs.add(tmpListOfPOIs.get(i));	
			System.out.print(tmpListOfPOIs.get(i).ID + " -> ");
		}
		System.out.println();
		
		
		
		// insert GS
		jointPOIs.add(tmpGS);		
		System.out.println("[DADCA-parted inserting GS id = " + tmpGS.ID);

		
		////////////////////////////////////////////////////////////////////////////////////////////
		System.out.print("[DADCA-parted right side messy: ");		
		// show
		for (int i=0; i < rightPOIs.size(); i++) {
			System.out.print(rightPOIs.get(i).ID + " -> ");
		}
		System.out.println();

		rightPOIs = orderByDistGS(rightPOIs);
		Collections.reverse(rightPOIs);
		
		tmpListOfPOIs.clear();
		tmpPOI2add = rightPOIs.get(0);
		tmpListOfPOIs.add(tmpPOI2add);
		rightPOIs.remove(0);
		
		lastDistance = Integer.MAX_VALUE;
		arrayIdx = -1;
		while (rightPOIs.size() > 0 ){
			for (int i =0; i < rightPOIs.size(); i++){
				tmpPOI4loops = rightPOIs.get(i);
				if (tmpPOI4loops.getPosition().distanceTo(tmpListOfPOIs.get(tmpListOfPOIs.size()-1).getPosition()) < lastDistance){
					arrayIdx = i;
					tmpPOI2add = rightPOIs.get(i);
					lastDistance = tmpPOI4loops.getPosition().distanceTo(tmpListOfPOIs.get(tmpListOfPOIs.size()-1).getPosition());
				}
			}			
			tmpListOfPOIs.add(tmpPOI2add);
			rightPOIs.remove(arrayIdx);
			lastDistance = Integer.MAX_VALUE;
			arrayIdx = -1;
		}
		System.out.print("[DADCA-parted right side ordered NSN: ");				
		for (int i=0; i < tmpListOfPOIs.size(); i++) {
			jointPOIs.add(tmpListOfPOIs.get(i));	
			System.out.print(tmpListOfPOIs.get(i).ID + " -> ");
		}
		System.out.println();
		
		return jointPOIs;
		
	}
	
	private ArrayList<POInode> orderByDistGS(ArrayList<POInode> list2order) {
		ArrayList<POInode> tmpList = list2order;		
		Collections.sort(tmpList, new Comparator<POInode>(){
			public int compare(POInode p1, POInode p2){
				if(p1.distToGS > p2.distToGS) {  // bigger
					return -1;
				} else {
					if(p1.distToGS == p2.distToGS) {
						return 0;
					} else {
						return 1;
					}
				}
			  }
		}); 		
		return tmpList;	
	}



}
