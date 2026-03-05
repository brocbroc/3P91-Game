package gameElements;

import utility.*;

public class GoldMine extends Building {
    private static final int MAX_LEVEL = 3;
    private static final Cost BUILD_COST = new Cost(0, 15, 25);
    private static final int BUILD_TIME = 20;

    public GoldMine(Position pos) { super(pos); }

    @Override public int getMaxLevel() { return MAX_LEVEL; }
    @Override public Cost getBuildCost() { return BUILD_COST; }
    @Override public int getBuildTime() { return BUILD_TIME; }

    @Override
    public Cost getUpgradeCost() {
        return new Cost(6 * getLevel(), 6 * getLevel(), 8 * getLevel());
    }
}
