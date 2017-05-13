package projects.UAV_Surveillance.nodes.nodeImplementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Position;

public class fppwrPlanner {
	
	
	
public ArrayList<POInode> fppwrPlathPlanner(ArrayList<POInode> originalListOfPois){
	
	// Get the horizontal radio range by the minimum range ////////////////////////
    int radioRange = -1;
	try {
		radioRange = (int) Configuration.getDoubleParameter("QUDG/rMax");
	} catch (CorruptConfigurationEntryException e) {
		e.printStackTrace();
	} 
	int squareSize = 2 * 2 * radioRange; // paper: 2 * 2R
	System.out.println("\n[FPPWR Planner] Radio range: " + radioRange);
	System.out.println("[FPPWR Planner] SquareSize: " + squareSize);

	///////////////////////////////////////////////////////////////////////////////
	
	// Getting the number os Squares //////////////////////////////////////////////
	int xMapSize = sinalgo.configuration.Configuration.dimX;
	int yMapSize = sinalgo.configuration.Configuration.dimY;

	System.out.println("[FPPWR Planner] xMapSize: " + xMapSize);
	System.out.println("[FPPWR Planner] yMapSize: " + yMapSize);		

	int nVerticalSquares = (int) (xMapSize / squareSize);
	if ((xMapSize % radioRange) !=0) // Iff there is a waste
		nVerticalSquares++;
	
	int nHorizontalSquares = (int) (yMapSize / squareSize);
	if ((yMapSize % radioRange) !=0) // Iff there is a waste
		nHorizontalSquares++;
	
	System.out.println("[FPPWR Planner] nVerticalSquares: " + nVerticalSquares);
	System.out.println("[FPPWR Planner] nHorizontalSquares: " + nHorizontalSquares);		
	
	///////////////////////////////////////////////////////////////////////////////

	
	// create array of squares ////////////////////////////////////////////////////
	int numSquares = nVerticalSquares * nHorizontalSquares;
	System.out.println("[FPPWR Planner] numSquares: " + numSquares);		
	
	ArrayList<fppwrSQUARE> squares = new ArrayList<fppwrSQUARE>();

	// Creates and classify each square ///////////////////////////////////////////
	int ctSquares = 0;
	int ctVertical = 0;
	int ctHorizontal = 0;
	int lastUPDOWN = 0;
	while (ctSquares < numSquares) {
		
		fppwrSQUARE squareTMP = new fppwrSQUARE(); // this should be in the class BUT is here just to test the first prototype
		squareTMP.jID = ctSquares;

		// setting square limits /////////////////////////////////////////////////////////////
		if ((ctHorizontal % 2) == 0){ // it means it is going from left to right
			
			squareTMP.xMin = ctVertical * squareSize;
			
			squareTMP.yMin = ctHorizontal * squareSize;				
			
			squareTMP.xMax = squareTMP.xMin + squareSize;
			squareTMP.yMax = squareTMP.yMin + squareSize;

			squareTMP.anchor.assign(squareTMP.xMin, squareTMP.yMin + (squareSize/2), 0);
			
		} else { // it is going from right to left
			
			squareTMP.xMin = (nVerticalSquares - ctVertical) * squareSize - squareSize;	 // tricky
			squareTMP.yMin = ctHorizontal * squareSize; 						// easy				
			
			squareTMP.xMax = squareTMP.xMin + squareSize; 	// easy
			squareTMP.yMax = squareTMP.yMin + squareSize; 	// easy
			
			squareTMP.anchor.assign(squareTMP.xMax, squareTMP.yMin + (squareSize/2), 0);
		} 
		///////////////////////////////////////////////////////////////////////////
		
		
		// Classifying the Square /////////////////////////////////////////////////
		if (ctSquares == 0) {
			squareTMP.relation = fppwrSQUARE.RelationType.BEGINNER;	
			ctVertical = ++ctVertical % nVerticalSquares;
		} else if (ctSquares == (numSquares -1)) {
			squareTMP.relation = fppwrSQUARE.RelationType.FINISHER; // would be faster put it in the end
		} else if (((ctSquares - nVerticalSquares) == lastUPDOWN)||(ctSquares == (nVerticalSquares-1))) {
			squareTMP.relation = fppwrSQUARE.RelationType.UPDOWN; // extremes but not beginner neither finisher
			lastUPDOWN = ctSquares;
			ctHorizontal++;	
			ctVertical = ++ctVertical % nVerticalSquares;
		} else {
			squareTMP.relation = fppwrSQUARE.RelationType.LEFTRIGHT; // none of up cases, so it is horizontal.
			ctVertical = ++ctVertical % nVerticalSquares;
		}
		////////////////////////////////////////////////////////////////////////////	
		

		// fitting POIs in Squares /////////////////////////////////////////////////
		ArrayList<POInode> squarePOIs = new ArrayList<POInode>();

		for (int k=0; k < originalListOfPois.size(); k++) { 
			POInode poiTMP = originalListOfPois.get(k);
		
			if (!poiTMP.flagFPPWR) {				
//				System.out.println("[GS " + this.ID + "] POI " + poiTMP.ID + " x = " + poiTMP.getPosition().xCoord 
//						+ " y = " + poiTMP.getPosition().yCoord 
//						+ " against " + "Min (" + squareTMP.xMin + "," + squareTMP.yMin + ")" 
//						+ " ... Max (" + squareTMP.xMax + "," + squareTMP.yMax + ")" );

				if ((poiTMP.getPosition().xCoord >= squareTMP.xMin)&&(squareTMP.xMax >= poiTMP.getPosition().xCoord)&&
						(poiTMP.getPosition().yCoord >= squareTMP.yMin)&&(squareTMP.yMax >= poiTMP.getPosition().yCoord)){
					
					// distance to use with sort phase
					poiTMP.fppwrDistToSquareRoot = (int) Math.sqrt(
				            (poiTMP.getPosition().xCoord - squareTMP.anchor.xCoord) *  (poiTMP.getPosition().xCoord - squareTMP.anchor.xCoord) + 
				            (poiTMP.getPosition().yCoord - squareTMP.anchor.yCoord) *  (poiTMP.getPosition().yCoord - squareTMP.anchor.yCoord)
				        );
					
					squarePOIs.add(poiTMP);	
					originalListOfPois.get(k).flagFPPWR = true;
					//System.out.println("[GS " + this.ID + "] POI " + poiTMP.ID + " added on J_" + squareTMP.jID);
				}	
			}
		}
		////////////////////////////////////////////////////////////////////////////
		
		squareTMP.innerSquarePOIs = (ArrayList<POInode>) squarePOIs.clone();
		squares.add(squareTMP);				
		ctSquares++;

	}
	
	// inner lists ////////////////////////////////////////////////////////////////////
	System.out.println("\n[FPPWR Planner] FPPWR buquet path: ");
	POInode poiTMP = null;
	fppwrSQUARE jota = null;	
	fppwrSQUARE jotaPRE = null;	
	
	POInode candHoutAlfa = null;
	POInode candHoutBeta = null;
	POInode candHinAlfa = null;
	POInode candHinBeta = null;

	
	ArrayList<POInode> resultPath = new ArrayList<POInode>();

	// sort and joinning them
	for(int m=0; m < squares.size(); m++ ){
		
		jota = squares.get(m);
		if (m >=1)
			jotaPRE = squares.get(m-1);

				
		//if (jota.innerSquarePOIs.size() >0 ){
		//	System.out.print("\n[GS " + this.ID + "] J_" + jota.jID + " flagged as " + jota.relation.toString() 
		//	+ " at (" + jota.xMin + ", " + jota.yMin + ") to (" + jota.xMax + ", " + jota.yMax + ") :"   );

		
		// SORT  // on early phase some tests showed that worked well do reduce something of trajectory, but to l = 2R, now it is l = 4R, and is worst than l = 2R
		// Sorting using Anonymous Inner class.  
		// Original article uses QuickSort. However a MergeSort would me more stable.
		// Java 8 API discussion on http://stackoverflow.com/questions/32334319/why-does-collections-sort-use-mergesort-but-arrays-sort-does-not
		Collections.sort(jota.innerSquarePOIs, new Comparator<POInode>(){
		   // overwriting the compare function to fppwr (the original was related to ID / DistToGS
			public int compare(POInode p1, POInode p2){
				if(p1.fppwrDistToSquareRoot < p2.fppwrDistToSquareRoot) {
					return -1;
				} else {
					if(p1.fppwrDistToSquareRoot == p2.fppwrDistToSquareRoot) {
						return 0;
					} else {
						return 1;
					}
				}
			  }
		}); ///////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// What FPPWR calls exaustive search.
		// test the possibilities do better fit two consecutive Squares.
		// this real looks naive, but it is their paper... i shaw code as well
		
		// just make sense if both squares have at least 2 POIs..
		if ((m >=1)&&(jotaPRE.innerSquarePOIs.size()>=2)&&(jota.innerSquarePOIs.size()>=2)) {
			
			if (jotaPRE.relation == fppwrSQUARE.RelationType.LEFTRIGHT) {
				// get candidates
				candHoutAlfa = jotaPRE.innerSquarePOIs.get(jotaPRE.innerSquarePOIs.size()-2);
				candHoutBeta = jotaPRE.innerSquarePOIs.get(jotaPRE.innerSquarePOIs.size()-1);
				candHinAlfa = jota.innerSquarePOIs.get(0);
				candHinBeta = jota.innerSquarePOIs.get(1);
				// get the exaustive possibilities
				int distA = distance(candHoutAlfa,candHoutBeta) + distance(candHoutBeta,candHinAlfa) + distance(candHinAlfa,candHinBeta);
				int distB = distance(candHoutAlfa,candHoutBeta) + distance(candHoutBeta,candHinBeta) + distance(candHinBeta,candHinAlfa);
				int distC = distance(candHoutBeta,candHoutAlfa) + distance(candHoutAlfa,candHinAlfa) + distance(candHinAlfa,candHinBeta);
				int distD = distance(candHoutBeta,candHoutAlfa) + distance(candHoutAlfa,candHinBeta) + distance(candHinBeta,candHinAlfa);
	
				// choose the smaller one and change POIs in both arrays/SquaresPaths
				if ((distA < distB)&&(distA < distC)&&(distA < distD)) { 		// first possibility is the best			
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-2), candHoutAlfa);
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-1), candHoutBeta);
					jota.innerSquarePOIs.set(0, candHinAlfa);
					jota.innerSquarePOIs.set(1, candHinBeta);
				
				} else if ((distB < distA)&&(distB < distC)&&(distB < distD)){  // second possibility is the best
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-2), candHoutAlfa);
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-1), candHoutBeta);
					jota.innerSquarePOIs.set(0, candHinBeta);
					jota.innerSquarePOIs.set(1, candHinAlfa);
				
				} else if ((distC < distB)&&(distC < distA)&&(distC < distD)){  // third possibility is the best
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-2), candHoutBeta);
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-1), candHoutAlfa);
					jota.innerSquarePOIs.set(0, candHinAlfa);
					jota.innerSquarePOIs.set(1, candHinBeta);
		
				} else if ((distD < distB)&&(distD < distC)&&(distD < distA)){  // fourth possibility is the best
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-2), candHoutBeta);
					jotaPRE.innerSquarePOIs.set((jotaPRE.innerSquarePOIs.size()-1), candHoutAlfa);
					jota.innerSquarePOIs.set(0, candHinBeta);
					jota.innerSquarePOIs.set(1, candHinAlfa);
				}			
			} else if (jotaPRE.relation == fppwrSQUARE.RelationType.UPDOWN){
				
				// putting the entrance as the higher X value POI
				candHinAlfa = jota.innerSquarePOIs.get(0); // the first regarding the Sort Method
				int posLastHin = 0;
				for (int i=0; i < jota.innerSquarePOIs.size(); i++ ){
					candHinBeta = jota.innerSquarePOIs.get(i);
					if (candHinBeta.getPosition().xCoord > candHinAlfa.getPosition().xCoord){
						candHinAlfa = candHinBeta; // now candHinAlfa is the max Vertical
						posLastHin = i;
					}
				}
				if (candHinAlfa != jota.innerSquarePOIs.get(0)) {
					//swap  (it could be better than just swap)
					candHinBeta = jota.innerSquarePOIs.get(0);
					jota.innerSquarePOIs.set(0, candHinAlfa);
					jota.innerSquarePOIs.set(posLastHin, candHinBeta); // it could be a kind of mess. I would re-order the entire array with out Hin and add it as a starter
				}
				
				
				// putting the Hout as the lower X value POI
				candHoutAlfa = jotaPRE.innerSquarePOIs.get(0); // the first regarding the Sort Method
				int posLastHout = 0;
				for (int i=0; i < jotaPRE.innerSquarePOIs.size(); i++ ){
					candHoutBeta = jotaPRE.innerSquarePOIs.get(i);
					if (candHoutBeta.getPosition().xCoord < candHinAlfa.getPosition().xCoord){
						candHoutAlfa = candHoutBeta; // now candHinAlfa is the max Vertical
						posLastHout = i;
					}
				}
				if (candHoutAlfa != jotaPRE.innerSquarePOIs.get(jotaPRE.innerSquarePOIs.size()-1)) {
					//swap  (it could be better than just swap)
					candHoutBeta = jotaPRE.innerSquarePOIs.get(0);
					jotaPRE.innerSquarePOIs.set(0, candHoutAlfa);
					jotaPRE.innerSquarePOIs.set(posLastHout, candHoutBeta); // it could be a kind of mess. I would re-order the entire array with out Hin and add it as a starter
				}
				
			    
			} else if (jotaPRE.relation == fppwrSQUARE.RelationType.FINISHER){
				//do nothing, just let the next loop add all POIs						
			} else if (jotaPRE.relation == fppwrSQUARE.RelationType.BEGINNER){ // blocked upside on IF(M>=1)
				//do nothing, just let the next loop add all POIs			
			}
		}
		
			
		
		
		// join //////////////////////////////////////////////////////////////////////////////////////////
		for(int l=0; l < jota.innerSquarePOIs.size(); l++){
			poiTMP = jota.innerSquarePOIs.get(l);
			//System.out.print(poiTMP.ID + " - >");
			resultPath.add(poiTMP);
		}
		//} ///////////////////////////////////////////////////////////////////////////////////////////////
	}
	System.out.print("\n\n");
	///////////////////////////////////////////////////////////////////////////////////

	// not optimized path yet /////////////////////////////////////////////////////////
	System.out.print("\n[FPPWR Planner] FPPWR raw path: ");
	for(int l=0; l < resultPath.size(); l++){
		poiTMP = resultPath.get(l);
		System.out.print(poiTMP.ID + " - >");
	}
	System.out.print("\n\n");
	///////////////////////////////////////////////////////////////////////////////////
	
	// looking for non coverd POI /////////////////////////////////////////////////////
	System.out.print("\n[FPPWR Planner] Orfans POIs: ");
	for(int l=0; l < resultPath.size(); l++){
		poiTMP = resultPath.get(l);
		
		if (!poiTMP.flagFPPWR)
			System.out.print(poiTMP.ID + " - >");
	}
	System.out.print("\n\n");
	///////////////////////////////////////////////////////////////////////////////////
	
	return resultPath;
	
}
	
private int distance(POInode a, POInode b){
	
	return (int) Math.sqrt(
            (a.getPosition().xCoord - b.getPosition().xCoord) *  (a.getPosition().xCoord - b.getPosition().xCoord) + 
            (a.getPosition().yCoord - b.getPosition().yCoord) *  (a.getPosition().yCoord - b.getPosition().yCoord)
        );
	
}


	

}
