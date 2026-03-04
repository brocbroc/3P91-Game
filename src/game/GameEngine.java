package game;

import gameElements.*;
import gui.GraphicalInterface;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import utility.*;

public class GameEngine implements Runnable {
	private GraphicalInterface gui;
	private HashMap<Integer, Player> players;
	private HashMap<Integer, Village> villages;
	private Thread gameThread;
	private Scanner in;
	private ScheduledExecutorService scheduler;
	private Player currentPlayer;
	private Village base;

	public GameEngine() {
		players = new HashMap<>();
		villages = new HashMap<>();
		in = new Scanner(System.in);
		scheduler = new ScheduledThreadPoolExecutor(5);
	}

	public void addPlayer(int id) {
		if (villages.containsKey(id)) {
			players.put(id, new Player(id, villages.get(id)));
		} else {
			Village v = new Village(id);
			villages.put(id, v);
			players.put(id, new Player(id, v));
		}
	}

	public void setCurrentPlayer(int id) {
		if (players.containsKey(id)) {
			currentPlayer = players.get(id);
			base = currentPlayer.getVillage();
		} else {
			System.out.println("Invalid player id");
		}
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		draw();

		while (gameThread != null) {
			System.out.print("Command: ");
			String input = in.nextLine().toLowerCase();
			update(input);
		}
	}

	public void update(String input) {
		if (input.equals("add building")) {
			this.addBuilding();
			return;
		}

		System.out.println("Invalid command.");
	}

	private Position parsePosition(String input) {
		String[] coordinates = input.split(" ");

		try {
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			return new Position(x, y);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public void draw() {
		synchronized (current) {
			Building[][] map = current.getVillage().getMap();

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

	public void addBuilding() {
		System.out.print("Building type: ");
		BuildingType type;

		switch (in.nextLine().toLowerCase()) {
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
		String posInput = in.nextLine();
		Position pos = parsePosition(posInput);

		if (pos == null) {
			System.out.println("Invalid building position.");
			return;
		}

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
		}, Player.getBuildTime(type), TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		System.out.println("Start");
		GameEngine game = new GameEngine();
		game.addPlayer(0);
		game.setCurrentPlayer(0);
		game.startGameThread();

		// Definitely need to figure out how to add events
	}
}
