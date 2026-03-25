package game;

import ChallengeDecision.*;
import gameElements.BuildingType;
import gameElements.InhabitantType;
import gameElements.building.*;
import gameElements.inhabitant.*;
import gui.GraphicalInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;
import java.util.HashMap;
import utility.*;

/**
 * This class represents the game engine.
 * It adds players, runs the main game thread, and handles I/O.
 */
public class GameEngine implements Runnable {
	private GraphicalInterface gui;
	private HashMap<Integer, Player> players;
	private HashMap<Integer, Village> villages;
	private Thread gameThread;
	private BufferedReader in;
	private ScheduledExecutorService scheduler;
	private volatile boolean running;
	private volatile boolean redraw; // Implement later
	private Village base;

	/**
	 * Class constructor.
	 */
	public GameEngine() {
		players = new HashMap<>();
		villages = new HashMap<>();
		in = new BufferedReader(new InputStreamReader(System.in));
		scheduler = new ScheduledThreadPoolExecutor(5);
		running = false;
		redraw = false;
	}

	/**
	 * Adds a new player to <code>players</code>.
	 * If there is not a village in <code>villages</code> corresponding to <code>id</code>,
	 * create a new <code>Village</code> object for the player.
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
	 * This method sets <code>base</code> to the village of the player with <code>id</code>
	 * @param id the player id
	 */
	public void setActivePlayer(int id) {
		if (players.containsKey(id)) {
			base = players.get(id).getVillage();
		} else {
			System.out.println("Invalid player id");
		}
	}

