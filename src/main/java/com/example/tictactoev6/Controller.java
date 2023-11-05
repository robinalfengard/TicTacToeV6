package com.example.tictactoev6;

import com.example.tictactoev6.Network.Client;
import com.example.tictactoev6.Network.Server;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Label messageReceived;
    @FXML
    public Button send;
    @FXML
    public TextField messageToSend;
    @FXML
    public Label usersOnline;
    public Canvas box1;
    public Canvas box2;
    public Canvas box3;
    public Canvas box4;
    public Canvas box5;
    public Canvas box6;
    public Canvas box7;
    public Canvas box8;
    public Canvas box9;
    private Model model;
    Client client;
    private final Map<String, Canvas> boxMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model();
        client = new Client(model);
        messageReceived.textProperty().bind(model.messageReceivedProperty());
        boxMap.put("box1", box1);
        boxMap.put("box2", box2);
        boxMap.put("box3", box3);
        boxMap.put("box4", box4);
        boxMap.put("box5", box5);
        boxMap.put("box6", box6);
        boxMap.put("box7", box7);
        boxMap.put("box8", box8);
        boxMap.put("box9", box9);
        model.setBoxMap(boxMap);
        // Repeat for other boxes

        // Set the map in the model
        model.setBoxMap(boxMap);
    }

    public void goOnline(ActionEvent actionEvent) {
        client.connect();
    }


    public Model getModel(){
        return model;
    }


    public void sendMessage(ActionEvent actionEvent) {
       /* client.sendMessage(messageToSend.getText());*/
        messageToSend.clear();
    }


    public void boxClicked(MouseEvent event) {
        Canvas boxClicked = (Canvas) event.getSource();
        String boxId = boxClicked.getId();
        client.MakeMove(boxId);
        model.makeMove(boxId);
        System.out.println(boxId +  " sent from box clicked");
    }

    public void playAgain(ActionEvent actionEvent) {

    }

    public void resetScore(ActionEvent actionEvent) {
    }
}