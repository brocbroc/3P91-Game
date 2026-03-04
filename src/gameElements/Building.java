package gameElements;

import utility.*;

public abstract class Building {
	protected Position position;
	protected int level;
	protected static int maxLevel;
	protected int hitPoints;
	protected static Cost buildCost;
	protected static int buildTime; // seconds

	public Building(Position pos) {
		position = pos;
		level = 1;
	}
}
