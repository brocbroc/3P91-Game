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
		scheduler.scheduleAtFixedRate(() -> {
			generateResources();
		}, 5, 5, TimeUnit.SECONDS);
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
			view.printMessage("Invalid player id");
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

	private void generateResources() {
		try {
			base.addLoot(new Cost(10, 10, 10));
			System.out.println("Resources generated!");
		} catch (Exception e) {
			e.printStackTrace();
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
			view.printMessage("Invalid building position.");
			return;
		}

		if (constructor.getCount() == constructor.getMaxCount()) {
			view.printMessage("Maximum number of buildings of this type already constructed.");
			return;
		}

		Worker builder = base.tryAddBuilding(constructor, pos);

		if (builder == null) {
			view.printMessage("Building requirements not met.");
			return;
		}

		int buildTime = (int) (constructor.getBuildTime() * base.getWorkRate());
		view.printMessage("Under construction...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeAddBuilding(builder, pos);
		}, buildTime, TimeUnit.SECONDS);
	}

	public void addBuildingNetwork() {
		try {
			BuildingConstructor constructor =
					new FarmConstructor((FarmData) base.getBuildingData(BuildingType.FARM));

			Position pos = null;
			for (int x = 0; x < 20; x++) {
				for (int y = 0; y < 20; y++) {
					Position p = new Position(x, y);
					if (!base.isSquareFull(p)) {
						pos = p;
						break;
					}
				}
				if (pos != null) break;
			}

			if (pos == null) return;

			Worker builder = base.tryAddBuilding(constructor, pos);

			if (builder == null) return;

			final Position finalPos = pos;

			scheduler.schedule(() -> {
				base.completeAddBuilding(builder, finalPos);
			}, constructor.getBuildTime(), TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
			view.printMessage("No building to upgrade.");
			return;
		}

		if (b.getLevel() == b.getMaxLevel()) {
			view.printMessage("Building is fully upgraded.");
			return;
		}

		Worker builder = base.tryUpgradeBuilding(b);

		if (builder == null) {
			view.printMessage("Upgrade requirements not met.");
			return;
		}

		int buildTime = (int) (b.getUpgradeTime() * base.getWorkRate());
		view.printMessage("Upgrading building...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeUpgradeBuilding(b, builder);
		}, buildTime, TimeUnit.SECONDS);
	}

	public void upgradeBuildingNetwork() {
		try {
			Building b = base.getAllBuildings().stream().findFirst().orElse(null);

			if (b == null) return;

			Worker w = base.tryUpgradeBuilding(b);

			if (w == null) return;

			scheduler.schedule(() -> {
				base.completeUpgradeBuilding(b, w);
			}, b.getUpgradeTime(), TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
				view.printMessage("Invalid inhabitant type.");
				return;
		}

		// User input verified. Checking if operation is possible

		if (base.isVillageFull()) {
			view.printMessage("Village is full. No new inhabitants can be added.");
			return;
		}

		Inhabitant inhabitant = base.tryAddInhabitant(constructor);

		if (inhabitant == null) {
			view.printMessage("Inhabitant requirements not met.");
			return;
		}

		view.printMessage("Producing inhabitant...");
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

		switch (view.prompt("Inhabitant type: ")) {
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
				view.printMessage("Invalid inhabitant type.");
				return;
		}

		if (constructor.getLevel() == constructor.getMaxLevel()) {
			view.printMessage("Maximum upgrade level reached");
			return;
		}

		if (!base.tryUpgradeInhabitant(constructor)) {
			view.printMessage("Upgrade requirements not met.");
			return;
		}

		view.printMessage("Upgrading inhabitant...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeUpgradeInhabitant(constructor);
		}, constructor.getUpgradeTime(), TimeUnit.SECONDS);

	}

    public void addSpecificInhabitant(InhabitantType type) {

        try {
            InhabitantConstructor constructor = null;

            switch (type) {
                case WORKER:
                    constructor = new WorkerConstructor(
                            (WorkerData) base.getInhabitantData(type));
                    break;
                case LUMBERMAN:
                    constructor = new LumbermanConstructor(
                            (LumbermanData) base.getInhabitantData(type));
                    break;
                case IRON_MINER:
                    constructor = new IronMinerConstructor(
                            (IronMinerData) base.getInhabitantData(type));
                    break;
                case GOLD_MINER:
                    constructor = new GoldMinerConstructor(
                            (GoldMinerData) base.getInhabitantData(type));
                    break;
                case SOLDIER:
                    constructor = new SoldierConstructor(
                            (SoldierData) base.getInhabitantData(type));
                    break;
                case ARCHER:
                    constructor = new ArcherConstructor(
                            (ArcherData) base.getInhabitantData(type));
                    break;
                case KNIGHT:
                    constructor = new KnightConstructor(
                            (KnightData) base.getInhabitantData(type));
                    break;
                case CATAPULT:
                    constructor = new CatapultConstructor(
                            (CatapultData) base.getInhabitantData(type));
                    break;
            }

            if (constructor == null || base.isVillageFull()) return;

            Inhabitant i = base.tryAddInhabitant(constructor);
            if (i == null) return;

            InhabitantType finalType = type;

            scheduler.schedule(() -> {
                base.completeAddInhabitant(i, finalType);
            }, constructor.getProductionTime(), TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public void addInhabitantNetwork() {
		try {
			InhabitantConstructor constructor;
			InhabitantType type;

			int choice = (int)(Math.random() * 4);

			switch (choice) {
				case 0:
					type = InhabitantType.SOLDIER;
					constructor = new SoldierConstructor(
							(SoldierData) base.getInhabitantData(type));
					break;
				case 1:
					type = InhabitantType.ARCHER;
					constructor = new ArcherConstructor(
							(ArcherData) base.getInhabitantData(type));
					break;
				case 2:
					type = InhabitantType.KNIGHT;
					constructor = new KnightConstructor(
							(KnightData) base.getInhabitantData(type));
					break;
				default:
					type = InhabitantType.CATAPULT;
					constructor = new CatapultConstructor(
							(CatapultData) base.getInhabitantData(type));
					break;
			}

			if (base.isVillageFull()) return;

			Inhabitant i = base.tryAddInhabitant(constructor);

			if (i == null) return;

			InhabitantType finalType = type;

			scheduler.schedule(() -> {
				base.completeAddInhabitant(i, finalType);
			}, constructor.getProductionTime(), TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public String generateVillageNetwork() {
		base.generateVillage();
		return "Village generated";
	}

	public String generateArmyNetwork() {
		return "Army generated (test)";
	}

	public String testVillageNetwork() {
		return "Village test complete";
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

	public String attackNetwork() {
		try {
			int[] fighterCounts = base.getFighterCount();

			if (fighterCounts[0] == 0 &&
					fighterCounts[1] == 0 &&
					fighterCounts[2] == 0 &&
					fighterCounts[3] == 0) {

				return "Attack FAILED (no army)";
			}

			boolean win = Math.random() > 0.5;

			if (win) {
				return "ATTACK SUCCESS | Loot gained";
			} else {
				return "ATTACK FAILED";
			}

		} catch (Exception e) {
			return "Attack error";
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
		try {
			int id = Integer.parseInt(view.prompt("New player ID: "));
			setActivePlayer(id);
		} catch (NumberFormatException e) {
			view.printMessage("Invalid player id.");
		}
	}

	/**
	 * Exits the game.
	 * @throws IOException if <code>view.prompt()</code> fails
	 */
	public void exit() throws IOException {
		view.printMessage("Game exited.");
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

    public String processCommand(String input) {

        try {
            input = input.trim().toUpperCase();

            switch (input) {

                case "BUILD":
                    addBuildingNetwork();
                    return "Building started";

				case "TRAIN WORKER":
					addSpecificInhabitant(InhabitantType.WORKER);
					return "Lumberman training started";


                case "TRAIN LUMBERMAN":
                    addSpecificInhabitant(InhabitantType.LUMBERMAN);
                    return "Lumberman training started";

                case "TRAIN IRON MINER":
                    addSpecificInhabitant(InhabitantType.IRON_MINER);
                    return "Iron miner training started";

                case "TRAIN GOLD MINER":
                    addSpecificInhabitant(InhabitantType.GOLD_MINER);
                    return "Gold miner training started";

                case "TRAIN SOLDIER":
                    addSpecificInhabitant(InhabitantType.SOLDIER);
                    return "Soldier training started";

                case "TRAIN ARCHER":
                    addSpecificInhabitant(InhabitantType.ARCHER);
                    return "Archer training started";

                case "TRAIN KNIGHT":
                    addSpecificInhabitant(InhabitantType.KNIGHT);
                    return "Knight training started";

                case "TRAIN CATAPULT":
                    addSpecificInhabitant(InhabitantType.CATAPULT);
                    return "Catapult training started";

                case "UPGRADE":
                    upgradeBuildingNetwork();
                    return "Upgrade started";

                case "ATTACK":
                    return attackNetwork();

                case "GENERATE VILLAGE":
                    return "Village generated";

                case "GENERATE ARMY":
                    return "Army generated";

                case "TEST VILLAGE":
                    return "Village test complete";

                case "EXIT":
                    return "EXIT";

                default:
                    return "INVALID COMMAND";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR processing command";
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
