package projects.UAV_Surveillance.models.distributionModels;


import projects.defaultProject.models.distributionModels.Random;
import sinalgo.configuration.Configuration;
import sinalgo.nodes.Position;
import sinalgo.tools.Tools;



public class PoiDistribution extends Random {

	@Override
	public Position getNextPosition() {
		Position pos = super.getNextPosition();
		if(Configuration.useMap){
			// anything else than white is considered an obstacle
			while(!Tools.getBackgroundMap().isWhite(pos)){
				pos = super.getNextPosition();
			}
		}
		return pos;
	}
	
}
