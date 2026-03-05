package gameElements.building;

import utility.*;

/**
 * This class represents a Building.
 */
public abstract class Building {
	protected Position position;
	protected int level;
	protected static int maxLevel;
	protected static int count;
	protected static int maxCount;
	protected static int[] maxCounts;
	protected static Cost buildCost;
	protected static int buildTime; // seconds
	protected Cost upgradeCost;
	protected static Cost[] upgradeCosts;
	protected int upgradeTime; // seconds
	protected static int[] upgradeTimes;
	protected boolean isUnderConstruction;

	/**
	 * Class constructor.
	 * @param pos the position of the new building
	 */
	public Building(Position pos) {
		position = pos;
		level = 1;
		isUnderConstruction = true;
	}

	/**
	 * Returns the position of the building.
	 * @return the position of the building
	 */
	public Position getPosition() { return position; }

	/**
	 * Returns the level of the building.
	 * @return the level of the building
	 */
	public int getLevel() { return level; }

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	public Cost getUpgradeCost() { return upgradeCost; }


	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	public int getUpgradeTime() { return upgradeTime; }

	/**
	 * Checks if the building is under construction
	 * @return <code>true</code> if the building is under construction, <code>false</code>
	 * otherwise
	 */
	public boolean isUnderConstruction() { return isUnderConstruction; }

	/**
	 * Set the construction status
	 * @param b the construction status
	 */
	public void setUnderConstruction(boolean b) { isUnderConstruction = b; }

	/**
	 * Returns the character representing the building
	 * @return a character
	 */
	public abstract String draw();
}
