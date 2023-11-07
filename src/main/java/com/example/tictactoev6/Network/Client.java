package com.example.tictactoev6.Network;
import com.example.tictactoev6.Model;
import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Model model;

    public Client(Model model) {
        this.model = model;
    }

    private PrintWriter out;
    public void connect() {
        try {
            Socket socket = new Socket("localhost", 6000);
            System.out.println("Connected to the server.");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Thread messageListener = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        if (message == null) {
                            // Connection closed by the server or an error occurred
                            break;
                        }
                        // Handle the received message
                            System.out.println("incoming message from client: " + message);
                            Platform.runLater(() -> model.MakeOpponentMove(message));



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


    public void MakeMove(String boxClicked) {
        if (out != null) {
            out.println(boxClicked); // Send the message to the server
            out.flush();
        }
    }
}

