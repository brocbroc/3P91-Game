package game;

import ChallengeDecision.*;
import gameElements.BuildingType;
import gameElements.InhabitantType;
import gameElements.building.*;
import gameElements.inhabitant.*;
import gui.View;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.HashMap;
import utility.*;

/**
 * This class represents the game engine. It is the controller of the MVC pattern. It processes
 * input from the view and calls the appropriate model methods.
 */
public class GameEngine {
	private View view;
	private HashMap<Integer, Player> players;
	private HashMap<Integer, Village> villages;
	private ScheduledExecutorService scheduler;
	private Village base;

	/**
	 * Class constructor.
	 */
	public GameEngine() {
		players = new HashMap<>();
		villages = new HashMap<>();
		scheduler = new ScheduledThreadPoolExecutor(5);
	}

	/**
	 * Adds a new player to players.
	 * If there is not a village in villages corresponding to the player id, create a new
	 * <code>Village</code> object for the player.
	 * @param id the player id
	 */
	public void addPlayer(int id) {
		if (villages.containsKey(id)) {
			players.put(id, new Player(id, villages.get(id)));
		} else {
			Village v = new Village();
			villages.put(id, v);
			players.put(id, new Player(id, v));
		}
	}

	/**
	 * Sets the active player.
	 * This method sets base to the village of the player with id, creates a view for the active
	 * player, and starts the view thread.
	 * @param id the player id
	 */
	public void setActivePlayer(int id) {
		if (players.containsKey(id)) {
			Player p = players.get(id);
			base = p.getVillage();

			if (view == null) {
				view = new View(this, p);
				view.startGameThread();
			} else {
				view.switchPlayer(p);
			}
		} else {
			System.out.println("Invalid player id");
		}
	}

	/**
	 * Executes the input command.
	 * @param input the command to execute
	 * @throws IOException if called methods throw an exception
	 */
	public void update(String input) throws IOException {
		switch (input) {
			case "add building":
				this.addBuilding();
				return;
			case "upgrade building":
				this.upgradeBuilding();
				return;
			case "add inhabitant":
				this.addInhabitant();
				return;
			case "upgrade inhabitant":
				this.upgradeInhabitant();
				return;
			case "attack":
				this.generateVillage();
				return;
			case "check rank":
				this.checkRank();
				return;
			case "switch player":
				this.switchPlayer();
				return;
			case "exit":
				this.exit();
				return;
			default:
				view.printMessage("Invalid command.");
		}
	}

