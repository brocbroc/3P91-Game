package gameElements.building;

import gameElements.inhabitant.GoldMiner;
import utility.Cost;
import utility.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a gold mine.
 */
public class GoldMine extends Building {
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private GoldMineData data;
    private List<GoldMiner> miners;
    private int maxMiners;

    static {
        BUILD_COST = new Cost(20, 0, 0);
        BUILD_TIME = 60;
        UPGRADE_COSTS = new Cost[] {
            new Cost(30, 10, 10),
            new Cost(40, 20, 20),
            new Cost(50, 30, 30),
            new Cost(60, 40, 40)
        };
        UPGRADE_TIMES = new int[] { 45, 60, 75, 90 };
    }

    /**
     * Class constructor.
     * @param pos the position of the new gold mine
     * @param data the gold mine data
     */
    public GoldMine(Position pos, GoldMineData data) {
        super(pos);
        this.data = data;
        data.incrementCount();
        upgradeCost = UPGRADE_COSTS[0];
        upgradeTime = UPGRADE_TIMES[0];
        hitPoints = 200;
        miners = new ArrayList<>();
        maxMiners = 1;
    }

    /**
     * Returns the maximum possible level of a gold mine.
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
        return "G";
    }

    /**
     * Upgrades the gold mine
     */
    @Override
    public void upgrade() {
        level++;
        hitPoints += 40;
        maxMiners += 2;

        if (level < MAX_LEVEL) {
            upgradeCost = UPGRADE_COSTS[level];
            upgradeTime = UPGRADE_TIMES[level];
        }
    }
}
