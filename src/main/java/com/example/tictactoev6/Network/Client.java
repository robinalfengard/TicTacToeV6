package com.example.tictactoev5.Network;

import com.example.tictactoev5.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public void connect() {
        try {
            Socket socket = new Socket("localhost", 6000);
            System.out.println("Connected to the server.");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Thread messageListener = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        if (message == null) {
                            // Connection closed by the server or an error occurred
                            break;
                        }
                        // Handle the received message
                        System.out.println("Received: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            messageListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

