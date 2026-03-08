package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents a siege unit used to damage buildings.
 */
public class Catapult extends Fighter {
    private static final Cost PRODUCTION_COST;
    private static final int PRODUCTION_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private static int level;
    private static boolean isUpgrading;
    private static int hitPoints;
    private static int damage;
    private static int range;

    static {
        PRODUCTION_COST = new Cost(20, 20, 20);
        PRODUCTION_TIME = 20;
        UPGRADE_COSTS = new Cost[] {
            new Cost(40, 40, 40),
            new Cost(80, 80, 80),
            new Cost(160, 160, 160),
            new Cost(320, 320, 320)
        };
        UPGRADE_TIMES = new int[] { 25, 30, 35, 40 };
        level = 0;
        hitPoints = 500;
        damage = 20;
        range = 10;
    }

    /**
     * Class constructor.
     */
    public Catapult() {
        super();
        remainingHitPoints = hitPoints;
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

    /**
     * Returns whether or not the class is upgrading.
     * @return <code>true</code> if the class is upgrading, <code>false</code> otherwise
     */
    public static boolean isUpgrading() {
        return isUpgrading;
    }

    /**
     * Sets whether or not the class is upgrading
     * @param upgrading the new upgrade status
     */
    public static void setUpgrading(boolean upgrading) {
        isUpgrading = upgrading;
    }

    /**
     * Returns the total hit points
     * @return the total hit points
     */
    public static int getHitPoints() {
        return hitPoints;
    }

    /**
     * Returns the damage done
     * @return the damage done
     */
    public static int getDamage() {
        return damage;
    }

    /**
     * Returns the range of attack
     * @return the range
     */
    public static int getRange() {
        return range;
    }

    /**
     * Upgrades the Soldier class
     */
    public static void upgrade() {
        level++;
        hitPoints += 50;
        damage += 5;
    }

    /**
     * Restores the hit points and sets <code>isDead</code> to false
     */
    @Override
    public void restore() {
        remainingHitPoints = hitPoints;
        isDead = false;
    }
}
