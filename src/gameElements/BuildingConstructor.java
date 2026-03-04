package gameElements;

import utility.*;

public interface BuildingConstructor {
	Cost getBuildCost();

	int getBuildTime();

	Building addBuilding(Position pos);
}
