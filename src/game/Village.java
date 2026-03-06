package game;

import gameElements.*;
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
	private Building getBuilding(Position pos) { return map[pos.X][pos.Y]; }

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
		if (!inventory.checkCost(constructor.getBuildCost())) {
			return null;
		}

		if (freeWorkers.isEmpty()) {
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
		this.getBuilding(pos).setUnderConstruction(false);
	}

	/**
	 * Attempts to pay a cost from the village inventory.
	 * The inventory is only updated if the village has enough resources.
	 *
	 * @param cost the resource cost to be paid
	 * @return true if the cost was successfully paid, false otherwise
	 */
	public boolean tryPayCost(Cost cost) {
		if (!inventory.checkCost(cost)) {
			return false;
		}

		inventory.payCost(cost);
		return true;
	}

	/**
	 * Adds loot to the village inventory.
	 * This is typically called after a successful attack where the village
	 * gains resources from another village.
	 *
	 * @param loot the amount of gold, iron, and lumber to add
	 */
	public void addLoot(Cost loot) {
		inventory.addGold(loot.GOLD);
		inventory.addIron(loot.IRON);
		inventory.addLumber(loot.LUMBER);
	}

	/**
	 * Removes resources from the village inventory.
	 * This method is used when the village is attacked and loses resources.
	 * The loss is clamped so that the inventory values never become negative.
	 *
	 * @param loss the amount of gold, iron, and lumber to remove
	 */
	public void removeLoot(Cost loss) {
		int goldLoss = Math.min(loss.GOLD, inventory.getGold());
		int ironLoss = Math.min(loss.IRON, inventory.getIron());
		int lumberLoss = Math.min(loss.LUMBER, inventory.getLumber());

		inventory.payCost(new Cost(goldLoss, ironLoss, lumberLoss));
	}

	/**
	 * Trains or adds a new inhabitant to the village.
	 * The inhabitant will only be added if the village can pay the required cost.
	 * Workers are added to the free worker list, while fighters are added to the army.
	 *
	 * @param h the inhabitant to add to the village
	 * @param trainingCost the cost required to train the inhabitant
	 * @return true if the inhabitant was successfully trained, false otherwise
	 */
	public boolean trainInhabitant(Inhabitant h, Cost trainingCost) {
		if (!tryPayCost(trainingCost)) {
			return false;
		}

		inhabitants.add(h);

		if (h instanceof Worker) {
			freeWorkers.add((Worker) h);
		}

		if (h instanceof Fighter) {
			army.add((Fighter) h);
		}

		return true;
	}

	/**
	 * Calculates the attack score of the village.
	 * The attack score is based on the total damage output of all army units.
	 *
	 * @return the total attack score of the village army
	 */
	public int getAttackScore() {
		int total = 0;

		for (Fighter fighter : army) {
			total += fighter.damage();
		}

		return total;
	}

	/**
	 * Calculates the defense score of the village.
	 * The defense score is determined by the damage to defense buildings
	 * (such as archer towers and cannons) and the size of the population.
	 *
	 * @return the defense score of the village
	 */
	public int getDefenseScore() {
		int total = 0;

		for (Building[] row : map) {
			for (Building building : row) {
				if (building != null && !building.isUnderConstruction() && building instanceof Damager) {
					total += ((Damager) building).damage();
				}
			}
		}

		total += inhabitants.size();
		return total;
	}

	/**
	 * Returns the total number of inhabitants currently in the village.
	 *
	 * @return the population size of the village
	 */
	public int getPopulation() {
		return inhabitants.size();
	}

	/**
	 * Returns the number of army units in the village.
	 *
	 * @return the number of fighters in the village army
	 */
	public int getArmySize() {
		return army.size();
	}
}
