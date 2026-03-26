package gui;

import game.*;
import gameElements.InhabitantType;
import gameElements.building.Building;
import gameElements.inhabitant.InhabitantData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;

/**
 * This class represents the view in the MVC pattern. It displays output to the screen.
 */
public class View implements Runnable, Observer {
	private GameEngine game;
	private Player player;
	private Village base;
	private Thread gameThread;
	private BufferedReader in;
	private volatile boolean running;

	/**
	 * Class constructor
	 * @param game the game engine
	 * @param player the player that interacts with the view
	 */
	public View(GameEngine game, Player player) {
		this.game = game;
		this.player = player;
		base = player.getVillage();
		in = new BufferedReader(new InputStreamReader(System.in));
		running = false;
	}

	/**
	 * Returns true if the view thread is running
	 * @return true if the view is running, false otherwise
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Starts the game thread. Attaches an observer to the village base.
	 */
	public void startGameThread() {
		base.addObserver(this);
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * Closes the game thread
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public void closeGameThread() throws IOException {
		running = false;
		base.removeObserver(this);
		in.close();
	}

	/**
	 * Switches to a new player
	 * @param player the new player
	 */
	public void switchPlayer(Player player) {
		base.removeObserver(this);
		this.player = player;
		base = player.getVillage();
		base.addObserver(this);
		draw();
	}

	/**
	 * Runs the game thread.
	 * This method takes input from the console and then calls <code>update()</code>
	 */
	@Override
	public void run() {
		System.out.println("Player " + player.getId());
		draw();

		while (running) {
			try {
				String input = in.readLine().toLowerCase();
				game.update(input);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Draws the village
	 */
	public void draw() {
		final Village BASE = base;

		synchronized (BASE) {
			System.out.println();
			int[] inventory = base.getInventoryValues();
			System.out.printf("Inventory: Gold - %d, Iron - %d, Lumber - %d", inventory[0], inventory[1], inventory[2]);
			System.out.println();
			Building[][] map = base.getMap();
			EnumMap<InhabitantType, InhabitantData> inhabitantData = base.getAllInhabitantData();
			int[] counts = base.getInhabitantCountByType();

			drawVillageRow(map[0]);
			System.out.printf("    Workers:     %d (level %d)\n", counts[0], inhabitantData.get(InhabitantType.WORKER).getLevel());
			drawVillageRow(map[1]);
			System.out.printf("    Lumbermen:   %d (level %d)\n", counts[1], inhabitantData.get(InhabitantType.LUMBERMAN).getLevel());
			drawVillageRow(map[2]);
			System.out.printf("    Iron miners: %d (level %d)\n", counts[2], inhabitantData.get(InhabitantType.IRON_MINER).getLevel());
			drawVillageRow(map[3]);
			System.out.printf("    Gold miners: %d (level %d)\n", counts[3], inhabitantData.get(InhabitantType.GOLD_MINER).getLevel());
			drawVillageRow(map[4]);
			System.out.printf("    Soldiers:    %d (level %d)\n", counts[4], inhabitantData.get(InhabitantType.SOLDIER).getLevel());
			drawVillageRow(map[5]);
			System.out.printf("    Archers:     %d (level %d)\n", counts[5], inhabitantData.get(InhabitantType.ARCHER).getLevel());
			drawVillageRow(map[6]);
			System.out.printf("    Knights:     %d (level %d)\n", counts[6], inhabitantData.get(InhabitantType.KNIGHT).getLevel());
			drawVillageRow(map[7]);
			System.out.printf("    Catapults:   %d (level %d)\n", counts[7], inhabitantData.get(InhabitantType.CATAPULT).getLevel());

			for (int i = 8; i < map.length; i++) {
				drawVillageRow(map[i]);
				System.out.println();
			}

			for (int i = 0; i < map[0].length; i++) {
				System.out.print("———");
			}

			System.out.println();
		}
	}

	/**
	 * Draws the given row of the village
	 * @param buildings a row of the village
	 */
	private void drawVillageRow(Building[] buildings) {
		for (Building building : buildings) {
			if (building == null) {
				System.out.print("..");
			} else if (building.isUnderConstruction()) {
				System.out.print("##");
			} else {
				System.out.print(building.draw());
			}

			System.out.print(" ");
		}
	}

	/**
	 * Prints a message
	 * @param message the message
	 */
	public void printMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Prints a prompt and returns the response.
	 * @param prompt the prompt
	 * @throws IOException if <code>BufferedReader</code> fails
	 */
	public String prompt(String prompt) throws IOException {
		System.out.print(prompt);
		return in.readLine().toLowerCase();
	}

	/**
	 * Update the observer.
	 * Prints the message and draws the village.
	 * @param message a string describing the changes to the observable subject
	 */
	@Override
	public void update(String message) {
		if (running) {
			printMessage(message);
			draw();
		}
	}
}
