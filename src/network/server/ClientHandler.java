package network.server;

import java.io.*;
import java.net.*;
import game.GameEngine;

    public class ClientHandler implements Runnable {

        private Socket socket;
        private GameEngine engine;

        private boolean isLoggedIn = false;
        private int playerId = -1;

        public ClientHandler(Socket socket, GameEngine engine, int playerId) {
            this.socket = socket;
            this.engine = engine;
            this.playerId = playerId;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(
                            socket.getOutputStream(), true)
            ) {

                String request;

                while ((request = in.readLine()) != null) {

                    String response = processRequest(request);

                    out.println(response);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processRequest(String request) {

            try {
                if (request.startsWith("LOGIN")) {
                    isLoggedIn = true;
                    engine.setActivePlayer(playerId);
                    return "LOGIN_SUCCESS";
                }

                if (!isLoggedIn) {
                    return "ERROR: Please login first";
                }

                request = request.trim().toUpperCase();

                switch (request) {

                    case "BUILD":
                        return engine.processCommand("BUILD");

                    case "TRAIN WORKER":
                        return engine.processCommand("TRAIN WORKER");

                    case "TRAIN LUMBERMAN":
                        return engine.processCommand("TRAIN LUMBERMAN");

                    case "TRAIN IRON MINER":
                        return engine.processCommand("TRAIN IRON MINER");

                    case "TRAIN GOLD MINER":
                        return engine.processCommand("TRAIN GOLD MINER");

                    case "TRAIN SOLDIER":
                        return engine.processCommand("TRAIN SOLDIER");

                    case "TRAIN ARCHER":
                        return engine.processCommand("TRAIN ARCHER");

                    case "TRAIN KNIGHT":
                        return engine.processCommand("TRAIN KNIGHT");

                    case "TRAIN CATAPULT":
                        return engine.processCommand("TRAIN CATAPULT");

                    case "UPGRADE":
                        return engine.processCommand("UPGRADE");

                    case "ATTACK":
                        return engine.processCommand("ATTACK");

                    case "GENERATE VILLAGE":
                        return engine.processCommand("GENERATE VILLAGE");

                    case "GENERATE ARMY":
                        return engine.processCommand("GENERATE ARMY");

                    case "TEST VILLAGE":
                        return engine.processCommand("TEST VILLAGE");

                    case "EXIT":
                        return "EXIT";

                    default:
                        return "INVALID COMMAND";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR processing request";
            }
        }
    }