package game;

import ChallengeDecision.ChallengeEntitySet;
import gameElements.*;
import gameElements.building.*;
import gameElements.inhabitant.*;

import java.util.EnumMap;
import java.util.Random;

import utility.*;

/**
 * This class represents a village. It stores the buildings and the inhabitants of the village.
 * Any changes to the contents of the village are handled by this class.
 */
public class Village {
	private static final int MAP_ROW_COUNT = 10;
	private static final int MAP_COL_COUNT = 20;
	private Building[][] map;
	private EnumMap<BuildingType, BuildingData> buildingData;
	private Inventory inventory;
	private int level;
	private int maxPopulation;
	private int population;
	private EnumMap<InhabitantType, InhabitantData> inhabitantData;
	private PeasantList<Worker> workers;
	private PeasantList<Lumberman> lumbermen;
	private PeasantList<IronMiner> ironMiners;
	private PeasantList<GoldMiner> goldMiners;
	private Army fighters;
	private int attackWins;
	private int attackFails;

	/**
	 * Class constructor.
	 */
	public Village() {
		map = new Building[MAP_ROW_COUNT][];

		for (int i = 0; i < map.length; i++) {
			map[i] = new Building[MAP_COL_COUNT];
		}

		buildingData = new EnumMap<>(BuildingType.class);
		inventory = new Inventory(1000, 1000, 1000);
		level = 0;
		maxPopulation = 10;
		population = 1;
		inhabitantData = new EnumMap<>(InhabitantType.class);
		workers = new PeasantList<>();
		lumbermen = new PeasantList<>();
		ironMiners = new PeasantList<>();
		goldMiners = new PeasantList<>();
		fighters = new Army();
		attackWins = 0;
		attackFails = 0;
		buildingData.put(BuildingType.VILLAGE_HALL, new VillageHallData());
		buildingData.put(BuildingType.FARM, new FarmData());
		buildingData.put(BuildingType.LUMBER_MILL, new LumberMillData());
		buildingData.put(BuildingType.IRON_MINE, new IronMineData());
		buildingData.put(BuildingType.GOLD_MINE, new GoldMineData());
		buildingData.put(BuildingType.ARCHER_TOWER, new ArcherTowerData());
		buildingData.put(BuildingType.CANNON, new CannonData());
		inhabitantData.put(InhabitantType.WORKER, new WorkerData());
		inhabitantData.put(InhabitantType.LUMBERMAN, new LumbermanData());
		inhabitantData.put(InhabitantType.IRON_MINER, new IronMinerData());
		inhabitantData.put(InhabitantType.GOLD_MINER, new GoldMinerData());
		inhabitantData.put(InhabitantType.SOLDIER, new SoldierData());
		inhabitantData.put(InhabitantType.ARCHER, new ArcherData());
		inhabitantData.put(InhabitantType.KNIGHT, new KnightData());
		inhabitantData.put(InhabitantType.CATAPULT, new CatapultData());
		workers.addPeasant(new Worker());
	}

	/**
	 * Returns the number of rows in <code>map</code>.
	 * @return the number of rows in <code>map</code>
	 */
	public static int getMapRowCount() {
		return MAP_ROW_COUNT;
	}

	/**
	 * Returns the number of columns in <code>map</code>.
	 * @return the number of columns in <code>map</code>
	 */
	public static int getMapColCount() {
		return MAP_COL_COUNT;
	}

	/**
	 * Returns the map of the village.
	 * @return <code>map</code>
	 */
	public Building[][] getMap() {
		return map;
	}

	/**
	 * Checks if a given map square is occupied.
	 * @param pos the position to check
	 * @return <code>true</code> if the square is occupied, <code>false</code> if not
	 */
	public boolean isSquareFull(Position pos) {
		return map[pos.X][pos.Y] != null;
	}

	/**
	 * Returns the building at the given map square.
	 * @param pos the position to retrieve from
	 * @return the <code>Building</code> object at the position
	 */
	public Building getBuilding(Position pos) {
		return map[pos.X][pos.Y];
	}

