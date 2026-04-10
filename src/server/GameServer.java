package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer implements Runnable {
	private int serverPort;
	private ServerSocket serverSocket = null;
	private boolean isStopped = false;
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private HashMap<String, Player> players;

	public GameServer(int port) {
		this.serverPort = port;
		players = new HashMap<>();
		players.put("0", new Player("0", new Village()));
		players.put("1", new Player("1", new Village()));
		players.put("2", new Player("2", new Village()));
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
					System.out.println("Game server stopped.") ;
					break;
				}

				throw new RuntimeException("Error accepting client connection", e);
			}

			System.out.println("Connection number " + ++counter);
			this.threadPool.execute(new GameEngine(clientSocket, players));
		}

		this.threadPool.shutdown();
		System.out.println("Game server stopped.") ;
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;

		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	public static void main(String[] args) {
		GameServer server = new GameServer(8080);
		System.out.println("Starting game server.");
		new Thread(server).start();

		/*try {
			Thread.sleep(500 * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Stopping game server.");
		server.stop();*/
	}
}