	/**
	 * Adds a new building to base, if requirements are met.
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void addBuilding() throws IOException {
		BuildingConstructor constructor;

		switch (view.prompt("Building type: ")) {
			case "village hall":
				constructor = new VillageHallConstructor(base.getAllBuildingData(), base.getAllInhabitantData());
				break;
			case "farm":
				constructor = new FarmConstructor((FarmData) base.getBuildingData(BuildingType.FARM));
				break;
			case "lumber mill":
				constructor = new LumberMillConstructor((LumberMillData) base.getBuildingData(BuildingType.LUMBER_MILL));
				break;
			case "iron mine":
				constructor = new IronMineConstructor((IronMineData) base.getBuildingData(BuildingType.IRON_MINE));
				break;
			case "gold mine":
				constructor = new GoldMineConstructor((GoldMineData) base.getBuildingData(BuildingType.GOLD_MINE));
				break;
			case "archer tower":
				constructor = new ArcherTowerConstructor((ArcherTowerData) base.getBuildingData(BuildingType.ARCHER_TOWER));
				break;
			case "cannon":
				constructor = new CannonConstructor((CannonData) base.getBuildingData(BuildingType.CANNON));
				break;
			default:
				view.printMessage("Invalid building type.");
				return;
		}

		Position pos = parsePosition(view.prompt("Position [x y]: "));

		if (pos == null || base.isSquareFull(pos)) {
			view.printMessage("Invalid building position.");
			return;
		}

		// User input verified. Begin checking if operation is possible

		if (base.isSquareFull(pos)) {
			System.out.println("Invalid building position.");
			return;
		}

		if (constructor.getCount() == constructor.getMaxCount()) {
			System.out.println("Maximum number of buildings of this type already constructed.");
			return;
		}

		Worker builder = base.tryAddBuilding(constructor, pos);

		if (builder == null) {
			System.out.println("Building requirements not met.");
			return;
		}

		int buildTime = (int) (constructor.getBuildTime() * base.getWorkRate());
		System.out.println("Under construction...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeAddBuilding(builder, pos);
		}, buildTime, TimeUnit.SECONDS);
	}

	/**
	 * Upgrades the building at the desired location, if requirements are met.
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void upgradeBuilding() throws IOException {
		Position pos = parsePosition(view.prompt("Position [x y]: "));

		if (pos == null) {
			view.printMessage("Invalid building position.");
			return;
		}

		// User input verified. Begin checking if operation is possible

		Building b = base.getBuilding(pos);

		if (b == null) {
			System.out.println("No building to upgrade.");
			return;
		}

		if (b.getLevel() == b.getMaxLevel()) {
			System.out.println("Building is fully upgraded.");
			return;
		}

		Worker builder = base.tryUpgradeBuilding(b);

		if (builder == null) {
			System.out.println("Upgrade requirements not met.");
			return;
		}

		int buildTime = (int) (b.getUpgradeTime() * base.getWorkRate());
		System.out.println("Upgrading building...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeUpgradeBuilding(b, builder);
		}, buildTime, TimeUnit.SECONDS);
	}

	/**
	 * Adds an inhabitant of the desired type, if requirements are met.
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void addInhabitant() throws IOException {
		InhabitantConstructor constructor;
		InhabitantType type;

		switch (view.prompt("Inhabitant type: ")) {
			case "worker":
				type = InhabitantType.WORKER;
				constructor = new WorkerConstructor((WorkerData) base.getInhabitantData(type));
				break;
			case "lumberman":
				type = InhabitantType.LUMBERMAN;
				constructor = new LumbermanConstructor((LumbermanData) base.getInhabitantData(type));
				break;
			case "iron miner":
				type = InhabitantType.IRON_MINER;
				constructor = new IronMinerConstructor((IronMinerData) base.getInhabitantData(type));
				break;
			case "gold miner":
				type = InhabitantType.GOLD_MINER;
				constructor = new GoldMinerConstructor((GoldMinerData) base.getInhabitantData(type));
				break;
			case "soldier":
				type = InhabitantType.SOLDIER;
				constructor = new SoldierConstructor((SoldierData) base.getInhabitantData(type));
				break;
			case "archer":
				type = InhabitantType.ARCHER;
				constructor = new ArcherConstructor((ArcherData) base.getInhabitantData(type));
				break;
			case "knight":
				type = InhabitantType.KNIGHT;
				constructor = new KnightConstructor((KnightData) base.getInhabitantData(type));
				break;
			case "catapult":
				type = InhabitantType.CATAPULT;
				constructor = new CatapultConstructor((CatapultData) base.getInhabitantData(type));
				break;
			default:
				System.out.println("Invalid inhabitant type.");
				return;
		}

		// User input verified. Checking if operation is possible

		if (base.isVillageFull()) {
			System.out.println("Village is full. No new inhabitants can be added.");
			return;
		}

		Inhabitant inhabitant = base.tryAddInhabitant(constructor);

		if (inhabitant == null) {
			System.out.println("Inhabitant requirements not met.");
			return;
		}

		System.out.println("Producing inhabitant...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeAddInhabitant(inhabitant, type);
		}, constructor.getProductionTime(), TimeUnit.SECONDS);
	}

	/**
	 * Upgrades the desired type of inhabitants, if requirements are met.
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void upgradeInhabitant() throws IOException {
		InhabitantConstructor constructor;
		InhabitantType type;

		switch (view.prompt("Inhabitant type: ")) {
			case "worker":
				type = InhabitantType.WORKER;
				constructor = new WorkerConstructor((WorkerData) base.getInhabitantData(type));
				break;
			case "lumberman":
				type = InhabitantType.LUMBERMAN;
				constructor = new LumbermanConstructor((LumbermanData) base.getInhabitantData(type));
				break;
			case "iron miner":
				type = InhabitantType.IRON_MINER;
				constructor = new IronMinerConstructor((IronMinerData) base.getInhabitantData(type));
				break;
			case "gold miner":
				type = InhabitantType.GOLD_MINER;
				constructor = new GoldMinerConstructor((GoldMinerData) base.getInhabitantData(type));
				break;
			case "soldier":
				type = InhabitantType.SOLDIER;
				constructor = new SoldierConstructor((SoldierData) base.getInhabitantData(type));
				break;
			case "archer":
				type = InhabitantType.ARCHER;
				constructor = new ArcherConstructor((ArcherData) base.getInhabitantData(type));
				break;
			case "knight":
				type = InhabitantType.KNIGHT;
				constructor = new KnightConstructor((KnightData) base.getInhabitantData(type));
				break;
			case "catapult":
				type = InhabitantType.CATAPULT;
				constructor = new CatapultConstructor((CatapultData) base.getInhabitantData(type));
				break;
			default:
				System.out.println("Invalid inhabitant type.");
				return;
		}

		if (constructor.getLevel() == constructor.getMaxLevel()) {
			System.out.println("Maximum upgrade level reached");
			return;
		}

		if (!base.tryUpgradeInhabitant(constructor)) {
			System.out.println("Upgrade requirements not met.");
			return;
		}

		System.out.println("Upgrading inhabitant...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeUpgradeInhabitant(constructor);
		}, constructor.getUpgradeTime(), TimeUnit.SECONDS);

	}

	/**
	 * Creates new villages until the player selects one, then starts attack
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void generateVillage() throws IOException {
		boolean generate = true;

		do {
			ChallengeEntitySet<Double, Double> challengee = base.generateVillage();
			List<ChallengeDefense<Double, Double>> entityDefenseList = challengee.getEntityDefenseList();
			double hitPoints = 0;
			double damage = 0;

			for (ChallengeDefense<Double, Double> defense : entityDefenseList) {
				hitPoints += defense.getHitPoints();
				damage += defense.getProperty();
			}

			view.printMessage("Village hit points: " + hitPoints);
			view.printMessage("Village damage: " + damage);
			String input = view.prompt("Accept or pass (anything else to stop): ");

			if (input.equals("accept")) {
				view.printMessage("Target found. Preparing attack force...");
				attack(challengee);
				generate = false;
			} else if (!input.equals("pass")) {
				view.printMessage("Village generation ended.");
				generate = false;
			}
		} while (generate);
	}

	/**
	 * Attacks the target village
	 * @param challengee the target village
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void attack(ChallengeEntitySet<Double, Double> challengee) throws IOException {
		int[] fighterCounts = base.getFighterCount();
		int[] attackForce = new int[4];

		try {
			String input = view.prompt("Number of soldiers (max " + fighterCounts[0] + "): ");
			attackForce[0] = Integer.parseInt(input);

			if (attackForce[0] < 0 || attackForce[0] > fighterCounts[0]) {
				view.printMessage("Invalid number of soldiers.");
				return;
			}

			input = view.prompt("Number of archers (max " + fighterCounts[1] + "): ");
			attackForce[1] = Integer.parseInt(input);

			if (attackForce[1] < 0 || attackForce[1] > fighterCounts[1]) {
				view.printMessage("Invalid number of archers.");
				return;
			}

			input = view.prompt("Number of knights (max " + fighterCounts[2] + "): ");
			attackForce[2] = Integer.parseInt(input);

			if (attackForce[2] < 0 || attackForce[2] > fighterCounts[2]) {
				view.printMessage("Invalid number of knights.");
				return;
			}

			input = view.prompt("Number of catapults (max " + fighterCounts[3] + "): ");
			attackForce[3] = Integer.parseInt(input);

			if (attackForce[3] < 0 || attackForce[3] > fighterCounts[3]) {
				view.printMessage("Invalid number of catapults.");
				return;
			}

			ChallengeEntitySet<Double, Double> challenger = base.createAttackForce(attackForce);
			ChallengeResult result = Arbitrer.challengeDecide(challenger, challengee);

			if (result.getChallengeWon()) {
				view.printMessage("Attack success.");
				base.recordAttack(true);
				List<ChallengeResource<Double,Double>> lootList = result.getLoot();
				Cost loot = new Cost(lootList.get(0).getProperty().intValue(), lootList.get(1).getProperty().intValue(), lootList.get(2).getProperty().intValue());
				base.addLoot(loot);
			} else {
				base.recordAttack(false);
				view.printMessage("Attack failed.");
			}
		} catch (NumberFormatException e) {
			view.printMessage("Invalid input.");
		}
	}

	/**
	 * Returns the rank of the active player
	 */
	public void checkRank() {
		view.printMessage("Rank of village: " + base.getRank());
	}

	/**
	 * Switches the active player
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void switchPlayer() throws IOException {
		// SOMEHOW BROKEN?? DOESN'T READ INPUT PROPERLY.
		// It's trying to get input from view run loop another time before switching to new view
		try {
			int id = Integer.parseInt(view.prompt("New player ID: "));
			setActivePlayer(id);
		} catch (NumberFormatException e) {
			System.out.println("Invalid player id.");
		}
	}

	/**
	 * Exits the game.
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void exit() throws IOException {
		System.out.println("Game exited.");
		scheduler.shutdown();
		view.closeGameThread();
	}

	/**
	 * Creates a <code>Position</code> object from <code>input</code>.
	 * @param input the input string of the form "[int] [int]"
	 * @return the <code>Position</code> object, or <code>null</code> if <code>input</code> was invalid
	 */
	private Position parsePosition(String input) {
		String[] coordinates = input.split(" ");

		try {
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			return new Position(x, y);
		} catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Begins a new game.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Start");
		GameEngine game = new GameEngine();
		game.addPlayer(0);
		game.addPlayer(1);
		game.setActivePlayer(0);
	}
}
