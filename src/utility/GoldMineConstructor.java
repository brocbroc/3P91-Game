package utility;

import gameElements.building.Building;
import gameElements.building.GoldMine;

/**
 * This class represents a constructor for the <code>GoldMine</code> class.
 */
public class GoldMineConstructor implements BuildingConstructor {
    /**
     * Returns the number of gold mines.
     * @return the number of gold mine
     */
    @Override
    public int getCount() {
        return GoldMine.getCount();
    }

    /**
     * Returns the maximum number of gold mine.
     * @return the maximum number of gold mine
     */
    @Override
    public int getMaxCount() {
        return GoldMine.getMaxCount();
    }

    /**
     * Returns the build cost
     * @return the build cost
     */
    @Override
    public Cost getBuildCost() {
        return GoldMine.getBuildCost();
    }

    /**
     * Returns the build time
     * @return the build time, in seconds
     */
    @Override
    public int getBuildTime() {
        return GoldMine.getBuildTime();
    }

    /**
     * Creates a new gold mine at the given position.
     * @param pos the position of the new gold mine
     * @return the new gold mine
     */
    @Override
    public Building addBuilding(Position pos) {
        return new GoldMine(pos);
    }
}
