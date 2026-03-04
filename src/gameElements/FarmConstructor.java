package gameElements;

import utility.*;

/**
 * This class represents a constructor for the <code>Farm</code> class.
 */
public class FarmConstructor implements BuildingConstructor {
	/**
	 * Returns the build cost
	 * @return the build cost
	 */
	@Override
	public Cost getBuildCost() { return Farm.getBuildCost(); }

	/**
	 * Returns the build time
	 * @return the build time, in seconds
	 */
	@Override
	public int getBuildTime() { return Farm.getBuildTime(); }

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
