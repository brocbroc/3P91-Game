package gameElements.building;

import utility.*;

public class IronMine extends Building {
    static {
        maxLevel = 1;
        count = 0;
        maxCount = 2;
        buildCost = new Cost(0, 20, 0);
        buildTime = 40;
        upgradeCosts = new Cost[] {
            new Cost(0, 30, 10),
            new Cost(5, 40, 20),
            new Cost(10, 50, 30)};
        upgradeTimes = new int[] { 30, 45, 60, 75 };
    }

    public IronMine(Position pos) {
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
    public String draw() { return "I"; }
}
