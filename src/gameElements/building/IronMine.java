package gameElements.building;

import gameElements.inhabitant.IronMiner;
import java.util.ArrayList;
import java.util.List;
import utility.Cost;
import utility.Position;

/**
 * This class represents an iron mine.
 */
public class IronMine extends Building {
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private static final int[] HIT_POINTS;
    private IronMineData data;
    private List<IronMiner> miners;
    private int maxMiners;

    static {
        BUILD_COST = new Cost(0, 20, 0);
        BUILD_TIME = 40;
        UPGRADE_COSTS = new Cost[] {
            new Cost(0, 30, 10),
            new Cost(5, 40, 20),
            new Cost(10, 50, 30),
            new Cost(15, 60, 40)
        };
        UPGRADE_TIMES = new int[] { 30, 45, 60, 75 };
        HIT_POINTS = new int[] { 200, 250, 300, 350, 400 };
    }

    /**
     * Class constructor.
     * @param pos the position of the new iron mine
     * @param data the iron mine data
     */
    public IronMine(Position pos, IronMineData data) {
        super(pos);
        this.data = data;
        data.incrementCount();
        upgradeCost = UPGRADE_COSTS[0];
        upgradeTime = UPGRADE_TIMES[0];
        hitPoints = HIT_POINTS[0];
        miners = new ArrayList<>();
        maxMiners = 1;
    }

    /**
     * Class constructor for generated villages
     * @param level the level of the building
     */
    public IronMine(int level) {
        super();
        hitPoints = HIT_POINTS[level];
    }

    /**
     * Returns the maximum possible level of an iron mine.
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
     * Returns the character representing the building
     * @return a character
     */
    @Override
    public String draw() {
        return "I" + level;
    }

    /**
     * Upgrades the iron mine
     */
    @Override
    public void upgrade() {
        level++;
        hitPoints = HIT_POINTS[level];
        maxMiners += 2;

        if (level < MAX_LEVEL) {
            upgradeCost = UPGRADE_COSTS[level];
            upgradeTime = UPGRADE_TIMES[level];
        }
    }
}
