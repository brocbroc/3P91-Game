package game;

import gameElements.*;
import gameElements.building.*;
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
			Village v = new Village(id);
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
			try {
				// Not working sometimes????
				// Not accepting new input after adding all buildings once
				System.out.println("Input command... ");
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
	 * @throws IOException if <code>addBuilding()</code> or <code>switchPlayer()</code> throw an
	 * exception
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
				// ADD CODE
				return;
			case "upgrade inhabitant":
				// ADD CODE
				return;
			case "attack":
				// ADD CODE
				// generate village
				// let player look for new village until they find one they like
				// begin attack
				// technically separate cases but related.
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
				constructor = new VillageHallConstructor();
				break;
			case "farm":
				constructor = new FarmConstructor();
				break;
			case "lumber mill":
				constructor = new LumberMillConstructor();
				break;
			case "iron mine":
				constructor = new IronMineConstructor();
				break;
			case "gold mine":
				constructor = new GoldMineConstructor();
				break;
			case "archer tower":
				constructor = new ArcherTowerConstructor();
				break;
			case "cannon":
				constructor = new CannonConstructor();
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

		System.out.println("Under construction...");
		final Village BASE = base;
		scheduler.schedule(() -> {
			BASE.completeAddBuilding(builder, pos);

			if (BASE == base) {
				System.out.println("Building completed.");
				draw();
			}
		}, constructor.getBuildTime(), TimeUnit.SECONDS);
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

		// Check upgrade requirements then make upgrade
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
			draw();
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

		// Definitely need to figure out how to add events
	}
}
