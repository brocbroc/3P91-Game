package gameElements.building;

import utility.*;

public class VillageHall extends Building {
	static {
		maxLevel = 5;
		count = 0;
		maxCount = 1;
		buildCost = new Cost(5, 5, 5);
		buildTime = 30;
		upgradeCosts = new Cost[] {
			new Cost(10, 10, 10),
			new Cost(20, 20, 20),
			new Cost(40, 40, 40)};
		upgradeTimes = new int[] { 20, 40, 60, 80 };
	}

	public VillageHall(Position pos) {
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
	public String draw() { return "H"; }
}