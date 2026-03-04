package gameElements;

import utility.*;

public class VillageHall extends Building {
	static {
		maxLevel = 5;
		buildCost = new Cost(5, 5, 5);
		buildTime = 30;
	}

	public VillageHall(Position pos) {
		super(pos);
	}

	public static Cost getBuildCost() { return buildCost; }

	public static int getBuildTime() { return buildTime; }
}
