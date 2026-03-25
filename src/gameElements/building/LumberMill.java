package gameElements.building;

import gameElements.inhabitant.Lumberman;
import java.util.List;
import java.util.ArrayList;
import utility.Cost;
import utility.Position;

/**
 * This class represents a lumber mill.
 */
public class LumberMill extends Building {
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private LumberMillData data;
    private List<Lumberman> miners;
    private int maxMiners;

    static {
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
     * @param data the lumber mill data
     */
    public LumberMill(Position pos, LumberMillData data) {
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
     * Returns the maximum possible level of a lumber mill.
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
        return "L";
    }

    /**
     * Upgrades the lumber mill
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
