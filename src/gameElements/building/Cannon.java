package gameElements.building;

import utility.Cost;
import utility.Position;

/**
 * This class represents a cannon.
 */
public class Cannon extends Building implements Defense {
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private static final int[] HIT_POINTS;
    private static final int[] DAMAGE;
    private CannonData data;
    private int damage; // per 2 seconds
    private int range;

    static {
        BUILD_COST = new Cost(20, 20, 0);
        BUILD_TIME = 60;
        UPGRADE_COSTS = new Cost[] {
            new Cost(30, 30, 10),
            new Cost(40, 40, 20),
            new Cost(50, 50, 30),
            new Cost(60, 60, 40)
        };
        UPGRADE_TIMES = new int[] { 45, 60, 75, 90 };
        HIT_POINTS = new int[] { 400, 500, 600, 700, 800 };
        DAMAGE = new int[] { 50, 60, 70, 80, 90 };
    }

    /**
     * Class constructor.
     * @param pos the position of the new cannon
     * @param data the cannon data
     */
    public Cannon(Position pos, CannonData data) {
        super(pos);
        this.data = data;
        data.incrementCount();
        upgradeCost = UPGRADE_COSTS[0];
        upgradeTime = UPGRADE_TIMES[0];
        hitPoints = HIT_POINTS[0];
        damage = DAMAGE[0];
        range = 5;
    }

    /**
     * Class constructor for generated villages
     * @param level the level of the building
     */
    public Cannon(int level) {
        super();
        hitPoints = HIT_POINTS[level];
        damage = DAMAGE[level];
    }

    /**
     * Returns the maximum possible level of a cannon.
     * @return the maximum possible level
     */
    @Override
    public int getMaxLevel() {
        return data.getMaxLevel();
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
        return "C";
    }

    /**
     * Upgrades the cannon
     */
    @Override
    public void upgrade() {
        level++;
        hitPoints = HIT_POINTS[level];
        damage = DAMAGE[level];

        if (level < MAX_LEVEL) {
            upgradeCost = UPGRADE_COSTS[level];
            upgradeTime = UPGRADE_TIMES[level];
        }
    }
}