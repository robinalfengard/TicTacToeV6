package com.example.tictactoev6;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private final StringProperty winningMessageProperty = new SimpleStringProperty();
    private  int opponentScore = 0;
    private final StringProperty opponentScorePrintout = new SimpleStringProperty("Opponent score: " + opponentScore);
    private  int yourScore = 0;
    private final StringProperty yourScorePrintout = new SimpleStringProperty("Your score: " + yourScore);


    public Model(){

    }

    public void makeMove(String boxId) {
            Platform.runLater(() -> {
                if (gameLogic.isMoveValid(boxId, boxMap)) {
                    System.out.println(boxId + " from makeMove");
                    boxSelector(boxId, Color.BLUE);
                    userMoves.add(boxId);
                    gameLogic.removeMove(boxId, boxMap);
                }
                isGameOver();
            });
    }

    public void MakeOpponentMove(String boxId) {
        Platform.runLater(() -> {
            if(gameLogic.isMoveValid(boxId, boxMap)){
                boxSelector(boxId, Color.RED);
                opponentMoves.add(boxId);
                gameLogic.removeMove(boxId, boxMap);
            }
            isGameOver();
        });

    }

    public void isGameOver() {
        if (gameLogic.winCheck(opponentMoves)) {
            opponentWin();
            disableAllMoves();
        } else if (gameLogic.winCheck(userMoves)) {
            userWin();
            disableAllMoves();
        } else if (boxMap.isEmpty()) {
            setWinningMessage("It's a tie!");
            disableAllMoves();
        }
    }

    private void userWin() {
        setWinningMessage("You won!");
        yourScore += 1;
        setYourScorePrintout("Your score: " + yourScore);
    }

    private void opponentWin() {
        setWinningMessage("Opponent won!");
        opponentScore +=1;
        setOpponentScorePrintout("Opponent score: " + opponentScore);
    }


    public void boxSelector(String id, Color color) {
        System.out.println(id + " from boxSelector");
        Canvas canvas = boxMap.get(id);
        if (canvas != null) {
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(color); // Set the fill color based on the provided color
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                System.out.println("Box " + id + " turned " + color.toString().toLowerCase() + ".");
            });
        }
    }

    private void disableAllMoves() {
        boxMap.clear();
    }


    public void resetScore() {
        yourScore = 0;
        opponentScore = 0;
        setOpponentScorePrintout("Opponent score: " + opponentScore);
        setYourScorePrintout("Your score: " + yourScore);
    }


    // GETTER AND SETTER

    public void setBoxMap(Map<String, Canvas> boxMap) {
        this.boxMap = boxMap;
    }

    public StringProperty winningMessageProperty() {
        return winningMessageProperty;
    }

    public void setWinningMessage(String message) {
        winningMessageProperty.set(message);
    }

    public String getYourScorePrintout() {
        return yourScorePrintout.get();
    }

    public StringProperty yourScorePrintoutProperty() {
        return yourScorePrintout;
    }

    public void setYourScorePrintout(String yourScorePrintout) {
        this.yourScorePrintout.set(yourScorePrintout);
    }

    public String getOpponentScorePrintout() {
        return opponentScorePrintout.get();
    }

    public StringProperty opponentScorePrintoutProperty() {
        return opponentScorePrintout;
    }

    public void setOpponentScorePrintout(String opponentScorePrintout) {
        this.opponentScorePrintout.set(opponentScorePrintout);
    }


}
