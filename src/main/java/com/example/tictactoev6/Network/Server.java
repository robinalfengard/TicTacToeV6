package com.example.tictactoev6.Network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private final int PORT = 6000;
    private int numberOfPlayers;


    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for players to connect...");
            while (numberOfPlayers<2){
                Socket socket = serverSocket.accept();
                System.out.println("Player connected");
                PlayerHandler ph = new PlayerHandler(socket);
                numberOfPlayers++;
                Thread thread = new Thread(ph);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error when trying to start server");
        }
    }
}
