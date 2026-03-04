package gameElements;

import utility.*;

public class Farm extends Building {
	static {
		maxLevel = 1;
		buildCost = new Cost(0, 5, 5);
		buildTime = 10;
	}

	public Farm(Position pos) {
		super(pos);
	}

	public static Cost getBuildCost() { return buildCost; }

	public static int getBuildTime() { return buildTime; }
}
