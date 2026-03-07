package game;

import gameElements.*;
import gameElements.building.Building;
import gameElements.building.Farm;
import java.util.ArrayList;
import java.util.List;
import utility.*;

/**
 * This class represents a village. It stores the buildings and the inhabitants of the village.
 */
public class Village {
	private final int PLAYER_ID;
	private static final int MAP_ROW_COUNT = 10;
	private static final int MAP_COL_COUNT = 20;
	private Building[][] map;
	private Inventory inventory;
	private int maxPopulation;
	private List<Worker> freeWorkers;
	private List<Worker> busyWorkers;
	private List<Inhabitant> inhabitants;//all inhabitants
	private List<Fighter> army;//only fighters for attacking

	/**
	 * Class constructor.
	 * @param id the ID of the player the village belongs to
	 */
	public Village(int id) {
		PLAYER_ID = id;
		map = new Building[MAP_ROW_COUNT][];

		for (int i = 0; i < map.length; i++) {
			map[i] = new Building[MAP_COL_COUNT];
		}

		inventory = new Inventory(1000, 1000, 1000);
		maxPopulation = 10;
		freeWorkers = new ArrayList<>();
		busyWorkers = new ArrayList<>();
		freeWorkers.add(new Worker());
		freeWorkers.add(new Worker());
		inhabitants = new ArrayList<>();
		army = new ArrayList<>();
		inhabitants.addAll(freeWorkers);
	}

	/**
	 * Returns the player ID.
	 * @return the player ID
	 */
	public int getPlayerID() { return PLAYER_ID; }

	/**
	 * Returns the number of rows in <code>map</code>.
	 * @return the number of rows in <code>map</code>
	 */
	public static int getMapRowCount() { return MAP_ROW_COUNT; }

	/**
	 * Returns the number of columns in <code>map</code>.
	 * @return the number of columns in <code>map</code>
	 */
	public static int getMapColCount() { return MAP_COL_COUNT; }

	/**
	 * Returns the map of the village.
	 * @return <code>map</code>
	 */
	public Building[][] getMap() { return map; }

	/**
	 * Checks if a given map square is occupied.
	 * @param pos the position to check
	 * @return <code>true</code> if the square is occupied, <code>false</code> if not
	 */
	public boolean isSquareFull(Position pos) { return map[pos.X][pos.Y] != null; }

	/**
	 * Returns the building at the given map square.
	 * @param pos the position to retrieve from
	 * @return the <code>Building</code> object at the position
	 */
	public Building getBuilding(Position pos) { return map[pos.X][pos.Y]; }

	/**
	 * Returns the current inventory count.
	 * @return an array of the inventory count of gold, iron, and lumber, respectively
	 */
	public int[] getInventoryValues() {
		return new int[] {inventory.getGold(), inventory.getIron(), inventory.getLumber()};
	}

	/**
	 * Adds a building to the map if the cost can be paid and there is a free worker.
	 * Sets the free worker to busy and returns the worker.
	 * @param constructor the building constructor
	 * @param pos the position of the building
	 * @return the worker constructing the building if a building is added, <code>null</code> if a
	 * building cannot be added
	 */
	public Worker tryAddBuilding(BuildingConstructor constructor, Position pos) {
		if (!inventory.checkCost(constructor.getBuildCost()) || freeWorkers.isEmpty()) {
			return null;
		}

		Worker builder = freeWorkers.remove(0);
		builder.setBusy(true);
		busyWorkers.add(builder);
		inventory.payCost(constructor.getBuildCost());
		map[pos.X][pos.Y] = constructor.addBuilding(pos);
		return builder;
	}

	/**
	 * Finishes building construction. Releases the builder and sets the building as not under
	 * construction.
	 * @param builder the worker constructing the building
	 * @param pos the position of the building
	 */
	public synchronized void completeAddBuilding(Worker builder, Position pos) {
		builder.setBusy(false);
		busyWorkers.remove(builder);
		freeWorkers.add(builder);
		Building b = this.getBuilding(pos);
		b.setUnderConstruction(false);
		System.out.println(maxPopulation);

		if (b instanceof Farm) {
			maxPopulation += ((Farm) b).getPopulationIncrease();
			System.out.println(maxPopulation);
		}
	}

	/**
	 * Begins the upgrade building process if the building is not under construction, the cost can
	 * be paid, and there is a free worker.
	 * @param b the building to upgrade
	 * @return the worker upgrading the building if the building can be upgrade,
	 * <code>null</code> if the building cannot be upgraded
	 */
	public Worker tryUpgradeBuilding(Building b) {
		if (b.isUnderConstruction() || !inventory.checkCost(b.getUpgradeCost()) ||
			freeWorkers.isEmpty()) {
			return null;
		}

		Worker builder = freeWorkers.remove(0);
		builder.setBusy(true);
		busyWorkers.add(builder);
		inventory.payCost(b.getUpgradeCost());
		b.setUnderConstruction(true);
		return builder;
	}

	/**
	 * Finishes the upgrade building process. Releases the worker, applies the upgrade, and sets
	 * the building as not under construction.
	 * @param b the building being upgraded
	 * @param builder the worker upgrading the building
	 */
	public synchronized void completeUpgradeBuilding(Building b, Worker builder) {
		builder.setBusy(false);
		busyWorkers.remove(builder);
		freeWorkers.add(builder);
		b.setUnderConstruction(false);
		b.upgrade();
		System.out.println(maxPopulation);

		if (b instanceof Farm) { // TEST FARM
			maxPopulation += ((Farm) b).getPopulationIncrease();
			System.out.println(maxPopulation);
		}
	}
}
