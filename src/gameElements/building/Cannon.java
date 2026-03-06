package gameElements.building;

import gameElements.Damager;
import utility.*;

public class Cannon extends Building implements Damager {
    private final double baseDamage = 12.0;

    static {
        maxLevel = 1;
        count = 0;
        maxCount = 5;
        buildCost = new Cost(20, 20, 0);
        buildTime = 60;
        upgradeCosts = new Cost[] {
            new Cost(30, 30, 10),
            new Cost(40, 40, 20),
            new Cost(50, 50, 30)};
        upgradeTimes = new int[] { 45, 60, 75, 90 };
    }

    public Cannon(Position pos) {
        super(pos);
        count++;
        upgradeCost = upgradeCosts[0];
        upgradeTime = upgradeTimes[0];
    }

    public static int getMaxLevel() { return maxLevel; }

    public static int getCount() { return count; }

    public static int getMaxCount() { return maxCount; }

    public static Cost getBuildCost() { return buildCost; }

    public static int getBuildTime() { return buildTime; }

    /**
     * Returns the character representing the building
     * @return a character
     */
    public String draw() { return "C"; }

    @Override
    public int damage() {return 12; }
}