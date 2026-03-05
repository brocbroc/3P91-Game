package gameElements;

import utility.*;

/**
 * This class represents a Building.
 */
public abstract class Building {
	protected Position position;
	protected int level;
	protected static int maxLevel;
	protected static Cost buildCost;
	protected static int buildTime; // seconds
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

	public Position getPosition() { return position; }

	public  int getLevel() { return level; }

	/**
	 * Upgrade this building by 1 level (caller should check max level).
	 */
	public void upgrade(){level++;}

	/**
	 * @return maximum level allowed for this building type
	 */
	public abstract int getMaxLevel();

	/**
	 * @return cost to build this building
	 */
	public abstract Cost getBuildCost();

	/**
	 * @return time to build (seconds)
	 */
	public abstract int getBuildTime();
	/**
	 * @return cost to upgrade from current level (simple scaling)
	 */
	public Cost getUpgradeCost() {
		// Simple default upgrade formula; override per building if you want
		return new Cost(2 * level, 2 * level, 2 * level);
	}

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
}
