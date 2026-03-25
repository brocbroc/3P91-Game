package utility;

import gameElements.building.Building;
import gameElements.building.Farm;
import gameElements.building.FarmData;

/**
 * This class represents a constructor for the <code>Farm</code> class.
 */
public class FarmConstructor implements BuildingConstructor {
	private FarmData data;

	public FarmConstructor(FarmData data) {
		this.data = data;
	}

	/**
	 * Returns the number of farms.
	 * @return the number of farms
	 */
	@Override
	public int getCount() {
		return data.getCount();
	}

	/**
	 * Returns the maximum number of farms.
	 * @return the maximum number of farms
	 */
	@Override
	public int getMaxCount() {
		return data.getMaxCount();
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
		return new Farm(pos, data);
	}
}
