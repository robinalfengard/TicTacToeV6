package com.example.tictactoev5;
import javafx.scene.image.Image;
import java.net.Socket;


public class Player {
    private int id;
    private Socket socket;

    public Player(Socket socket, int playerId) {
        this.socket = socket;
        this.id = playerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }



}
