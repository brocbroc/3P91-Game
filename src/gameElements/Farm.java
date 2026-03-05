package gameElements;

import utility.*;

public class Farm extends Building {
	private static final int MAX_LEVEL = 1;
	private static final Cost BUILD_COST = new Cost(0, 5, 5);
	private static final int BUILD_TIME = 10;

	public Farm(Position pos) {
		super(pos);
	}

	@Override public int getMaxLevel() { return MAX_LEVEL; }
	@Override public Cost getBuildCost() { return BUILD_COST; }
	@Override public int getBuildTime() { return BUILD_TIME; }
}

