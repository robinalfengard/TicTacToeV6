package com.example.tictactoev6.Network;
import com.example.tictactoev6.Model;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;

public class Player {
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;
    private final Socket socket;
    private final Model model;
    public Player(Model model) throws IOException {
        socket = new Socket("localhost", 6000);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.model = model;
    }


    public void sendMessage(String message){
        try{
            if(message != null){
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException ex){
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }


    public void listenForMessages(){
        new Thread(() -> {
            String incommingMessage;
            while(socket.isConnected()){
                try{
                    incommingMessage = bufferedReader.readLine();
                    if(incommingMessage != null) {
                        String messageToSend = incommingMessage;
                        if (incommingMessage.equals("resetGameBoard")){
                            Platform.runLater(model::resetGameBoard);
                        } else if (incommingMessage.equals("resetScore")) {
                            Platform.runLater(model::resetScore);
                        } else{
                            Platform.runLater(() -> model.makeOpponentMove(messageToSend));
                        }
                    }
                } catch (IOException ex){
                    closeAll(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
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
            System.out.println("Error from closeAll method");
        }
    }
}