	/**
	 * Returns the building data of the specified type
	 * @param type the type of building
	 * @return the building data
	 */
	public BuildingData getBuildingData(BuildingType type) {
		return buildingData.get(type);
	}

	/**
	 * Returns the building data of the village for all types of buildings
	 * @return the building data
	 */
	public EnumMap<BuildingType, BuildingData> getAllBuildingData() {
		return buildingData;
	}

	/**
	 * Returns the current inventory count.
	 * @return an array of the inventory count of gold, iron, and lumber, respectively
	 */
	public int[] getInventoryValues() {
		return new int[] { inventory.getGold(), inventory.getIron(), inventory.getLumber() };
	}

	/**
	 * Return the level of the village
	 * @return the level of the village
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Returns whether or not the village is full of inhabitants.
	 * @return <code>true</code> if the village is full, <code>false</code> if not
	 */
	public boolean isVillageFull() {
		return population >= maxPopulation;
	}

	/**
	 * Returns the work rate of the workers
	 * @return the work rate
	 */
	public double getWorkRate() {
		return ((WorkerData) inhabitantData.get(InhabitantType.WORKER)).getWorkRate();
	}

	/**
	 * Returns the inhabitant data of the specified type
	 * @param type the type of inhabitant
	 * @return the inhabitant data
	 */
	public InhabitantData getInhabitantData(InhabitantType type) {
		return inhabitantData.get(type);
	}

	/**
	 * Returns the inhabitant data for all types
	 * @return the inhabitant data of all types
	 */
	public EnumMap<InhabitantType, InhabitantData> getAllInhabitantData() {
		return inhabitantData;
	}

