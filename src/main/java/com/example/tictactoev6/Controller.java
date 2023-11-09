package com.example.tictactoev6;
import com.example.tictactoev6.Network.Player;
import com.example.tictactoev6.Network.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Canvas box1;
    public Canvas box2;
    public Canvas box3;
    public Canvas box4;
    public Canvas box5;
    public Canvas box6;
    public Canvas box7;
    public Canvas box8;
    public Canvas box9;
    public Label winningMessage;
    public Label opponentScorePrintout;
    public Label yourScorePrintout;
    public Label isItYourTurn;
    public Button startServerButton;
    public Button haveJoinedButton;
    private Model model;
    Player player;
    Server server;
    private final Map<String, Canvas> boxMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model();
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
        model.setBoxMap(boxMap);
        winningMessage.textProperty().bind(model.winningMessageProperty());
        opponentScorePrintout.textProperty().bind(model.opponentScorePrintoutProperty());
        yourScorePrintout.textProperty().bind(model.userScorePrintoutProperty());
        isItYourTurn.textProperty().bind(model.isItYourTurnPrintOutProperty());
        haveJoinedButton.disableProperty().bind(model.haveJoinedProperty());
        startServerButton.disableProperty().bind(model.isServerRunningProperty());
    }

    public Model getModel() {
        return model;
    }


    public void goOnline(ActionEvent actionEvent) {
        try {
            player = new Player(model);
            player.listenForMessages();
            model.setGameRunning(true);
            model.setHaveJoined(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void boxClicked(MouseEvent event) {
        Canvas boxClicked = (Canvas) event.getSource();
        String boxId = boxClicked.getId();
        if(model.isGameRunning() && model.isIsItYourTurn()){
            model.makeMove(boxId);
            player.sendMessage(boxId);
        }
    }


    public void playAgain(ActionEvent actionEvent) {
        model.resetGameBoard();
        player.sendMessage("resetGameBoard");
    }

    public void resetScore(ActionEvent actionEvent) {
        model.resetScore();
        player.sendMessage("resetScore");
    }


    public void startServer(ActionEvent actionEvent) {
        Thread serverThread = new Thread(()->{
            server = new Server();
            server.startServer();
        });
        serverThread.start();
        model.setIsServerRunning(true);
    }
}