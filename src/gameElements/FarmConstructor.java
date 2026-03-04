package gameElements;

import utility.*;

public class FarmConstructor implements BuildingConstructor {
	@Override
	public Cost getBuildCost() { return Farm.getBuildCost(); }

	@Override
	public int getBuildTime() { return Farm.getBuildTime(); }

	public Building addBuilding(Position pos) {
		return new Farm(pos);
	}
}