	/**
	 * Returns the number of each type of fighter
	 * @return an array of the number of each type of fighter
	 */
	public int[] getFighterCount() {
		return new int[] {
			fighters.getSoldierCount(),
			fighters.getArcherCount(),
			fighters.getKnightCount(),
			fighters.getCatapultCount()
		};
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
		} else if (b instanceof VillageHall) {
			level++;
		}
	}

	/**
	 * Adds an inhabitant if the cost can be paid.
	 * @param constructor the inhabitant constructor
	 * @return an inhabitant if one was added, <code>null</code> otherwise
	 */
	public Inhabitant tryAddInhabitant(InhabitantConstructor constructor) {
		if (!inventory.checkCost(constructor.getProductionCost())) {
			return null;
		}

		inventory.payCost(constructor.getProductionCost());
		population++;
		return constructor.addInhabitant();
	}

	/**
	 * Completes adding an inhabitant to the village
	 * @param inhabitant the inhabitant to add
	 * @param type the type of inhabitant
	 */
	public synchronized void completeAddInhabitant(Inhabitant inhabitant, InhabitantType type) {
		switch (type) {
			case WORKER:
				workers.addPeasant((Worker) inhabitant);
				break;
			case LUMBERMAN:
				lumbermen.addPeasant((Lumberman) inhabitant);
				break;
			case IRON_MINER:
				ironMiners.addPeasant((IronMiner) inhabitant);
				break;
			case GOLD_MINER:
				goldMiners.addPeasant((GoldMiner) inhabitant);
				break;
			case SOLDIER:
				fighters.addSoldier((Soldier) inhabitant);
				break;
			case ARCHER:
				fighters.addArcher((Archer) inhabitant);
				break;
			case KNIGHT:
				fighters.addKnight((Knight) inhabitant);
				break;
			case CATAPULT:
				fighters.addCatapult((Catapult) inhabitant);
				break;
		}
	}

	/**
	 * Begins the upgrade inhabitant process, if the upgrade cost can be paid
	 * @param constructor the inhabitant constructor
	 * @return <code>true</code> if the inhabitant can be added, <code>false</code> if not
	 */
	public boolean tryUpgradeInhabitant(InhabitantConstructor constructor) {
		if (constructor.isUpgrading() || !inventory.checkCost(constructor.getUpgradeCost())) {
			return false;
		}

		inventory.payCost(constructor.getUpgradeCost());
		constructor.setUpgrading(true);
		return true;
	}

	/**
	 * Completes the upgrade inhabitant process
	 * @param constructor the inhabitant constructor
	 */
	public void completeUpgradeInhabitant(InhabitantConstructor constructor) {
		constructor.upgrade();
		constructor.setUpgrading(false);
	}

	/**
	 * Generates village for attacking.
	 * @return a ChallengeEntitySet representing the village
	 */
	public ChallengeEntitySet<Double, Double> generateVillage() {
		Random rand = new Random();
		Building[][] buildings = new Building[7][];
		buildings[0] = new Building[1];
		buildings[1] = new Building[rand.nextInt(3) - 2 + buildingData.get(BuildingType.FARM).getMaxCount()];
		buildings[2] = new Building[rand.nextInt(3) - 2 + buildingData.get(BuildingType.LUMBER_MILL).getMaxCount()];
		buildings[3] = new Building[rand.nextInt(3) - 2 + buildingData.get(BuildingType.IRON_MINE).getMaxCount()];
		buildings[4] = new Building[rand.nextInt(3) - 2 + buildingData.get(BuildingType.GOLD_MINE).getMaxCount()];
		buildings[5] = new Building[rand.nextInt(3) - 2 + buildingData.get(BuildingType.ARCHER_TOWER).getMaxCount()];
		buildings[6] = new Building[rand.nextInt(3) - 2 + buildingData.get(BuildingType.CANNON).getMaxCount()];

		buildings[0][0] = new VillageHall(level);

		for (int i = 0; i < buildings[1].length; i++) {
			buildings[1][i] = new Farm(level);
		}
		for (int i = 0; i < buildings[2].length; i++) {
			buildings[2][i] = new LumberMill(level);
		}
		for (int i = 0; i < buildings[3].length; i++) {
			buildings[3][i] = new IronMine(level);
		}
		for (int i = 0; i < buildings[4].length; i++) {
			buildings[4][i] = new GoldMine(level);
		}
		for (int i = 0; i < buildings[5].length; i++) {
			buildings[5][i] = new ArcherTower(level);
		}
		for (int i = 0; i < buildings[6].length; i++) {
			buildings[6][i] = new Cannon(level);
		}

		Inventory generatedInventory = new Inventory((level + 1) * 100, (level + 1) * 100,
			(level + 1) * 100);
		return new DefenseChallengeEntitySetAdapter(buildings, generatedInventory);
	}

	/**
	 * Returns a ChallengeEntitySet representing the attack force
	 * @param attackForce the counts of the fighters
	 * @return a ChallengeEntitySet representing the attack force
	 */
	public ChallengeEntitySet<Double, Double> createAttackForce(int[] attackForce) {
		return new AttackChallengeEntitySetAdapter(attackForce, inhabitantData, inventory);
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
	 * Updates the number of attack wins or fails.
	 * @param result <code>true</code> if attack was successful, <code>false</code> if not
	 */
	public void recordAttack(boolean result) {
		if (result) {
			attackWins++;
		} else {
			attackFails++;
		}
	}

	/**
	 * Returns the rank of the village.
	 * @return a string indicating the rank of the village
	 */
	public String getRank() {
		switch (level) {
			case 4:
				if (attackRatio() > 0.6) {
					return "PLATINUM";
				}

				return "GOLD";
			case 3:
				if (attackRatio() > 0.6) {
					return "GOLD";
				}

				return "SILVER";
			case 2:
				if (attackRatio() > 0.6) {
					return "SILVER";
				}

				return "BRONZE";
			case 1:
				if (attackRatio() > 0.6) {
					return "BRONZE";
				}

				return "IRON";
 			default:
				return "IRON";
		}
	}

	/**
	 * Returns the ratio of successful attacks to total attacks
	 * @return the attack ratio
	 */
	private double attackRatio() {
		return (double) attackWins / (attackWins + attackFails);
	}
}
