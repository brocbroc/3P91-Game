package gameElements.building;

import gameElements.Damager;
import utility.*;

public class ArcherTower extends Building implements Damager {
    private final double baseDamage = 8.0;

    static {
        maxLevel = 1;
        count = 0;
        maxCount = 5;
        buildCost = new Cost(5, 10, 20);
        buildTime = 60;
        upgradeCosts = new Cost[] {
            new Cost(10, 20, 30),
            new Cost(15, 30, 40),
            new Cost(20, 40, 50)};
        upgradeTimes = new int[] { 45, 60, 75, 90 };
    }

    public ArcherTower(Position pos) {
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
    public String draw() { return "A"; }

    @Override
    public int damage(){return 8;}
}
