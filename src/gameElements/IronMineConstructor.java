package gameElements;

import utility.*;

public class IronMineConstructor implements BuildingConstructor {
    private static final Cost BUILD_COST = new Cost(0, 10, 20);
    private static final int BUILD_TIME = 18;

    @Override public Cost getBuildCost() { return BUILD_COST; }
    @Override public int getBuildTime() { return BUILD_TIME; }
    @Override public Building addBuilding(Position pos) { return new IronMine(pos); }
}