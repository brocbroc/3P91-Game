package gameElements.building;

import gameElements.BuildingType;
import utility.Cost;
import utility.Position;
import java.util.EnumMap;

/**
 * This class represents a village hall.
 * The level of the village hall determines the maximum upgrade level and the maximum number of
 * buildings for the other Building subclasses. Only one village hall may exist.
 */
public class VillageHall extends Building {
	private static final Cost BUILD_COST;
	private static final int BUILD_TIME; // seconds
	private static final Cost[] UPGRADE_COSTS;
	private static final int[] UPGRADE_TIMES;
	private EnumMap<BuildingType, BuildingData> buildingData;
	private VillageHallData data;

	static {
		BUILD_COST = new Cost(5, 5, 5);
		BUILD_TIME = 5;
		UPGRADE_COSTS = new Cost[] {
			new Cost(10, 10, 10),
			new Cost(20, 20, 20),
			new Cost(30, 30, 30),
			new Cost(40, 40, 40)
		};
		UPGRADE_TIMES = new int[] { 5, 5, 5, 5 };
	}

	/**
	 * Class constructor.
	 * @param pos the position of the new village hall
	 */
	public VillageHall(Position pos, VillageHallData data, EnumMap<BuildingType, BuildingData> buildingData) {
		super(pos);
		this.data = data;
		this.buildingData = buildingData;
		data.incrementCount();
		upgradeCost = UPGRADE_COSTS[0];
		upgradeTime = UPGRADE_TIMES[0];
		hitPoints = 500;
	}

	/**
	 * Returns the build cost
	 * @return the build cost
	 */
	public static Cost getBuildCost() {
		return BUILD_COST;
	}

	/**
	 * Returns the build time
	 * @return the build time, in seconds
	 */
	public static int getBuildTime() {
		return BUILD_TIME;
	}

	/**
	 * Returns the maximum level of the building
	 * @return the maximum level of the building
	 */
	public int getMaxLevel() {
		return data.getMaxLevel();
	}

	/**
	 * Returns the character representing the building
	 * @return a character
	 */
	@Override
	public String draw() {
		return "H";
	}

	/**
	 * Upgrades the village hall
	 */
	@Override
	public void upgrade() {
		level++;

		if (level < MAX_LEVEL) {
			upgradeCost = UPGRADE_COSTS[level];
			upgradeTime = UPGRADE_TIMES[level];
		}

		setAllMaxLevel(level);
		setAllMaxCount();
		hitPoints += 100;
	}

	/**
	 * This method sets the maximum upgrade level of the other Building subclasses.
	 * @param level the maximum upgrade level
	 */
	private void setAllMaxLevel(int level) {
		buildingData.get(BuildingType.FARM).setMaxLevel(level);
		buildingData.get(BuildingType.LUMBER_MILL).setMaxLevel(level);
		buildingData.get(BuildingType.IRON_MINE).setMaxLevel(level);
		buildingData.get(BuildingType.GOLD_MINE).setMaxLevel(level);
		buildingData.get(BuildingType.ARCHER_TOWER).setMaxLevel(level);
		buildingData.get(BuildingType.CANNON).setMaxLevel(level);
	}

	/**
	 * This method sets the maximum number of the other Building subclasses.
	 */
	private void setAllMaxCount() {
		buildingData.get(BuildingType.FARM).setMaxCount(level);
		buildingData.get(BuildingType.LUMBER_MILL).setMaxCount(level);
		buildingData.get(BuildingType.IRON_MINE).setMaxCount(level);
		buildingData.get(BuildingType.GOLD_MINE).setMaxCount(level);
		buildingData.get(BuildingType.ARCHER_TOWER).setMaxCount(level);
		buildingData.get(BuildingType.CANNON).setMaxCount(level);
	}
}