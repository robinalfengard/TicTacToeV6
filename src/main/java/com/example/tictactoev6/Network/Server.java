package com.example.tictactoev6.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    // Read what client is sending
    private BufferedReader bufferedReader;
    // Send messages to clients
    private BufferedWriter bufferedWriter;

    public static void main(String[] args) {
        new Server().startServer();
    }



    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            int numberOfPlayers = 0;
            // player 1
            System.out.println("Waiting for player 1 to connect...");
            Socket player1Socket = serverSocket.accept();
            numberOfPlayers++;
            Player player1 = new Player(player1Socket, numberOfPlayers);
            System.out.println("Player " + player1.getId() + " connected.");
            PrintWriter player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
            BufferedReader player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));


            // player 2
            System.out.println("Waiting for player 2 to connect...");
            Socket player2Socket = serverSocket.accept();
            numberOfPlayers++;
            Player player2 = new Player(player2Socket, numberOfPlayers);
            System.out.println("Player " + player2.getId() + " connected.");
            PrintWriter player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
            BufferedReader player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));


            Thread gameThread = new Thread(() -> {
                while (true) {
                    try {

                        // Input from player 1

                        String messageFromPlayer1 = player1In.readLine();
                        System.out.println("User1: " + messageFromPlayer1);
                        player2Out.println("User 1 says: " + messageFromPlayer1);


                        // Process and handle player 1's move (e.g., update game state)

                        // Input from player 2
                        String messageFromPlayer2 = player2In.readLine();
                        System.out.println("User2: " + messageFromPlayer2);
                        player1Out.println("User 2 says: " + messageFromPlayer2);

                        // Process and handle player 2's move (e.g., update game state)

                        // Send game state or messages back to players (if needed)
                        // For example, you can use the player1Out and player2Out PrintWriter objects to send responses.
                    } catch (IOException e) {
                        // Handle errors, such as player disconnects
                        // You might want to break out of the loop or perform appropriate error handling.
                    }
                }
            });

            // Start the game thread
            gameThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if(bufferedReader != null)
                bufferedReader.close();
            if(bufferedWriter != null)
                bufferedWriter.close();
            if(socket != null)
                socket.close();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error from closeAll method");
        }
    }
}
