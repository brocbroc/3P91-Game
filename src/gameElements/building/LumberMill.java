package gameElements.building;

import utility.*;

/**
 * This class represents a lumber mill.
 */
public class LumberMill extends Building {
    private static int maxLevel;
    private static int count;
    private static int maxCount;
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;

    static {
        maxLevel = 0;
        count = 0;
        maxCount = 2;
        BUILD_COST = new Cost(0, 0, 20);
        BUILD_TIME = 20;
        UPGRADE_COSTS = new Cost[] {
            new Cost(0, 5, 30),
            new Cost(5, 10, 40),
            new Cost(10, 15, 50),
            new Cost(15, 20, 60)
        };
        UPGRADE_TIMES = new int[] { 15, 30, 45, 60 };
    }

    /**
     * Class constructor.
     * @param pos the position of the new lumber mill
     */
    public LumberMill(Position pos) {
        super(pos);
        count++;
        upgradeCost = UPGRADE_COSTS[0];
        upgradeTime = UPGRADE_TIMES[0];
    }

    /**
     * Returns the maximum possible level of a lumber mill.
     * @return the maximum possible level
     */
    @Override
    public int getMaxLevel() { return maxLevel; }

    /**
     * Returns the current number of lumber mills
     * @return the number of lumber mills
     */
    public static int getCount() { return count; }

    /**
     * Returns the maximum number of lumber mills
     * @return the maximum number of lumber mills
     */
    public static int getMaxCount() { return maxCount; }

    /**
     * Returns the build cost
     * @return the build cost
     */
    public static Cost getBuildCost() { return BUILD_COST; }

    /**
     * Returns the build time
     * @return the build time, in seconds
     */
    public static int getBuildTime() { return BUILD_TIME; }

    /**
     * Sets the maximum upgrade level
     * @param level the maximum upgrade level
     */
    static void setMaxLevel(int level) { maxLevel = level; }

    /**
     * Sets the maximum number of lumber mills allowed
     * @param count the maximum number of lumber mills
     */
    static void setMaxCount(int count) { maxCount = count; }

    /**
     * Returns the character representing the building
     * @return a character
     */
    @Override
    public String draw() { return "L"; }

    /**
     * Upgrades the lumber mill
     */
    @Override
    public void upgrade() {
        level++;

        if (level < MAX_LEVEL) {
            upgradeCost = UPGRADE_COSTS[level];
            upgradeTime = UPGRADE_TIMES[level];
        }

        // Increases number of people allowed to work at mill
        // Increases the resource collecting multiplier
    }
}
