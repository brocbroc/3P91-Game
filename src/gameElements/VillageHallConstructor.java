package gameElements;

import utility.*;

public class VillageHallConstructor implements BuildingConstructor {
	@Override
	public Cost getBuildCost() { return VillageHall.getBuildCost(); }

	@Override
	public int getBuildTime() { return VillageHall.getBuildTime(); }

	public Building addBuilding(Position pos) {
		return new VillageHall(pos);
	}
}
