package projects.UAV_Surveillance.models.distributionModels;




import projects.UAV_Surveillance.CustomGlobal;
import projects.defaultProject.models.distributionModels.Random;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Position;
import sinalgo.runtime.Global;
import sinalgo.tools.Tools;




public class PoiDistributionNN extends Random {

	
	
	@Override
	public Position getNextPosition() throws CorruptConfigurationEntryException {
		Position pos = super.getNextPosition();

		
		
		//if(Configuration.useMap){
			// anything else than white is considered an obstacle
			try {
				//while((!Tools.getBackgroundMap().isWhite(pos))&&(!fairEnoughFromOthers(pos))){
				while(  !fairEnoughFromOthers(pos)  ){
					pos = super.getNextPosition();
				}
			} catch (Exception e) {
				System.out.print("\n\n\n\n[PoiDistributionNN]  ERROR!!!! \n\n\n\n");

				e.printStackTrace();
			}
		//}
		
		Global.insertedPositions.add(pos);
		return pos;
	}
	


	public boolean fairEnoughFromOthers(Position pos) throws CorruptConfigurationEntryException{
		
		int minDistBetweenPOIs = 0;
		minDistBetweenPOIs = sinalgo.configuration.Configuration.getIntegerParameter("UDG/rMax");
		
		for(int i=0; i<Global.insertedPositions.size(); i++){
			if (pos.distanceTo(Global.insertedPositions.get(i)) < minDistBetweenPOIs) {
		    	
				System.out.print("\n\n[PoiDistributionNN] Position to close to already created POI: Blocked! (minDistBetweenPOIs = " + minDistBetweenPOIs + ")\n\n");
				return false;
			}
         }	
		//System.out.print("\n\n[PoiDistributionNN] Position free \n\n");

		return true;
	
	}
	



}