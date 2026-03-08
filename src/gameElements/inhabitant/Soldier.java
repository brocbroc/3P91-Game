package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents a basic soldier unit in the army.
 */
public class Soldier extends Fighter {
    private static final Cost PRODUCTION_COST;
    private static final int PRODUCTION_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private static int level;

    static {
        PRODUCTION_COST = new Cost(0, 20, 10);
        PRODUCTION_TIME = 10;
        UPGRADE_COSTS = new Cost[] {
            new Cost(0, 0, 0),
            new Cost(0, 0, 0),
            new Cost(0, 0, 0),
            new Cost(0, 0, 0)
        };
        UPGRADE_TIMES = new int[] {};
        level = 0;
    }

    /**
     * Class constructor.
     */
    public Soldier() {
        super(6, 40);
    }

    /**
     * Returns the production cost
     * @return the production cost
     */
    public static Cost getProductionCost() {
        return PRODUCTION_COST;
    }

    /**
     * Returns the production time
     * @return the production time, in seconds
     */
    public static int getProductionTime() {
        return PRODUCTION_TIME;
    }

    /**
     * Returns the upgrade cost
     * @return the upgrade cost
     */
    public static Cost getUpgradeCost() {
        if (level < Inhabitant.MAX_LEVEL) {
            return UPGRADE_COSTS[level];
        }

        return null;
    }

    /**
     * Returns the upgrade time
     * @return the upgrade time, in seconds
     */
    public static int getUpgradeTime() {
        if (level < Inhabitant.MAX_LEVEL) {
            return UPGRADE_TIMES[level];
        }

        return 0;
    }

    /**
     * Returns the level of the class
     * @return the level
     */
    public static int getLevel() {
        return level;
    }
}