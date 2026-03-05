package gameElements;

import utility.*;

public class VillageHall extends Building {
	private static final int MAX_LEVEL = 5;
	private static final Cost BUILD_COST = new Cost(5, 5, 5);
	private static final int BUILD_TIME = 30;

	public VillageHall(Position pos) {
		super(pos);
	}

	@Override
	public int getMaxLevel() {
		return MAX_LEVEL;
	}

	@Override
	public Cost getBuildCost() {
		return BUILD_COST;
	}

	@Override
	public int getBuildTime() {
		return BUILD_TIME;
	}

	@Override
	public Cost getUpgradeCost() {
		// slightly more expensive per level
		return new Cost(5 * getLevel(), 5 * getLevel(), 5 * getLevel());
	}
}