	/**
	 * Starts the thread <code>gameThread</code>
	 */
	public void startGameThread() {
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * Runs <code>gameThread</code>.
	 * This method takes commands from the console and then executes the commands, through
	 * <code>update()</code>
	 */
	@Override
	public void run() {
		draw();

		while (running) {
			if (redraw) { // DOESN'T REDRAW UNTIL NEW INPUT
				redraw = false;
				draw();
			}

			try {
				String input = in.readLine().toLowerCase();
				update(input);
			} catch (IOException e) {
				System.out.println("An IO exception occurred.");
				e.printStackTrace();
			}
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
				System.out.println("Invalid command.");
		}
	}

	/**
	 * Draws <code>base</code>.
	 */
	public void draw() {
		final Village BASE = base;

		synchronized (BASE) {
			System.out.println();
			int[] inventory = base.getInventoryValues();
			System.out.printf("Inventory: Gold - %d, Iron - %d, Lumber - %d", inventory[0],
				inventory[1], inventory[2]);
			System.out.println();
			Building[][] map = base.getMap();

			for (Building[] buildings : map) {
				for (Building building : buildings) {
					if (building == null) {
						System.out.print(".");
					} else if (building.isUnderConstruction()) {
						System.out.print("#");
					} else {
						System.out.print(building.draw());
					}

					System.out.print(" ");
				}

				System.out.println();
			}

			for (int i = 0; i < map[0].length; i++) {
				System.out.print("——");
			}

			System.out.println();
		}
	}

	/**
	 * Adds a new building to <code>base</code>, if requirements are met.
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void addBuilding() throws IOException {
		System.out.print("Building type: ");
		BuildingConstructor constructor;

		switch (in.readLine().toLowerCase()) {
			case "village hall":
				constructor = new VillageHallConstructor(base.getAllBuildingData());
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
				System.out.println("Invalid building type.");
				return;
		}

		System.out.print("Position [x y]: ");
		String posInput = in.readLine();
		Position pos = parsePosition(posInput);

		if (pos == null || base.isSquareFull(pos)) {
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

			if (BASE == base) {
				System.out.println("Building completed.");
				redraw = true;
			}
		}, buildTime, TimeUnit.SECONDS);
	}

	/**
	 * Upgrades the building at the desired location, if requirements are met.
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void upgradeBuilding() throws IOException {
		System.out.print("Position [x y]: ");
		String posInput = in.readLine();
		Position pos = parsePosition(posInput);

		if (pos == null) {
			System.out.println("Invalid building position.");
			return;
		}

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

			if (BASE == base) {
				System.out.println("Upgrade completed.");
				redraw = true;
			}
		}, buildTime, TimeUnit.SECONDS);
	}

	/**
	 * Adds an inhabitant of the desired type, if requirements are met.
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void addInhabitant() throws IOException {
		if (base.isVillageFull()) {
			System.out.println("Village is full. No new inhabitants can be added.");
			return;
		}

		System.out.print("Inhabitant type: ");
		InhabitantConstructor constructor;
		InhabitantType type;

		switch (in.readLine().toLowerCase()) {
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

		Inhabitant inhabitant = base.tryAddInhabitant(constructor);

		if (inhabitant == null) {
			System.out.println("Inhabitant requirements not met.");
		}

		System.out.println("Producing inhabitant...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeAddInhabitant(inhabitant, type);
			System.out.println("Inhabitant added.");
		}, constructor.getProductionTime(), TimeUnit.SECONDS);
	}

	/**
	 * Upgrades the desired type of inhabitants, if requirements are met.
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void upgradeInhabitant() throws IOException {
		System.out.print("Inhabitant type: ");
		InhabitantConstructor constructor;
		InhabitantType type;

		switch (in.readLine().toLowerCase()) {
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

		if (!base.tryUpgradeInhabitant(constructor)) {
			System.out.println("Upgrade requirements not met.");
			return;
		}

		System.out.println("Upgrading inhabitant...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeUpgradeInhabitant(constructor);
			System.out.println("Inhabitant upgraded.");
		}, constructor.getUpgradeTime(), TimeUnit.SECONDS);

	}

	/**
	 * Creates new villages until the player selects one, then starts attack
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void generateVillage() throws IOException {
		// UPDATE TO USE CHALLENGE DECISION
		boolean generate = true;

		do {
			int defenseScore = base.generateVillage();
			System.out.println("Village defense score: " + defenseScore);
			System.out.print("Accept or pass (anything else to stop): ");
			String input = in.readLine().toLowerCase();

			if (input.equals("accept")) {
				System.out.println("Target found. Attack begins.");
				attack(defenseScore);
				generate = false;
			} else if (!input.equals("pass")) {
				System.out.println("Village generation ended.");
				generate = false;
			}
		} while (generate);
	}

	/**
	 * Attacks the target village
	 * @param defenseScore the target village defense score
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void attack(int defenseScore) throws IOException {
		int[] fighterCounts = base.getFighterCount();

		try {
			System.out.print("Number of soldiers (max " + fighterCounts[0] + "): ");
			String input = in.readLine();
			int soldiers = Integer.parseInt(input);

			if (soldiers < 0 || soldiers > fighterCounts[0]) {
				System.out.println("Invalid number of soldiers.");
				return;
			}

			System.out.print("Number of archers (max " + fighterCounts[1] + "): ");
			input = in.readLine();
			int archers = Integer.parseInt(input);

			if (archers < 0 || archers > fighterCounts[1]) {
				System.out.println("Invalid number of archers.");
				return;
			}

			System.out.print("Number of knights (max " + fighterCounts[2] + "): ");
			input = in.readLine();
			int knights = Integer.parseInt(input);

			if (knights < 0 || knights > fighterCounts[2]) {
				System.out.println("Invalid number of knights.");
				return;
			}

			System.out.print("Number of catapults (max " + fighterCounts[3] + "): ");
			input = in.readLine();
			int catapults = Integer.parseInt(input);

			if (catapults < 0 || catapults > fighterCounts[3]) {
				System.out.println("Invalid number of catapults.");
				return;
			}

			int attackScore = 0;
			//int attackScore = soldiers * Soldier.getDamage() + archers * Archer.getDamage() + knights * Knight.getDamage() + catapults * Catapult.getDamage();

			if (defenseScore <= attackScore) {
				System.out.println("Attack success.");
				Cost loot = new Cost(defenseScore / 50, defenseScore / 50, defenseScore / 50);
				base.addLoot(loot);
				base.recordAttack(true);
			} else {
				System.out.println("Attack failed.");
				base.recordAttack(false);
			}

			redraw = true;
		} catch (NumberFormatException e) {
			System.out.println("Invalid input.");
		}
	}

	/**
	 * Returns the rank of the active player
	 */
	public void checkRank() {
		System.out.println("Rank of village: " + base.getRank());
	}

	/**
	 * Switches the active player
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void switchPlayer() throws IOException {
		System.out.print("New player ID: ");

		try {
			int id = Integer.parseInt(in.readLine());
			setActivePlayer(id);
			redraw = true;
		} catch (NumberFormatException e) {
			System.out.println("Invalid player id.");
		}
	}

	/**
	 * Exits the game.
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void exit() throws IOException {
		System.out.println("Game exited.");
		running = false;
		in.close();
		scheduler.shutdown();
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
		game.startGameThread();
	}
}
