package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

public class GameServer implements Runnable {
	private int serverPort;
	private ServerSocket serverSocket = null;
	private boolean isStopped = false;
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private ConcurrentHashMap<String, Player> players;

	public GameServer(int port) {
		this.serverPort = port;
		players = new ConcurrentHashMap<>();
	}

	public Player authenticatePlayer(String id) {
		if (players.containsKey(id)) {
			return players.get(id);
		}

		if (playerExistsInFile(id)) {
			Player player = new Player(id, new Village());
			players.put(id, player);
			return player;
		}

		return null;
	}

	private boolean playerExistsInFile(String id) {
		Path path = Paths.get("players.txt");

		if (!Files.exists(path)) {
			return false;
		}

		try (Stream<String> lines = Files.lines(path)) {
			return lines.map(String::trim)
					.anyMatch(line -> line.equals(id));
		} catch (IOException e) {
			throw new RuntimeException("Error reading players.txt", e);
		}
	}

	public void run() {
		int counter = 0;

		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port 8080", e);
		}

		while (!isStopped()) {
			Socket clientSocket;

			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Game server stopped.");
					break;
				}

				throw new RuntimeException("Error accepting client connection", e);
			}

			System.out.println("Connection number " + ++counter);
			this.threadPool.execute(new GameEngine(this, clientSocket, players));
		}

		this.threadPool.shutdown();
		System.out.println("Game server stopped.");
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		if (((ThreadPoolExecutor) threadPool).getActiveCount() == 1) {
			this.isStopped = true;

			try {
				this.serverSocket.close();
			} catch (IOException e) {
				throw new RuntimeException("Error closing server", e);
			}
		}
	}

	public static void main(String[] args) {
		GameServer server = new GameServer(8080);
		System.out.println("Starting game server.");
		new Thread(server).start();
	}
}