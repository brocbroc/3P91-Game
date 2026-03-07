package game;

import gameElements.*;
import gameElements.building.Building;
import gameElements.building.Farm;
import gameElements.inhabitant.*;
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
	private int population;
	private PeasantList<Worker> workers;
	private PeasantList<Lumberman> lumbermen;
	private PeasantList<IronMiner> ironMiners;
	private PeasantList<GoldMiner> goldMiners;
	private Army fighters;

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
		population = 1;
		workers = new PeasantList<>();
		lumbermen = new PeasantList<>();
		ironMiners = new PeasantList<>();
		goldMiners = new PeasantList<>();
		fighters = new Army();
		workers.addPeasant(new Worker());

		army = new ArrayList<>();
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
	 * Returns whether or not the village is full of inhabitants.
	 * @return <code>true</code> if the village is full, <code>false</code> if not
	 */
	public boolean isVillageFull() {
		return population >= maxPopulation;
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
		if (!inventory.checkCost(constructor.getBuildCost()) || workers.isFreePeasantEmpty()) {
			return null;
		}

		Worker w = workers.getPeasant();
		w.setPosition(pos);
		inventory.payCost(constructor.getBuildCost());
		map[pos.X][pos.Y] = constructor.addBuilding(pos);
		return w;
	}

	/**
	 * Finishes building construction. Releases the builder and sets the building as not under
	 * construction.
	 * @param w the worker constructing the building
	 * @param pos the position of the building
	 */
	public synchronized void completeAddBuilding(Worker w, Position pos) {
		workers.freePeasant(w);
		Building b = this.getBuilding(pos);
		b.setUnderConstruction(false);

		if (b instanceof Farm) {
			maxPopulation += ((Farm) b).getPopulationIncrease();
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
		if (b.isUnderConstruction() || !inventory.checkCost(b.getUpgradeCost())
			|| workers.isFreePeasantEmpty()) {
			return null;
		}

		Worker w = workers.getPeasant();
		w.setPosition(b.getPosition());
		inventory.payCost(b.getUpgradeCost());
		b.setUnderConstruction(true);
		return w;
	}

	/**
	 * Finishes the upgrade building process. Releases the worker, applies the upgrade, and sets
	 * the building as not under construction.
	 * @param b the building being upgraded
	 * @param w the worker upgrading the building
	 */
	public synchronized void completeUpgradeBuilding(Building b, Worker w) {
		workers.freePeasant(w);
		b.setUnderConstruction(false);
		b.upgrade();

		if (b instanceof Farm) {
			maxPopulation += ((Farm) b).getPopulationIncrease();
		}
	}

	/**
	 * Adds an inhabitant if the cost can be paid.
	 * @param constructor the inhabitant constructor
	 * @param type the type of inhabitant
	 * @return <code>true</code> if an inhabitant was added, <code>false</code> otherwise
	 */
	public boolean tryAddInhabitant(InhabitantConstructor constructor, InhabitantType type) {
		if (!inventory.checkCost(constructor.getProductionCost())) {
			return false;
		}

		inventory.payCost(constructor.getProductionCost());
		Inhabitant person = constructor.addInhabitant();
		population++;

		switch (type) {
			case WORKER:
				workers.addPeasant((Worker) person);
				System.out.println("Worker count: " + workers.getCount());
				break;
			case LUMBERMAN:
				lumbermen.addPeasant((Lumberman) person);
				System.out.println("Lumberman count: " + lumbermen.getCount());
				break;
			case IRON_MINER:
				ironMiners.addPeasant((IronMiner) person);
				System.out.println("Iron miner count: " + ironMiners.getCount());
				break;
			case GOLD_MINER:
				goldMiners.addPeasant((GoldMiner) person);
				System.out.println("Gold miner count: " + goldMiners.getCount());
				break;
			case SOLDIER:
				fighters.addSoldier((Soldier) person);
				System.out.println("Fighter count: " + fighters.getCount());
				break;
			case ARCHER:
				fighters.addArcher((Archer) person);
				System.out.println("Fighter count: " + fighters.getCount());
				break;
			case KNIGHT:
				fighters.addKnight((Knight) person);
				System.out.println("Fighter count: " + fighters.getCount());
				break;
			case CATAPULT:
				fighters.addCatapult((Catapult) person);
				System.out.println("Fighter count: " + fighters.getCount());
				break;
		}

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

		//total += inhabitants.size();
		return total;
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
