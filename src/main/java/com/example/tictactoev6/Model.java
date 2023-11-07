package com.example.tictactoev6;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class Model {
    GameLogic gameLogic = new GameLogic();
    FactoryMethods factoryMethods = new FactoryMethods();
    private  Map<String, Canvas> boxMap;
    // CHekca om det går att använda kopian för isValidMove och remove, då behöver jag bara måla om alla orginalcanvas till vit vid reset.
    private  List<String> availableMoves;
    private final List<String> userMoves = new ArrayList<>();
    private final List<String> opponentMoves = new ArrayList<>();
    private final StringProperty winningMessageProperty = new SimpleStringProperty();
    private  int opponentScore = 0;
    private final StringProperty opponentScorePrintout = new SimpleStringProperty("Opponent score: " + opponentScore);
    private  int userScore = 0;
    private final StringProperty userScorePrintout = new SimpleStringProperty("Your score: " + userScore);


    public Model(){
        availableMoves = factoryMethods.getAvailableMoves();
    }


    public void makeMove(String boxId) {
            Platform.runLater(() -> {
                if (gameLogic.isMoveValid(boxId, boxMap, availableMoves)) {
                    boxSelector(boxId, Color.BLUE);
                    userMoves.add(boxId);
                    gameLogic.removeMove(boxId, availableMoves);
                }
                isGameOver();
            });
    }

    public void MakeOpponentMove(String boxId) {
        Platform.runLater(() -> {
                boxSelector(boxId, Color.RED);
                opponentMoves.add(boxId);
                gameLogic.removeMove(boxId, availableMoves);
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
        }
    }

    private void userWin() {
        setWinningMessage("You won!");
        userScore += 1;
        setUserScorePrintout("Your score: " + userScore);
    }

    private void opponentWin() {
        setWinningMessage("Opponent won!");
        opponentScore +=1;
        setOpponentScorePrintout("Opponent score: " + opponentScore);
    }


    public void boxSelector(String id, Color color) {
        Canvas canvas = boxMap.get(id);
        if (canvas != null) {
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(color); // Set the fill color based on the provided color
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2.0);
                gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
            });
        }
    }

    private void disableAllMoves() {
        availableMoves.clear();
    }


    public void resetScore() {
        userScore = 0;
        opponentScore = 0;
        setOpponentScorePrintout("Opponent score: " + opponentScore);
        setUserScorePrintout("Your score: " + userScore);
        resetGameBoard();
    }
    public void playAgain() {
        userMoves.clear();
        opponentMoves.clear();
        resetGameBoard();
    }

    public void resetRequest(String message) {
        System.out.println(message + " received in model");
        userMoves.clear();
        opponentMoves.clear();
        if(message.equals("reset"))
            Platform.runLater(this::resetGameBoard);
    }


    private void resetGameBoard() {
        Platform.runLater(() -> {
            boxMap.forEach((box, canvas) -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2.0);
                gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
            });
        });
        availableMoves = factoryMethods.getAvailableMoves();
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

    public String getUserScorePrintout() {
        return userScorePrintout.get();
    }

    public StringProperty userScorePrintoutProperty() {
        return userScorePrintout;
    }

    public void setUserScorePrintout(String userScorePrintout) {
        this.userScorePrintout.set(userScorePrintout);
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
