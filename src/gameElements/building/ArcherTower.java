package gameElements.building;

import utility.Cost;
import utility.Position;

/**
 * This class represents an archer tower.
 */
public class ArcherTower extends Building implements Defense {
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private static final int[] HIT_POINTS;
    private static final int[] DAMAGE;
    private ArcherTowerData data;
    private int damage; // per 2 seconds
    private int range;

    static {
        BUILD_COST = new Cost(5, 10, 20);
        BUILD_TIME = 60;
        UPGRADE_COSTS = new Cost[] {
            new Cost(10, 20, 30),
            new Cost(15, 30, 40),
            new Cost(20, 40, 50),
            new Cost(25, 50, 60)
        };
        UPGRADE_TIMES = new int[] { 45, 60, 75, 90 };
        HIT_POINTS = new int[] { 400, 500, 600, 700, 800 };
        DAMAGE = new int[] { 25, 30, 35, 40, 45 };
    }

    /**
     * Class constructor.
     * @param pos the position of the new archer tower
     * @param data the archer tower data
     */
    public ArcherTower(Position pos, ArcherTowerData data) {
        super(pos);
        this.data = data;
        data.incrementCount();
        upgradeCost = UPGRADE_COSTS[0];
        upgradeTime = UPGRADE_TIMES[0];
        hitPoints = HIT_POINTS[0];
        damage = DAMAGE[0];
        range = 15;
    }

    /**
     * Class constructor for generated villages
     * @param level the level of the building
     */
    public ArcherTower(int level) {
        super();
        hitPoints = HIT_POINTS[level];
        damage = DAMAGE[level];
    }

    /**
     * Returns the maximum possible level of an archer tower.
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
        return "A";
    }

    /**
     * Upgrades the archer tower
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
