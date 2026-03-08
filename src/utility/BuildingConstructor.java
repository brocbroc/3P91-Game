package utility;

import gameElements.building.Building;

/**
 * This interface represents a constructor for the <code>Building</code> subclasses.
 */
public interface BuildingConstructor {
	/**
	 * Returns the number of buildings of this type.
	 * @return the number of buildings of this type
	 */
	int getCount();

	/**
	 * Returns the maximum number of buildings of this type.
	 * @return the maximum number of buildings of this type
	 */
	int getMaxCount();

	/**
	 * Returns the build cost.
	 * @return the build cost
	 */
	Cost getBuildCost();

	/**
	 * Returns the build time.
	 * @return the build time, in seconds
	 */
	int getBuildTime();

	/**
	 * Creates a new building at the given position.
	 * @param pos the position of the new building
	 * @return the new building
	 */
	Building addBuilding(Position pos);
}
