package projects.UAV_Surveillance.models.mobilityModels;

import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.nodes.Node;
import sinalgo.nodes.Position;

public class NotSoNaiveOrderedMobility  extends NaiveOrderedMobility{

	public NotSoNaiveOrderedMobility() throws CorruptConfigurationEntryException {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public Position getNextPos(Node n) {
		return super.getNextPos(n);
		
	}

}
