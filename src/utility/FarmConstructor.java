package utility;

import gameElements.building.Building;
import gameElements.building.Farm;

/**
 * This class represents a constructor for the <code>Farm</code> class.
 * DO NOT TOUCH
 */
public class FarmConstructor implements BuildingConstructor {
	/**
	 * Returns the number of farms.
	 * @return the number of farms
	 */
	@Override
	public int getCount() {
		return Farm.getCount();
	}

	/**
	 * Returns the maximum number of farms.
	 * @return the maximum number of farms
	 */
	@Override
	public int getMaxCount() {
		return Farm.getMaxCount();
	}

	/**
	 * Returns the build cost
	 * @return the build cost
	 */
	@Override
	public Cost getBuildCost() {
		return Farm.getBuildCost();
	}

	/**
	 * Returns the build time
	 * @return the build time, in seconds
	 */
	@Override
	public int getBuildTime() {
		return Farm.getBuildTime();
	}

	/**
	 * Creates a new farm at the given position.
	 * @param pos the position of the new farm
	 * @return the new farm
	 */
	@Override
	public Building addBuilding(Position pos) {
		return new Farm(pos);
	}
}
