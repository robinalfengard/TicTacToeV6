package com.example.tictactoev6;

import com.example.tictactoev6.Network.Client;
import com.example.tictactoev6.Network.Server;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
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

    private Model model;

    Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model();
        client = new Client(model);
        messageReceived.textProperty().bind(model.messageReceivedProperty());
    }

    public void goOnline(ActionEvent actionEvent) {
        client.connect();
    }


    public Model getModel(){
        return model;
    }


    public void sendMessage(ActionEvent actionEvent) {
        client.sendMessage(messageToSend.getText());
        messageToSend.clear();
    }


}