package projects.UAV_Surveillance.models.distributionModels;




import java.util.ArrayList;

import projects.UAV_Surveillance.nodes.nodeImplementations.POInode;
import projects.defaultProject.models.distributionModels.Random;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Position;
import sinalgo.runtime.Global;
import sinalgo.tools.Tools;



public class PoiDistributionForVideos  extends Random{
	
	public static ArrayList<Position> hardCodedPoisPositions = new ArrayList<Position>();

	// Hardcode for urgent video
	// SHAME!!!
	public PoiDistributionForVideos() {
		
//		for(int i = 0; i < 11; i++) {  //12 POIs on video
//			Position posCR = new Position();
//			hardCodedPoisPositions.add(posCR);
//		}
		
//		Position posTMP = new Position();

		
//		posTMP.assign(245, 151, 0.0); hardCodedPoisPositions.add(posTMP);  		
//		posTMP.assign(64, 400, 0.0); hardCodedPoisPositions.add(posTMP);	
//		posTMP.assign(317, 384, 0.0); hardCodedPoisPositions.add(posTMP);	
//		posTMP.assign(471, 246, 0.0); hardCodedPoisPositions.add(posTMP);	
//		posTMP.assign(703, 222, 0.0); hardCodedPoisPositions.add(posTMP);
//		posTMP.assign(733, 52, 0.0); hardCodedPoisPositions.add(posTMP);
//		posTMP.assign(918, 99, 0.0); hardCodedPoisPositions.add(posTMP);
//		posTMP.assign(1113, 224, 0.0); hardCodedPoisPositions.add(posTMP);
//		posTMP.assign(1075, 516, 0.0); hardCodedPoisPositions.add(posTMP);
//		posTMP.assign(882, 644, 0.0); hardCodedPoisPositions.add(posTMP);
//		posTMP.assign(519, 552, 0.0); hardCodedPoisPositions.add(posTMP);
//		posTMP.assign(383, 629, 0.0); hardCodedPoisPositions.add(posTMP);	
		
		
		hardCodedPoisPositions.add(new Position(245, 151, 0.0));  		
		hardCodedPoisPositions.add(new Position(64, 400, 0.0));	
		hardCodedPoisPositions.add(new Position(317, 384, 0.0));	
		hardCodedPoisPositions.add(new Position(471, 246, 0.0));	
		hardCodedPoisPositions.add(new Position(703, 222, 0.0));
		hardCodedPoisPositions.add(new Position(733, 52, 0.0));
		hardCodedPoisPositions.add(new Position(918, 99, 0.0));
		hardCodedPoisPositions.add(new Position(1113, 224, 0.0));
		hardCodedPoisPositions.add(new Position(1075, 516, 0.0));
		hardCodedPoisPositions.add(new Position(882, 644, 0.0));
		hardCodedPoisPositions.add(new Position(519, 552, 0.0));
		hardCodedPoisPositions.add(new Position(383, 629, 0.0));	
		
		
		System.out.print("\n[PoiDistributionForVideos] gerado na marra: ");

		for(int i = 0; i < 11; i++) {  //12 POIs on video
			
			System.out.print(hardCodedPoisPositions.get(i).toString() + " ; ");
		}
		
	}
	
	@Override
	public Position getNextPosition()  {
		
		//System.out.println("[PoiDistributionForVideos] ENTROOUUUUU");
//		System.out.print("[PoiDistributionForVideos] lastPOIloaded = " + Global.lastPOIloaded  + "  ");
//		System.out.println("[PoiDistributionForVideos] Global.listOfLoadedPOIs.size() = " + Global.listOfLoadedPOIs.size());

//		if (Global.listOfLoadedPOIs.size()<=0) {
//			System.out.println("\n\n[PoiDistributionForVideos] ERROR!!! Empty listOfLoadedPOIs\n\n");
//		}
		
		Position pos = hardCodedPoisPositions.get(Global.lastPOIloaded);

		Global.lastPOIloaded++;
		
		//System.out.println("\n[PoiDistributionForVideos] giving pos: " + pos.toString());

		return pos;
	}
	
}
