package server;

import ChallengeDecision.*;
import client.VillageSnapshot;
import gameElements.BuildingType;
import gameElements.InhabitantType;
import gameElements.building.*;
import gameElements.inhabitant.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import utility.*;

/**
 * This class represents the game engine. It is the controller of the MVC pattern. It processes
 * input from the view and calls the appropriate model methods. It also functions as a handler for
 * GameClient.
 */
public class GameEngine implements Runnable, Observer {
	private GameServer server;
	private ScheduledExecutorService scheduler;
	private HashMap<String, Player> players;
	private Player player;
	private Village base;
	private Socket clientSocket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private boolean running;
	private boolean terminate;
	private ChallengeEntitySet<Double, Double> targetVillage;
	private ExecutorService threadPool;
	private final int[] TESTING_RATIO = new int[2];
	private CountDownLatch latch;

	/**
	 * Class constructor.
	 * @param clientSocket the client to handle
	 * @param players the valid players
	 */
	public GameEngine(GameServer server, Socket clientSocket, HashMap<String, Player> players) {
		this.server = server;
		this.clientSocket = clientSocket;
		scheduler = new ScheduledThreadPoolExecutor(10);
		this.players = players;
		running = false;
		terminate = false;
		threadPool = Executors.newFixedThreadPool(10);
	}

	/**
	 * Main game loop. Connects to client socket, verifies client, and handles client input.
	 */
	public void run() {
		try {
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Client at " + clientSocket.getInetAddress() + " has connected.");

			// Verify client; handshake protocol
			Packet key = (Packet) inputStream.readObject();

			if (key.getHeader() != Protocol.KEY || !players.containsKey(key.getMessage())) {
				System.out.println("Client is not a valid player.");
				key = new Packet(Protocol.KEY, "invalid");
				outputStream.writeObject(key);
				outputStream.flush();
				close();
				return;
			}

			player = players.get(key.getMessage());
			base = player.getVillage();
			base.addObserver(this);
			key = new Packet(Protocol.KEY, "valid");
			outputStream.writeObject(key);
			outputStream.flush();

			// Begin game
			running = true;
			update("Start. Enter \"menu\" for options");

			while (running) {
				Packet input = (Packet) inputStream.readObject();

				switch (input.getHeader()) {
					case ADD_BUILDING:
						addBuilding(input.getMessage());
						break;
					case UPGRADE_BUILDING:
						upgradeBuilding(input.getMessage());
						break;
					case TRAIN_INHABITANT:
						trainInhabitant(input.getMessage());
						break;
					case UPGRADE_INHABITANT:
						upgradeInhabitant(input.getMessage());
						break;
					case GENERATE_VILLAGE:
						generateVillage();
						break;
					case ATTACK:
						attack(input.getMessage());
						break;
					case ATTACK_TESTING:
						attackTesting();
						break;
					case VILLAGE_TESTING:
						villageTesting();
						break;
					case CHECK_RANK:
						checkRank();
						break;
					case CLOSE_GAME:
						running = false;
						break;
					case EXIT_PROGRAM:
						running = false;
						terminate = true;
						break;
				}
			}

			close();

			if (terminate) {
				server.stop();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Closes the client handler
	 * @throws IOException if exception occurs on closing socket and I/O streams
	 */
	public void close() throws IOException {
		base.removeObserver(this);
		inputStream.close();
		outputStream.close();
		clientSocket.close();
		System.out.println("Client at " + clientSocket.getInetAddress() + " has disconnected.");
	}

	/**
	 * Sends the message and a snapshot of the village to the client when the village updates
	 * @param message a string describing the changes to the observable subject
	 */
	@Override
	public void update(String message) {
		try {
			Packet update = new Packet(Protocol.UPDATE, message, createVillageSnapshot());
			outputStream.writeObject(update);
			outputStream.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a snapshot of the village
	 * @return a snapshot of the village
	 */
	public VillageSnapshot createVillageSnapshot() {
		Building[][] buildings = base.getMap();
		String[][] map = new String[buildings.length][];

		for (int i = 0; i < buildings.length; i++) {
			map[i] = new String[buildings[i].length];

			for (int j = 0; j < buildings[i].length; j++) {
				if (buildings[i][j] == null) {
					map[i][j] = "..";
				} else if (buildings[i][j].isUnderConstruction()) {
					map[i][j] = "##";
				} else {
					map[i][j] = buildings[i][j].draw();
				}
			}
		}

		return new VillageSnapshot(map, base.getInventoryValues(), base.getInhabitantLevels(), base.getInhabitantCounts());
	}

	/**
	 * Sends an alert to the client
	 * @param message the alert message
	 * @throws IOException if outputStream fails
	 */
	public void alert(String message) throws IOException {
		Packet alert = new Packet(Protocol.ALERT, message);
		outputStream.writeObject(alert);
		outputStream.flush();
	}

	/**
	 * Sends a prompt to the client.
	 * @param message the prompt message
	 * @throws IOException if outputStream fails
	 */
	public void prompt(String message) throws IOException {
		Packet alert = new Packet(Protocol.PROMPT, message);
		outputStream.writeObject(alert);
		outputStream.flush();
	}

	/**
	 * Adds a new building to base, if requirements are met.
	 * @throws IOException if <code>alert()</code> fails
	 */
	public void addBuilding(String input) throws IOException {
		String[] arguments = input.split("\n");

		if (arguments.length != 2) {
			alert("Invalid input.");
			return;
		}

		BuildingConstructor constructor;

		switch (arguments[0]) {
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
				alert("Invalid building type.");
				return;
		}

		Position pos = parsePosition(arguments[1]);

		if (pos == null) {
			alert("Invalid building position.");
			return;
		}

		// User input verified. Begin checking if operation is possible
		Worker builder = base.tryAddBuilding(constructor, pos);

		if (builder == null) {
			alert("Building construction requirements not met.");
			return;
		}

		alert("Building under construction...");
		int buildTime = (int) (constructor.getBuildTime() * base.getWorkRate());
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeAddBuilding(builder, pos);
		}, buildTime, TimeUnit.SECONDS);
	}

	/**
	 * Upgrades the building at the desired location, if requirements are met.
	 * @throws IOException if <code>alert()</code> fails
	 */
	public void upgradeBuilding(String input) throws IOException {
		Position pos = parsePosition(input);

		if (pos == null) {
			alert("Invalid building position.");
			return;
		}

		// User input verified. Begin checking if operation is possible
		Worker builder = base.tryUpgradeBuilding(pos);

		if (builder == null) {
			alert("Building upgrade requirements not met.");
			return;
		}

		alert("Upgrading building...");
		int buildTime = (int) (base.getBuilding(pos).getUpgradeTime() * base.getWorkRate());
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeUpgradeBuilding(pos, builder);
		}, buildTime, TimeUnit.SECONDS);
	}

	/**
	 * Adds an inhabitant of the desired type, if requirements are met.
	 * @throws IOException if <code>alert()</code> fails
	 */
	public void trainInhabitant(String input) throws IOException {
		InhabitantType type;
		InhabitantConstructor constructor;

		switch (input) {
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
				alert("Invalid inhabitant type.");
				return;
		}

		// User input verified. Checking if operation is possible
		Inhabitant inhabitant = base.tryTrainInhabitant(constructor);

		if (inhabitant == null) {
			alert("Inhabitant training requirements not met.");
			return;
		}

		alert("Training inhabitant...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeTrainInhabitant(inhabitant, type);
		}, constructor.getProductionTime(), TimeUnit.SECONDS);
	}

