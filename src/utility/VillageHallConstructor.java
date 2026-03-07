package utility;

import gameElements.building.Building;
import gameElements.building.VillageHall;

/**
 * This class represents a constructor for the <code>VillageHall</code> class.
 * DO NOT TOUCH
 */
public class VillageHallConstructor implements BuildingConstructor {
	/**
	 * Returns the number of village halls.
	 * @return the number of village halls
	 */
	@Override
	public int getCount() {
		return VillageHall.getCount();
	}

	/**
	 * Returns the maximum number of village halls.
	 * @return the maximum number of village halls
	 */
	@Override
	public int getMaxCount() {
		return VillageHall.getMaxCount();
	}

	/**
	 * Returns the build cost
	 * @return the build cost
	 */
	@Override
	public Cost getBuildCost() { return VillageHall.getBuildCost(); }

	/**
	 * Returns the build time
	 * @return the build time, in seconds
	 */
	@Override
	public int getBuildTime() { return VillageHall.getBuildTime(); }

	/**
	 * Creates a new village hall at the given position.
	 * @param pos the position of the new village hall
	 * @return the new village hall
	 */
	@Override
	public Building addBuilding(Position pos) {
		return new VillageHall(pos);
	}
}
