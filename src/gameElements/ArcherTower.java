package gameElements;

import utility.*;

public class ArcherTower extends Building implements Damager {
    private static final int MAX_LEVEL = 4;
    private static final Cost BUILD_COST = new Cost(0, 10, 20);
    private static final int BUILD_TIME = 15;

    private final double baseDamage = 8.0;

    public ArcherTower(Position pos) { super(pos); }

    @Override public int getMaxLevel() { return MAX_LEVEL; }
    @Override public Cost getBuildCost() { return BUILD_COST; }
    @Override public int getBuildTime() { return BUILD_TIME; }

    @Override
    public int damage(){return 8;}
}
