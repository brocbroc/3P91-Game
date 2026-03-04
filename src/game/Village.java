package game;

import gameElements.*;
import java.util.ArrayList;
import java.util.List;
import utility.*;

public class Village {
	public final int PLAYER_ID;
	private Building[][] map;
	private Inventory inventory;
	private List<Worker> workers;
	public static final int MAP_ROW_COUNT = 10;
	public static final int MAP_COL_COUNT = 20;

	public Village(int id) {
		PLAYER_ID = id;
		map = new Building[MAP_ROW_COUNT][];

		for (int i = 0; i < map.length; i++) {
			map[i] = new Building[MAP_COL_COUNT];
		}

		inventory = new Inventory(100, 100, 100);
		workers = new ArrayList<>();
		workers.add(new Worker());
	}

	public Building[][] getMap() { return map; }

	/**
	 * This method returns true if the map square contains a building
	 * @param pos the position to check
	 * @return true if the square is occupied, false if not
	 */
	public boolean isSquareFull(Position pos) {
		return map[pos.x][pos.y] != null;
	}

	public boolean checkInventory(Cost cost) {
		return inventory.checkCost(cost);
	}

	public void payCost(Cost cost) {
		inventory.payCost(cost);
	}

	public Worker getFreeWorker() {
		for (Worker w : workers) {
			if (!w.isBusy()) {
				return w;
			}
		}

		return null;
	}

	// Testing purposes only
	public void addBuildingTest(BuildingType type, Position pos) {
		switch (type) {
			case VILLAGE_HALL:
				map[pos.x][pos.y] = new VillageHall(pos);
				break;
			case FARM:
				map[pos.x][pos.y] = new Farm(pos);
				break;
		}
	}
}
