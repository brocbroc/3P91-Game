package utility;

import gameElements.building.Building;
import gameElements.building.IronMine;
import gameElements.building.IronMineData;

/**
 * This class represents a constructor for the <code>IronMine</code> class.
 */
public class IronMineConstructor implements BuildingConstructor {
    private IronMineData data;

    /**
     * Class constructor
     * @param data the iron mine data
     */
    public IronMineConstructor(IronMineData data) {
        this.data = data;
    }

    /**
     * Returns the number of iron mines.
     * @return the number of iron mines
     */
    @Override
    public int getCount() {
        return data.getCount();
    }

    /**
     * Returns the maximum number of iron mines.
     * @return the maximum number of iron mines
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
        return IronMine.getBuildCost();
    }

    /**
     * Returns the build time
     * @return the build time, in seconds
     */
    @Override
    public int getBuildTime() {
        return IronMine.getBuildTime();
    }

    /**
     * Creates a new iron mine at the given position.
     * @param pos the position of the new iron mine
     * @return the new iron mine
     */
    @Override
    public Building addBuilding(Position pos) {
        return new IronMine(pos, data);
    }
}