	/**
	 * Upgrades the desired type of inhabitants, if requirements are met.
	 * @throws IOException if <code>alert()</code> fails
	 */
	public void upgradeInhabitant(String input) throws IOException {
		InhabitantConstructor constructor;

		switch (input) {
			case "worker":
				constructor = new WorkerConstructor((WorkerData) base.getInhabitantData(InhabitantType.WORKER));
				break;
			case "lumberman":
				constructor = new LumbermanConstructor((LumbermanData) base.getInhabitantData(InhabitantType.LUMBERMAN));
				break;
			case "iron miner":
				constructor = new IronMinerConstructor((IronMinerData) base.getInhabitantData(InhabitantType.IRON_MINER));
				break;
			case "gold miner":
				constructor = new GoldMinerConstructor((GoldMinerData) base.getInhabitantData(InhabitantType.GOLD_MINER));
				break;
			case "soldier":
				constructor = new SoldierConstructor((SoldierData) base.getInhabitantData(InhabitantType.SOLDIER));
				break;
			case "archer":
				constructor = new ArcherConstructor((ArcherData) base.getInhabitantData(InhabitantType.ARCHER));
				break;
			case "knight":
				constructor = new KnightConstructor((KnightData) base.getInhabitantData(InhabitantType.KNIGHT));
				break;
			case "catapult":
				constructor = new CatapultConstructor((CatapultData) base.getInhabitantData(InhabitantType.CATAPULT));
				break;
			default:
				alert("Invalid inhabitant type.");
				return;
		}

		// User input verified. Checking if operation is possible
		if (!base.tryUpgradeInhabitant(constructor)) {
			alert("Inhabitant upgrade requirements not met.");
			return;
		}

		alert("Upgrading inhabitant...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeUpgradeInhabitant(constructor);
		}, constructor.getUpgradeTime(), TimeUnit.SECONDS);

	}

	/**
	 * Generates a village to attack
	 * @throws IOException if <code>prompt()</code> fails
	 */
	public void generateVillage() throws IOException {
		targetVillage = base.generateVillage();
		List<ChallengeDefense<Double, Double>> entityDefenseList = targetVillage.getEntityDefenseList();
		double hitPoints = 0;
		double damage = 0;

		for (ChallengeDefense<Double, Double> defense : entityDefenseList) {
			hitPoints += defense.getHitPoints();
			damage += defense.getProperty();
		}

		prompt("Village hit points: " + hitPoints + "\nVillage damage: " + damage + "\n");
	}

	/**
	 * Attacks the target village
	 * @param input the attack force
	 * @throws IOException if <code>alert()</code> fails
	 */
	public void attack(String input) throws IOException {
		int[] fighterCounts = base.getFighterCount();
		int[] attackForce = new int[4];
		String[] counts = input.split("\n");

		System.out.println(input);

		try {
			attackForce[0] = Integer.parseInt(counts[0]);
			attackForce[1] = Integer.parseInt(counts[1]);
			attackForce[2] = Integer.parseInt(counts[2]);
			attackForce[3] = Integer.parseInt(counts[3]);

			if (attackForce[0] < 0 || attackForce[0] > fighterCounts[0]) {
				alert("Invalid number of soldiers.");
				return;
			}

			if (attackForce[1] < 0 || attackForce[1] > fighterCounts[1]) {
				alert("Invalid number of archers.");
				return;
			}

			if (attackForce[2] < 0 || attackForce[2] > fighterCounts[2]) {
				alert("Invalid number of knights.");
				return;
			}

			if (attackForce[3] < 0 || attackForce[3] > fighterCounts[3]) {
				alert("Invalid number of catapults.");
				return;
			}

			ChallengeEntitySet<Double, Double> challenger = base.createAttackForce(attackForce);
			ChallengeResult result = Arbitrer.challengeDecide(challenger, targetVillage);

			if (result.getChallengeWon()) {
				base.recordAttack(true);
				List<ChallengeResource<Double,Double>> lootList = result.getLoot();
				Cost loot = new Cost(lootList.get(0).getProperty().intValue(), lootList.get(1).getProperty().intValue(), lootList.get(2).getProperty().intValue());
				base.addLoot(loot);
			} else {
				base.recordAttack(false);
				alert("Attack failed.");
			}
		} catch (NumberFormatException e) {
			alert("Invalid input.");
		}
	}

	/**
	 * Conducts attack tests on generated villages
	 * @throws IOException if alert() fails
	 */
	public synchronized void attackTesting() throws IOException {
		try {
			TESTING_RATIO[0] = 0;
			TESTING_RATIO[1] = 0;
			latch = new CountDownLatch(10);
			ChallengeEntitySet<Double, Double> challenger = base.createAttackForce(base.getFighterCount());

			for (int i = 0; i < 10; i++) {
				threadPool.execute(new AttackTest(challenger));
			}

			latch.await();
			alert("Attack win/loss ratio: " + TESTING_RATIO[0] + " : " + TESTING_RATIO[1]);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Helper class for attack testing
	 */
	private class AttackTest implements Runnable {
		private ChallengeEntitySet<Double, Double> challenger;

		/**
		 * Class constructor.
		 * @param challenger the player's army
		 */
		public AttackTest(ChallengeEntitySet<Double, Double> challenger) {
			this.challenger = challenger;
		}

		/**
		 * Conducts one attack test
		 */
		public void run() {
			ChallengeEntitySet<Double, Double> challengee = base.generateVillage();
			ChallengeResult result = Arbitrer.challengeDecide(challenger, challengee);

			if (result.getChallengeWon()) {
				synchronized (TESTING_RATIO) {
					TESTING_RATIO[0]++;
				}
			} else {
				synchronized (TESTING_RATIO) {
					TESTING_RATIO[1]++;
				}
			}

			latch.countDown();
		}
	}

	/**
	 * Conducts defense tests on base
	 * @throws IOException if alert() fails
	 */
	public synchronized void villageTesting() throws IOException {
		try {
			TESTING_RATIO[0] = 0;
			TESTING_RATIO[1] = 0;
			latch = new CountDownLatch(10);
			ChallengeEntitySet<Double, Double> challengee = base.createDefenseForce();

			for (int i = 0; i < 10; i++) {
				threadPool.execute(new DefenseTest(challengee));
			}

			latch.await();
			alert("Defense win/loss ratio: " + TESTING_RATIO[0] + " : " + TESTING_RATIO[1]);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Helper class for defense testing
	 */
	private class DefenseTest implements Runnable {
		private ChallengeEntitySet<Double, Double> challengee;

		/**
		 * Class constructor.
		 * @param challengee the player's defenses
		 */
		public DefenseTest(ChallengeEntitySet<Double, Double> challengee) {
			this.challengee = challengee;
		}

		/**
		 * Conducts one defense test
		 */
		public void run() {
			ChallengeEntitySet<Double, Double> challenger = base.generateArmy();
			ChallengeResult result = Arbitrer.challengeDecide(challenger, challengee);

			if (result.getChallengeWon()) {
				synchronized (TESTING_RATIO) {
					TESTING_RATIO[1]++;
				}
			} else {
				synchronized (TESTING_RATIO) {
					TESTING_RATIO[0]++;
				}
			}

			latch.countDown();
		}
	}

	/**
	 * Returns the rank of the active player
	 * @throws IOException if <code>alert()</code> fails
	 */
	public void checkRank() throws IOException {
		alert("Rank of village: " + base.getRank());
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
}
