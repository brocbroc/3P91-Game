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
