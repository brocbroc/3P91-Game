package gameElements;

import utility.*;

public class GoldMineConstructor implements BuildingConstructor {
    private static final Cost BUILD_COST = new Cost(0, 15, 25);
    private static final int BUILD_TIME = 20;

    @Override public Cost getBuildCost() { return BUILD_COST; }
    @Override public int getBuildTime() { return BUILD_TIME; }
    @Override public Building addBuilding(Position pos) { return new GoldMine(pos); }
}
