package com.example.tictactoev6.Network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private final int PORT = 6000;


    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for players to connect...");
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Player connected");
                PlayerHandler ph = new PlayerHandler(socket);
                Thread thread = new Thread(ph);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error when trying to start server");
        }
    }
}
