package gameElements.building;

import utility.*;

/**
 * This class represents a Building.
 * DO NOT TOUCH
 */
public abstract class Building {
	protected static final int MAX_LEVEL = 4;
	protected Position position;
	protected int level;
	protected Cost upgradeCost;
	protected int upgradeTime; // seconds
	protected boolean isUnderConstruction;
	protected int hitPoints;

	/**
	 * Class constructor.
	 * @param pos the position of the new building
	 */
	public Building(Position pos) {
		position = pos;
		level = 0;
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
	 * Returns the maximum possible level of buildings of this type.
	 * @return the maximum possible level
	 */
	public abstract int getMaxLevel();

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

	/**
	 * Upgrades the building
	 */
	public abstract void upgrade();
}
