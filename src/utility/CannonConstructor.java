package utility;

import gameElements.building.Building;
import gameElements.building.Cannon;

/**
 * This class represents a constructor for the <code>Cannon</code> class.
 * DO NOT TOUCH
 */
public class CannonConstructor implements BuildingConstructor {
    /**
     * Returns the number of cannons.
     * @return the number of cannons
     */
    @Override
    public int getCount() {
        return Cannon.getCount();
    }

    /**
     * Returns the maximum number of cannons.
     * @return the maximum number of cannons
     */
    @Override
    public int getMaxCount() {
        return Cannon.getMaxCount();
    }

    /**
     * Returns the build cost
     * @return the build cost
     */
    @Override
    public Cost getBuildCost() {
        return Cannon.getBuildCost();
    }

    /**
     * Returns the build time
     * @return the build time, in seconds
     */
    @Override
    public int getBuildTime() {
        return Cannon.getBuildTime();
    }

    /**
     * Creates a new cannon at the given position.
     * @param pos the position of the new cannon
     * @return the new cannon
     */
    @Override
    public Building addBuilding(Position pos) {
        return new Cannon(pos);
    }
}
