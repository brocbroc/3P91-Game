package client;

import server.Packet;
import server.Protocol;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents a client of the game. It acts as the view of the MVC pattern. It takes
 * input from the user and displays output from the server.
 */
public class GameClient {
	private String id;
	private String hostname;
	private int port;
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private BufferedReader in;
	private volatile boolean attacking;
	private BlockingQueue<Packet> packetQueue;

	public GameClient(String id, String hostname, int port) {
		this.id = id;
		this.hostname = hostname;
		this.port = port;
		attacking = false;
		packetQueue = new LinkedBlockingQueue<>();

		try {
			socket = new Socket(hostname, port);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			System.out.println("Successfully connected to the server.");

			// Verify client; handshake protocol
			Packet key = new Packet(Protocol.KEY, id);
			outputStream.writeObject(key);
			outputStream.flush();
			key = (Packet) inputStream.readObject();

			if (key.getHeader() != Protocol.KEY || key.getMessage().equals("invalid")) {
				System.out.println("Client is not a valid player.");
				close();
				return;
			}

			// Begin game
			in = new BufferedReader(new InputStreamReader(System.in));
			new Thread(this::receiveLoop).start();
			new Thread(this::sendLoop).start();

			//close();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public void receiveLoop() {
		try {
			while (!socket.isClosed()) {
				Packet input = (Packet) inputStream.readObject();

				switch (input.getHeader()) {
					case UPDATE:
						System.out.println(input.getMessage());
						draw(input.getSnapshot());
						break;
					case ALERT:
						System.out.println(input.getMessage());
						break;
					case PROMPT:
						packetQueue.put(input);
						break;
				}
			}
		} catch (IOException e) {
			if (!socket.isClosed()) {
				throw new RuntimeException(e);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Draws the village
	 */
	public void draw(VillageSnapshot snapshot) {
		System.out.println();
		int[] inventory = snapshot.getInventory();
		System.out.printf("Inventory: Gold - %d, Iron - %d, Lumber - %d", inventory[0], inventory[1], inventory[2]);
		System.out.println();
		String[][] map = snapshot.getMap();
		int[] inhabitantLevels = snapshot.getInhabitantLevels();
		int[] inhabitantCounts = snapshot.getInhabitantCounts();

		drawVillageRow(map[0]);
		System.out.printf("    Workers:     %d (level %d)\n", inhabitantCounts[0], inhabitantLevels[0]);
		drawVillageRow(map[1]);
		System.out.printf("    Lumbermen:   %d (level %d)\n", inhabitantCounts[1], inhabitantLevels[1]);
		drawVillageRow(map[2]);
		System.out.printf("    Iron miners: %d (level %d)\n", inhabitantCounts[2], inhabitantLevels[2]);
		drawVillageRow(map[3]);
		System.out.printf("    Gold miners: %d (level %d)\n", inhabitantCounts[3], inhabitantLevels[3]);
		drawVillageRow(map[4]);
		System.out.printf("    Soldiers:    %d (level %d)\n", inhabitantCounts[4], inhabitantLevels[4]);
		drawVillageRow(map[5]);
		System.out.printf("    Archers:     %d (level %d)\n", inhabitantCounts[5], inhabitantLevels[5]);
		drawVillageRow(map[6]);
		System.out.printf("    Knights:     %d (level %d)\n", inhabitantCounts[6], inhabitantLevels[6]);
		drawVillageRow(map[7]);
		System.out.printf("    Catapults:   %d (level %d)\n", inhabitantCounts[7], inhabitantLevels[7]);

		for (int i = 8; i < map.length; i++) {
			drawVillageRow(map[i]);
			System.out.println();
		}

		for (int i = 0; i < map[0].length; i++) {
			System.out.print("———");
		}

		System.out.println();
	}

	/**
	 * Draws the given row of the village
	 * @param mapRow a row of the village
	 */
	private void drawVillageRow(String[] mapRow) {
		for (String s : mapRow) {
			System.out.print(s + " ");
		}
	}

	public void sendLoop() {
		try {
			while (!socket.isClosed()) {
				String command = in.readLine().toLowerCase();

				switch (command) {
					case "menu":
						System.out.println("1. add building");
						System.out.println("2. upgrade building");
						System.out.println("3. train inhabitant (add new inhabitant)");
						System.out.println("4. upgrade inhabitant");
						System.out.println("5. generate village (attack exploring)");
						System.out.println("6. attack testing (test village army against multiple enemy villages)");
						System.out.println("7. village testing (test village defenses against multiple enemy armies)");
						System.out.println("8. check rank");
						System.out.println("9. close game");
						break;
					case "add building":
						outputStream.writeObject(addBuilding());
						outputStream.flush();
						break;
					case "upgrade building":
						outputStream.writeObject(upgradeBuilding());
						outputStream.flush();
						break;
					case "train inhabitant":
						outputStream.writeObject(trainInhabitant());
						outputStream.flush();
						break;
					case "upgrade inhabitant":
						outputStream.writeObject(upgradeInhabitant());
						outputStream.flush();
						break;
					case "generate village":
						outputStream.writeObject(generateVillage());
						outputStream.flush();
						handleGenerateVillage();
						break;
					case "attack testing":
						outputStream.writeObject(new Packet(Protocol.ATTACK_TESTING, ""));
						outputStream.flush();
						break;
					case "village testing":
						outputStream.writeObject(new Packet(Protocol.VILLAGE_TESTING, ""));
						outputStream.flush();
						break;
					case "check rank":
						outputStream.writeObject(new Packet(Protocol.CHECK_RANK, ""));
						outputStream.flush();
						break;
					case "close game":
						outputStream.writeObject(closeGame());
						outputStream.flush();
						close();
						break;
					default:
						System.out.println("Invalid command.");
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Packet addBuilding() throws IOException {
		System.out.print("Building type: ");
		String type = in.readLine().toLowerCase();
		System.out.print("Position [x y]: ");
		String position = in.readLine().toLowerCase();
		return new Packet(Protocol.ADD_BUILDING, type + "\n" + position);
	}

	public Packet upgradeBuilding() throws IOException {
		System.out.print("Position [x y]: ");
		String position = in.readLine().toLowerCase();
		return new Packet(Protocol.UPGRADE_BUILDING, position);
	}

	public Packet trainInhabitant() throws IOException {
		System.out.print("Inhabitant type: ");
		String type = in.readLine().toLowerCase();
		return new Packet(Protocol.TRAIN_INHABITANT, type);
	}

	public Packet upgradeInhabitant() throws IOException {
		System.out.print("Inhabitant type: ");
		String type = in.readLine().toLowerCase();
		return new Packet(Protocol.UPGRADE_INHABITANT, type);
	}

	public Packet generateVillage() {
		return new Packet(Protocol.GENERATE_VILLAGE, "");
	}

	public void handleGenerateVillage() throws IOException {
		try {
			Packet option = packetQueue.take();
			System.out.print(option.getMessage());
			System.out.print("Enter accept to begin attack (anything else to stop): ");
			String response = in.readLine().toLowerCase();

			switch (response) {
				case "accept":
					System.out.println("Target found. Preparing attack force...");
					attacking = true;
					handleAttack();
					break;
				default:
					System.out.println("Target rejected.");
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void handleAttack() throws IOException {
		System.out.print("Number of soldiers: ");
		String fighters = in.readLine() + "\n";
		System.out.print("Number of archers: ");
		fighters += in.readLine() + "\n";
		System.out.print("Number of knights: ");
		fighters += in.readLine() + "\n";
		System.out.print("Number of catapults: ");
		fighters += in.readLine();
		outputStream.writeObject(new Packet(Protocol.ATTACK, fighters));
		outputStream.flush();
	}

	public Packet closeGame() {
		return new Packet(Protocol.CLOSE_GAME, "");
	}

	public synchronized void close() throws IOException {
		inputStream.close();
		outputStream.close();
		socket.close();
		System.out.println("Client closed.");
	}

	public static void main(String[] args) {
		GameClient client = new GameClient("2", "localhost", 8080);
	}
}
