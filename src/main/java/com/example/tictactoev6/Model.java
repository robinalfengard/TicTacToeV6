package com.example.tictactoev6;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model {
    GameLogic gameLogic = new GameLogic();
    private  Map<String, Canvas> boxMap;
    private final List<String> userMoves = new ArrayList<>();
    private final List<String> opponentMoves = new ArrayList<>();


    public Model(){

    }

    public void makeMove(String boxId) {
        Platform.runLater(() -> {
            if(gameLogic.isMoveValid(boxId, boxMap)){
                System.out.println(boxId + " from makeMove");
                boxSelectorUser(boxId);
                userMoves.add(boxId);
                gameLogic.removeMove(boxId, boxMap);
            }
            isGameOver();
        });
    }

    public void MakeOpponentMove(String boxId) {
        Platform.runLater(() -> {
            if(gameLogic.isMoveValid(boxId, boxMap)){
                boxSelectorOpponent(boxId);
                opponentMoves.add(boxId);
                gameLogic.removeMove(boxId, boxMap);
            }
            isGameOver();
        });
    }

    public void isGameOver() {
        if (gameLogic.winCheck(opponentMoves)) {
            System.out.println("Opponent won");
        } else if (gameLogic.winCheck(userMoves)) {
            System.out.println("You won!");
        } else if (boxMap.isEmpty()) {
            System.out.println("It's a tie");
        }
    }


    public void boxSelectorUser(String id) {
        System.out.println(id + " from boxSelector");
        Canvas canvas = boxMap.get(id);
        if (canvas != null) {
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.RED); // Set the fill color
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                System.out.println("Box " + id + " turned red.");
            });
        }
    }

    public void boxSelectorOpponent(String id) {
        System.out.println(id + " from boxSelector");
        Canvas canvas = boxMap.get(id);
        if (canvas != null) {
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.BLUE); // Set the fill color
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                System.out.println("Box " + id + " turned blue.");
            });
        }
    }

    public void setBoxMap(Map<String, Canvas> boxMap) {
        this.boxMap = boxMap;
    }
}
