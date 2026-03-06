package gameElements.building;

import utility.*;

public class Farm extends Building {
	static {
		maxLevel = 1;
		count = 0;
		maxCount = 5;
		buildCost = new Cost(0, 10, 10);
		buildTime = 10;
		upgradeCosts = new Cost[] {
			new Cost(5, 10, 10),
			new Cost(10, 20, 20),
			new Cost(15, 40, 40)};
		upgradeTimes = new int[] { 10, 20, 30, 40 };
	}

	public Farm(Position pos) {
		super(pos);
		count++;
		upgradeCost = upgradeCosts[0];
		upgradeTime = upgradeTimes[0];
	}

	public static int getMaxLevel() { return maxLevel; }

	public static int getCount() { return count; }

	public static int getMaxCount() { return maxCount; }

	public static Cost getBuildCost() { return buildCost; }

	public static int getBuildTime() { return buildTime; }

	/**
	 * Returns the character representing the building
	 * @return a character
	 */
	public String draw() { return "F"; }
}

