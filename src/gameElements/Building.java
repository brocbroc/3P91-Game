package gameElements;

import utility.*;

public abstract class Building {
	protected Position position;
	protected int level;
	protected static int maxLevel;
	protected int hitPoints;
	protected static Cost buildCost;
	protected static int buildTime; // seconds
	protected boolean isUnderConstruction;

	public Building(Position pos) {
		position = pos;
		level = 1;
		isUnderConstruction = true;
	}

	public boolean isUnderConstruction() { return isUnderConstruction; }

	public void setUnderConstruction(boolean b) { isUnderConstruction = b; }
}
