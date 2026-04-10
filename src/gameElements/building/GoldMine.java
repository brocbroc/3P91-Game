package gameElements.building;

import gameElements.BuildingType;
import gameElements.inhabitant.GoldMiner;
import java.util.ArrayList;
import java.util.List;
import utility.Cost;
import utility.Position;

/**
 * This class represents a gold mine.
 */
public class GoldMine extends Building {
    private static final Cost BUILD_COST;
    private static final int BUILD_TIME; // seconds
    private static final Cost[] UPGRADE_COSTS;
    private static final int[] UPGRADE_TIMES;
    private static final int[] HIT_POINTS;
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
        HIT_POINTS = new int[] { 200, 250, 300, 350, 400 };
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
        hitPoints = HIT_POINTS[0];
        miners = new ArrayList<>();
        maxMiners = 1;
    }

    /**
     * Class constructor for generated villages
     * @param level the level of the building
     */
    public GoldMine(int level) {
        super();
        hitPoints = HIT_POINTS[level];
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
     * Returns the type of the building
     * @return the building type
     */
    @Override
    public BuildingType getType() {
        return BuildingType.GOLD_MINE;
    }

    /**
     * Returns the character representing the building
     * @return a character
     */
    @Override
    public String draw() {
        return "G" + level;
    }

    /**
     * Upgrades the gold mine
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
