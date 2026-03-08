package gameElements.building;

import utility.Cost;
import utility.Position;

/**
 * This class represents a farm.
 * The level and number of farms determine the maximum population size.
 */
public class Farm extends Building {
	private static int maxLevel;
	private static int count;
	private static int maxCount;
	private static final Cost BUILD_COST;
	private static final int BUILD_TIME; // seconds
	private static final Cost[] UPGRADE_COSTS;
	private static final int[] UPGRADE_TIMES;
	private static final int[] POPULATION_SUPPORTED;

	static {
		maxLevel = 0;
		count = 0;
		maxCount = 5;
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
	}

	/**
	 * Class constructor.
	 * @param pos the position of the new farm
	 */
	public Farm(Position pos) {
		super(pos);
		count++;
		upgradeCost = UPGRADE_COSTS[0];
		upgradeTime = UPGRADE_TIMES[0];
		hitPoints = 100;
	}

	/**
	 * Returns the maximum possible level of a farm.
	 * @return the maximum possible level
	 */
	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * Returns the current number of farms
	 * @return the number of farms
	 */
	public static int getCount() {
		return count;
	}

	/**
	 * Returns the maximum number of farms
	 * @return the maximum number of farms
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
	 * Sets the maximum number of farms allowed
	 * @param count the maximum number of farms
	 */
	static void setMaxCount(int count) {
		maxCount = count;
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
		return "F";
	}

	/**
	 * Upgrades the farm
	 */
	@Override
	public void upgrade() {
		level++;
		hitPoints += 20;

		if (level < MAX_LEVEL) {
			upgradeCost = UPGRADE_COSTS[level];
			upgradeTime = UPGRADE_TIMES[level];
		}
	}
}

