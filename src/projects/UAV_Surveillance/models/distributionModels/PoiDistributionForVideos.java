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
	@Override
	public Position getNextPosition()  {
		
//		System.out.println("[PoiDistributionFromFile] ENTROOUUUUU");
//		System.out.print("[PoiDistributionFromFile] lastPOIloaded = " + Global.lastPOIloaded  + "  ");
//		System.out.println("[PoiDistributionFromFile] Global.listOfLoadedPOIs.size() = " + Global.listOfLoadedPOIs.size());

		if (Global.listOfLoadedPOIs.size()<=0) {
			System.out.println("\n\n[PoiDistributionFromFile] ERROR!!! Empty listOfLoadedPOIs\n\n");
		}
		
		Position pos = Global.listOfLoadedPOIs.get(Global.lastPOIloaded).myPos;
		//pos = (Position) Global.listOfLoadedPOIs.get(Global.lastPOIloaded).getPosition();
		Global.lastPOIloaded++;
		

		return pos;
	}
	
}