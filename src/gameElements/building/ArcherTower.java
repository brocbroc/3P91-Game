package gameElements.building;

import utility.Cost;
import utility.Position;

/**
 * This class represents an archer tower.
 */
public class ArcherTower extends Building implements Defense {
    private static int maxLevel;
    private static int count;
    private static int maxCount;
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private int damage; // per 2 seconds
    private int range;

    static {
        maxLevel = 0;
        count = 0;
        maxCount = 5;
        BUILD_COST = new Cost(5, 10, 20);
        BUILD_TIME = 60;
        UPGRADE_COSTS = new Cost[] {
            new Cost(10, 20, 30),
            new Cost(15, 30, 40),
            new Cost(20, 40, 50),
            new Cost(25, 50, 60)
        };
        UPGRADE_TIMES = new int[] { 45, 60, 75, 90 };
    }

    /**
     * Class constructor.
     * @param pos the position of the new archer tower
     */
    public ArcherTower(Position pos) {
        super(pos);
        count++;
        upgradeCost = UPGRADE_COSTS[0];
        upgradeTime = UPGRADE_TIMES[0];
        hitPoints = 400;
        damage = 25;
        range = 15;
    }

    /**
     * Returns the maximum possible level of an archer tower.
     * @return the maximum possible level
     */
    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Returns the current number of archer towers
     * @return the number of archer towers
     */
    public static int getCount() {
        return count;
    }

    /**
     * Returns the maximum number of archer towers
     * @return the maximum number of archer towers
     */
    public static int getMaxCount() {
        return maxCount;
    }

    /**
     * Returns the build cost
     * @return the build cost
     */
    public static Cost getBuildCost() {
        return BUILD_COST;
    }

    /**
     * Returns the build time
     * @return the build time, in seconds
     */
    public static int getBuildTime() {
        return BUILD_TIME;
    }

    /**
     * Sets the maximum upgrade level
     * @param level the maximum upgrade level
     */
    static void setMaxLevel(int level) {
        maxLevel = level;
    }

    /**
     * Sets the maximum number of archer towers allowed
     * @param count the maximum number of archer towers
     */
    static void setMaxCount(int count) {
        maxCount = count;
    }

    /**
     * Returns the amount of damage dealt
     * @return the amount of damage
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the character representing the building
     * @return a character
     */
    @Override
    public String draw() {
        return "A";
    }

    /**
     * Upgrades the archer tower
     */
    @Override
    public void upgrade() {
        level++;
        hitPoints += 80;
        damage += 5;

        if (level < MAX_LEVEL) {
            upgradeCost = UPGRADE_COSTS[level];
            upgradeTime = UPGRADE_TIMES[level];
        }

        // Increases the damage
        // Maybe increase the attack range
    }
}
