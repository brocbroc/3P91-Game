package gameElements.building;

import utility.Cost;
import utility.Position;

/**
 * This class represents a farm.
 * The level and number of farms determine the maximum population size.
 */
public class Farm extends Building {
	private static final Cost BUILD_COST;
	private static final int BUILD_TIME; // seconds
	private static final Cost[] UPGRADE_COSTS;
	private static final int[] UPGRADE_TIMES;
	private static final int[] HIT_POINTS;
	private static final int[] POPULATION_SUPPORTED;
	private FarmData data;

	static {
		BUILD_COST = new Cost(0, 10, 10);
		BUILD_TIME = 5;
		UPGRADE_COSTS = new Cost[] {
			new Cost(5, 10, 10),
			new Cost(10, 20, 20),
			new Cost(15, 30, 30),
			new Cost(20, 40, 40)
		};
		UPGRADE_TIMES = new int[] { 10, 10, 20, 20 };
		POPULATION_SUPPORTED = new int[] { 10, 20, 40, 60, 100 };
		HIT_POINTS = new int[] { 100, 125, 150, 175, 200 };
	}

	/**
	 * Class constructor.
	 * @param pos the position of the new farm
	 * @param data the data of the farm
	 */
	public Farm(Position pos, FarmData data) {
		super(pos);
		this.data = data;
		data.incrementCount();
		upgradeCost = UPGRADE_COSTS[0];
		upgradeTime = UPGRADE_TIMES[0];
		hitPoints = HIT_POINTS[0];
	}

	/**
	 * Class constructor for generated villages
	 * @param level the level of the building
	 */
	public Farm(int level) {
		super();
		hitPoints = HIT_POINTS[level];
	}

	/**
	 * Returns the maximum level of the building
	 * @return the maximum level of the building
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
	 * Returns the increase in population after a farm is added/upgraded
	 * @return the increase in population
	 */
	public int getPopulationIncrease() {
		if (level == 0) {
			return POPULATION_SUPPORTED[0];
		}

		return POPULATION_SUPPORTED[level] - POPULATION_SUPPORTED[level - 1];
	}

	/**
	 * Returns the character representing the building
	 * @return a character
	 */
	@Override
	public String draw() {
		return "F" + level;
	}

	/**
	 * Upgrades the farm
	 */
	@Override
	public void upgrade() {
		level++;
		hitPoints = HIT_POINTS[level];

		if (level < MAX_LEVEL) {
			upgradeCost = UPGRADE_COSTS[level];
			upgradeTime = UPGRADE_TIMES[level];
		}
	}
}

