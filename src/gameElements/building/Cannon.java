package gameElements.building;

import gameElements.Damager;
import utility.*;

/**
 * This class represents a cannon.
 */
public class Cannon extends Building implements Damager {
    private static int maxLevel;
    private static int count;
    private static int maxCount;
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private final double baseDamage = 12.0;

    static {
        maxLevel = 0;
        count = 0;
        maxCount = 5;
        BUILD_COST = new Cost(20, 20, 0);
        BUILD_TIME = 60;
        UPGRADE_COSTS = new Cost[] {
            new Cost(30, 30, 10),
            new Cost(40, 40, 20),
            new Cost(50, 50, 30),
            new Cost(60, 60, 40)
        };
        UPGRADE_TIMES = new int[] { 45, 60, 75, 90 };
    }

    /**
     * Class constructor.
     * @param pos the position of the new cannon
     */
    public Cannon(Position pos) {
        super(pos);
        count++;
        upgradeCost = UPGRADE_COSTS[0];
        upgradeTime = UPGRADE_TIMES[0];
    }

    /**
     * Returns the maximum possible level of a cannon.
     * @return the maximum possible level
     */
    @Override
    public int getMaxLevel() { return maxLevel; }

    /**
     * Returns the current number of cannons
     * @return the number of cannons
     */
    public static int getCount() { return count; }

    /**
     * Returns the maximum number of cannons
     * @return the maximum number of cannons
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
     * Sets the maximum number of cannons allowed
     * @param count the maximum number of cannons
     */
    static void setMaxCount(int count) { maxCount = count; }

    /**
     * Returns the character representing the building
     * @return a character
     */
    @Override
    public String draw() { return "C"; }

    /**
     * Upgrades the cannon
     */
    @Override
    public void upgrade() {
        level++;

        if (level < MAX_LEVEL) {
            upgradeCost = UPGRADE_COSTS[level];
            upgradeTime = UPGRADE_TIMES[level];
        }

        // Increases the damage
        // Increases the attack speed
        // Maybe increase the attack range
    }

    @Override
    public int damage() {return 12; }
}