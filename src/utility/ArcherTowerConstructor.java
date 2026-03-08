package utility;

import gameElements.building.ArcherTower;
import gameElements.building.Building;

/**
 * This class represents a constructor for the <code>ArcherTower</code> class.
 */
public class ArcherTowerConstructor implements BuildingConstructor {
    /**
     * Returns the number of archer towers.
     * @return the number of archer towers
     */
    @Override
    public int getCount() {
        return ArcherTower.getCount();
    }

    /**
     * Returns the maximum number of archer towers.
     * @return the maximum number of archer towers
     */
    @Override
    public int getMaxCount() {
        return ArcherTower.getMaxCount();
    }

    /**
     * Returns the build cost
     * @return the build cost
     */
    @Override
    public Cost getBuildCost() {
        return ArcherTower.getBuildCost();
    }

    /**
     * Returns the build time
     * @return the build time, in seconds
     */
    @Override
    public int getBuildTime() {
        return ArcherTower.getBuildTime();
    }

    /**
     * Creates a new archer tower at the given position.
     * @param pos the position of the new archer tower
     * @return the new archer tower
     */
    @Override
    public Building addBuilding(Position pos) {
        return new ArcherTower(pos);
    }
}
