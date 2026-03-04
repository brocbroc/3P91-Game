package game;

import gameElements.*;
import utility.*;

public class Player {
	private int playerID;
	private Village village;

	public Player(int playerID, Village village) {
		this.playerID = playerID;
		this.village = village;
	}

	public Village getVillage() { return village; }

	public static Cost getBuildCost(BuildingType type) {
		switch (type) {
			case VILLAGE_HALL:
				return VillageHall.getBuildCost();
			case FARM:
				return Farm.getBuildCost();
			default:
				return null;
		}
	}

	public static int getBuildTime(BuildingType type) {
		switch (type) {
			case VILLAGE_HALL:
				return VillageHall.getBuildTime();
			case FARM:
				return Farm.getBuildTime();
			default:
				return 0;
		}
	}

	public Worker checkAddBuilding(BuildingType type, Position pos) {
		if (village.isSquareFull(pos)) {
			return null;
		}

		Cost buildCost = getBuildCost(type);

		if (!village.checkInventory(buildCost)) {
			return null;
		}

		Worker w = village.getFreeWorker();

		if (w == null) {
			return null;
		}

		village.payCost(buildCost);
		return w; // totally not thread safe right now
		// also make sure inventory and map are thread safe
	}

	public void addBuilding(BuildingType type, Position pos) {

	}

	// Testing purposes only
	public void addBuildingTest(BuildingType type, Position pos) {
		village.addBuildingTest(type, pos);
	}
}
