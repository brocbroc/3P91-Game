package gameElements.building;

import utility.*;

/**
 * This class represents a village hall.
 * The level of the village hall determines the maximum upgrade level and the maximum number of
 * buildings for the other Building subclasses. Only one village hall may exist.
 * DO NOT TOUCH
 */
public class VillageHall extends Building {
	private static int maxLevel;
	private static int count;
	private static int maxCount;
	private static final Cost BUILD_COST;
	private static final int BUILD_TIME; // seconds
	private static final Cost[] UPGRADE_COSTS;
	private static final int[] UPGRADE_TIMES;

	static {
		maxLevel = MAX_LEVEL;
		count = 0;
		maxCount = 1;
		BUILD_COST = new Cost(5, 5, 5);
		BUILD_TIME = 5;
		UPGRADE_COSTS = new Cost[] {
			new Cost(10, 10, 10),
			new Cost(20, 20, 20),
			new Cost(30, 30, 30),
			new Cost(40, 40, 40)
		};
		UPGRADE_TIMES = new int[] { 5, 5, 5, 5 };
	}

	/**
	 * Class constructor.
	 * @param pos the position of the new village hall
	 */
	public VillageHall(Position pos) {
		super(pos);
		count++;
		upgradeCost = UPGRADE_COSTS[0];
		upgradeTime = UPGRADE_TIMES[0];
	}

	/**
	 * Returns the maximum possible level of a village hall.
	 * @return the maximum possible level
	 */
	@Override
	public int getMaxLevel() { return maxLevel; }

	/**
	 * Returns the current number of village halls
	 * @return the number of village halls
	 */
	public static int getCount() { return count; }

	/**
	 * Returns the maximum number of village halls
	 * @return the maximum number of village halls
	 */
	public static int getMaxCount() { return maxCount; }

	/**
	 * Returns the build cost
	 * @return the build cost
	 */
	public static Cost getBuildCost() { return BUILD_COST; }

	/**
	 * Returns the build time
	 * @return the build time, in seconds
	 */
	public static int getBuildTime() { return BUILD_TIME; }

	/**
	 * Returns the character representing the building
	 * @return a character
	 */
	@Override
	public String draw() { return "H"; }

	/**
	 * Upgrades the village hall
	 */
	@Override
	public void upgrade() {
		level++;

		if (level < MAX_LEVEL) {
			upgradeCost = UPGRADE_COSTS[level];
			upgradeTime = UPGRADE_TIMES[level];
		}

		setAllMaxLevel(level);
		System.out.println("Inside 6");
		setAllMaxCount();
		System.out.println("Inside 7");
	}

	/**
	 * This method sets the maximum upgrade level of the other Building subclasses.
	 * @param level the maximum upgrade level
	 */
	private void setAllMaxLevel(int level) {
		Farm.setMaxLevel(level);
		LumberMill.setMaxCount(level);
		IronMine.setMaxLevel(level);
		GoldMine.setMaxLevel(level);
		ArcherTower.setMaxLevel(level);
		Cannon.setMaxLevel(level);
	}

	/**
	 * This method sets the maximum number of the other Building subclasses.
	 */
	private void setAllMaxCount() {
		Farm.setMaxCount(Farm.getMaxCount() + 5);
		LumberMill.setMaxCount(LumberMill.getMaxCount() + 2);
		IronMine.setMaxCount(IronMine.getMaxCount() + 2);
		GoldMine.setMaxCount(GoldMine.getMaxCount() + 2);
		ArcherTower.setMaxCount(ArcherTower.getMaxCount() + 5);
		Cannon.setMaxCount(Cannon.getMaxCount() + 5);
	}
}