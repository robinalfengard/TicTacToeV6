package com.example.tictactoev6.Network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PlayerHandler  implements Runnable{
    private static final List<PlayerHandler> players = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public PlayerHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            players.add(this);
        } catch (IOException ex){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String moveFromPlayer;
        while (true) {
            try {
                moveFromPlayer = bufferedReader.readLine();
                noticeLatestMove(moveFromPlayer);
            } catch (IOException ex) {
                closeAll(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void noticeLatestMove(String moveFromPlayer) {
        for (PlayerHandler player : players) {
            try {
                player.bufferedWriter.write(moveFromPlayer + "\n");
                player.bufferedWriter.flush();
            } catch (IOException ex) {
                closeAll(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException ex){
            System.out.println("Error from closeAll");
        }
    }
}
