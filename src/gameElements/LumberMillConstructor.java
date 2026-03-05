package gameElements;

import gameElements.building.Building;
import gameElements.building.LumberMill;
import utility.*;

/**
 * This class represents a constructor for the <code>LumberMill</code> class.
 * DO NOT TOUCH
 */
public class LumberMillConstructor implements BuildingConstructor {
    /**
     * Returns the number of lumber mills.
     * @return the number of lumber mills
     */
    @Override
    public int getCount() {
        return LumberMill.getCount();
    }

    /**
     * Returns the maximum number of lumber mills.
     * @return the maximum number of lumber mills
     */
    @Override
    public int getMaxCount() {
        return LumberMill.getMaxCount();
    }

    /**
     * Returns the build cost
     * @return the build cost
     */
    @Override
    public Cost getBuildCost() {
        return LumberMill.getBuildCost();
    }

    /**
     * Returns the build time
     * @return the build time, in seconds
     */
    @Override
    public int getBuildTime() {
        return LumberMill.getBuildTime();
    }

    /**
     * Creates a new lumber mill at the given position.
     * @param pos the position of the new lumber mill
     * @return the new lumber mill
     */
    @Override
    public Building addBuilding(Position pos) {
        return new LumberMill(pos);
    }
}