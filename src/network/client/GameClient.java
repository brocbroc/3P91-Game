package network.client;

import gameElements.InhabitantType;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 5000;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {

            // LOGIN
            out.println("LOGIN");
            String loginResponse = in.readLine();

            System.out.println(loginResponse);

            if (!loginResponse.contains("LOGIN_SUCCESS")) {
                System.out.println("Login failed");
                return;
            }

            while (true) {
                System.out.println("\nEnter command:");
                System.out.println("1. BUILD");
                System.out.println("2. TRAIN WORKER");
                System.out.println("3. TRAIN LUMBERMAN");
                System.out.println("4. TRAIN IRON MINER");
                System.out.println("5. TRAIN GOLD MINER");
                System.out.println("6. TRAIN SOLDIER");
                System.out.println("7. TRAIN ARCHER");
                System.out.println("8. TRAIN KNIGHT");
                System.out.println("9. TRAIN CATAPULT");
                System.out.println("10. UPGRADE");
                System.out.println("11. ATTACK");
                System.out.println("12. GENERATE VILLAGE");
                System.out.println("13. GENERATE ARMY");
                System.out.println("14. TEST VILLAGE");
                System.out.println("15. EXIT");

                String input = scanner.nextLine().trim();

                switch (input) {
                    case "1":
                        out.println("BUILD");
                        break;
                    case "2":
                        out.println("TRAIN WORKER");
                        break;
                    case "3":
                        out.println("TRAIN LUMBERMAN");
                        break;
                    case "4":
                        out.println("TRAIN IRON MINER");
                        break;
                    case "5":
                        out.println("TRAIN GOLD MINER");
                        break;
                    case "6":
                        out.println("TRAIN SOLDIER");
                        break;
                    case "7":
                        out.println("TRAIN ARCHER");
                        break;
                    case "8":
                        out.println("TRAIN KNIGHT");
                        break;
                    case "9":
                        out.println("TRAIN CATAPULT");
                        break;
                    case "10":
                        out.println("UPGRADE");
                        break;
                    case "11":
                        out.println("ATTACK");
                        break;
                    case "12":
                        out.println("GENERATE VILLAGE");
                        break;
                    case "13":
                        out.println("GENERATE ARMY");
                        break;
                    case "14":
                        out.println("TEST VILLAGE");
                        break;
                    case "15":
                        out.println("EXIT");
                        return;
                    default:
                        System.out.println("Invalid input");
                        continue;
                }
                String response = in.readLine();
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}