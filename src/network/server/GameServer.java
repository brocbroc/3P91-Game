package network.server;

import java.net.*;
import game.GameEngine;

public class GameServer {

    private static int nextPlayerId = 0;
    private static GameEngine engine = new GameEngine();

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server running on port 5000");

        // create players ONCE
        engine.addPlayer(0);
        engine.addPlayer(1);

        while (true) {
            Socket socket = serverSocket.accept();

            int playerId = nextPlayerId++;
            System.out.println("Client connected as Player " + playerId);

            new Thread(new ClientHandler(socket, engine, playerId)).start();
        }
    }
}