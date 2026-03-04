package game;

import gameElements.*;
import gui.GraphicalInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;
import java.util.HashMap;
import utility.*;

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
	 * This is a constructor for GameEngine.
	 */
	public GameEngine() {
		players = new HashMap<>();
		villages = new HashMap<>();
		in = new BufferedReader(new InputStreamReader(System.in));
		scheduler = new ScheduledThreadPoolExecutor(5);
		running = false;
	}

	/**
	 * This method adds a player to the list of players.
	 * If the player does not have a village, create a new village for the player.
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
	 * This method sets the active player.
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
	 * This method starts the game thread
	 */
	public void startGameThread() {
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * This method is the main game thread.
	 * It takes commands from the console and then executes the commands, through the update method
	 */
	@Override
	public void run() {
		draw();

		while (running) {
			System.out.print("Command: ");
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
	 * This method executes the input command.
	 * @param input the command to execute
	 * @throws IOException if addBuilding() or switchPlayer() throw exceptions
	 */
	public void update(String input) throws IOException {
		switch (input) {
			case "add building":
				this.addBuilding();
				return;
			case "switch player":
				this.switchPlayer();
				return;
			case "exit":
				running = false;
				in.close();
				return;
			default:
				System.out.println("Invalid command.");
		}
	}

	/**
	 * This method draws the village of the active player.
	 */
	public void draw() {
		synchronized (base) {
			int[] inventory = base.getInventoryValues();
			System.out.printf("Inventory: Gold - %d, Iron - %d, Lumber - %d", inventory[0], inventory[1], inventory[2]);
			System.out.println();
			Building[][] map = base.getMap();

			for (Building[] buildings : map) {
				for (Building building : buildings) {
					if (building == null) {
						System.out.print(".");
					} else {
						switch (building.getClass().getName()) {
							case "gameElements.VillageHall":
								System.out.print("H");
								break;
							case "gameElements.Farm":
								System.out.print("F");
								break;
						}
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
	 * This method adds a new building to the village.
	 * @throws IOException if BufferedReader fails
	 */
	public void addBuilding() throws IOException {
		System.out.print("Building type: ");
		BuildingType type;

		switch (in.readLine().toLowerCase()) {
			case "village hall":
				type = BuildingType.VILLAGE_HALL;
				break;
			case "farm":
				type = BuildingType.FARM;
				break;
			case "lumber mill":
				type = BuildingType.LUMBER_MILL;
				break;
			case "iron mine":
				type = BuildingType.IRON_MINE;
				break;
			case "gold mine":
				type = BuildingType.GOLD_MINE;
				break;
			case "archer tower":
				type = BuildingType.ARCHER_TOWER;
				break;
			case "cannon":
				type = BuildingType.CANNON;
				break;
			default:
				System.out.println("Invalid building type.");
				return;
		}

		System.out.print("Position [x y]: ");
		String posInput = in.readLine();
		Position pos = parsePosition(posInput);

		if (pos == null) {
			System.out.println("Invalid building position.");
			return;
		}

		// User input acquired. Now time for synchronized part
		synchronized (base) {

		}
		/*
		Worker builder = current.checkAddBuilding(type, pos);

		if (builder == null) {
			System.out.println("Invalid resources/space/builder.");
			return;
		}

		System.out.println("Building started...");
		scheduler.schedule(() -> {
			synchronized (current) {
				builder.setBusy(true);
				current.addBuildingTest(type, pos);
				builder.setBusy(false);
			}
			System.out.println("Building completed.");
			draw();
		}, Player.getBuildTime(type), TimeUnit.SECONDS);*/
	}

	/**
	 * This method switches the active player
	 * @throws IOException if BufferedReader fails
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
	 * This method creates a position object from string input
	 * @param input the input string of the form "[int] [int]"
	 * @return the position object, or null if the input was invalid
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
	 * This method begins a new game.
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
