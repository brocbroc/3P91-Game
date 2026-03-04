package gameElements;

import utility.*;

/**
 * This class represents a constructor for the <code>Building</code> class.
 */
public interface BuildingConstructor {
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
