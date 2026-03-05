package gameElements;

import utility.*;

public class LumberMill extends Building {
    private static final int MAX_LEVEL = 3;
    private static final Cost BUILD_COST = new Cost(0, 5, 15);
    private static final int BUILD_TIME = 15;

    public LumberMill(Position pos) { super(pos); }

    @Override public int getMaxLevel() { return MAX_LEVEL; }
    @Override public Cost getBuildCost() { return BUILD_COST; }
    @Override public int getBuildTime() { return BUILD_TIME; }

    @Override
    public Cost getUpgradeCost() {
        return new Cost(2 * getLevel(), 3 * getLevel(), 6 * getLevel());
    }
}
