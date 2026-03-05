package gameElements.building;

import utility.*;

public class LumberMill extends Building {
    static {
        maxLevel = 1;
        count = 0;
        maxCount = 2;
        buildCost = new Cost(0, 0, 20);
        buildTime = 20;
        upgradeCosts = new Cost[] {
            new Cost(0, 5, 30),
            new Cost(5, 10, 40),
            new Cost(10, 15, 50)};
        upgradeTimes = new int[] { 15, 30, 45, 60 };
    }

    public LumberMill(Position pos) {
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
    public String draw() { return "L"; }
}
