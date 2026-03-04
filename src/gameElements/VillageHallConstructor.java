package gameElements;

import utility.*;

/**
 * This class represents a constructor for the <code>VillageHall</code> class.
 */
public class VillageHallConstructor implements BuildingConstructor {
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
