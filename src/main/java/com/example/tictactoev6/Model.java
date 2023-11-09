package com.example.tictactoev6;
import com.example.tictactoev6.Network.PlayerHandler;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.*;

public class Model {
    //todo visa vems tur det är, userWin blir just nu +2
    GameLogic gameLogic = new GameLogic();
    FactoryMethods factoryMethods = new FactoryMethods();
    private  Map<String, Canvas> boxMap;
    private  List<String> availableMoves;
    private final StringProperty winningMessageProperty = new SimpleStringProperty();
    private  int opponentScore = 0;
    private final StringProperty opponentScorePrintout = new SimpleStringProperty("Opponent score: " + opponentScore);
    private  int userScore = 0;
    private final StringProperty userScorePrintout = new SimpleStringProperty("Your score: " + userScore);
    private BooleanProperty isItYourTurn = new SimpleBooleanProperty(true);
    private final StringProperty isItYourTurnPrintOut = new SimpleStringProperty("");
    private final BooleanProperty gameRunning = new SimpleBooleanProperty(true);


    public Model(){
        availableMoves = factoryMethods.getAvailableMoves();
    }


    public void makeMove(String boxId) {
            Platform.runLater(() -> {
                if (gameLogic.isMoveValid(boxId, boxMap, availableMoves) && isIsItYourTurn()) {
                    boxSelector(boxId, Color.BLUE);
                    gameLogic.getUserMoves().add(boxId);
                    gameLogic.removeMove(boxId, availableMoves);
                    setIsItYourTurn(false);
                    setTurnText();
                    System.out.println("Is it your turn after move: " + isIsItYourTurn());
                } else{
                    System.out.println("Not valid");
                }
                isGameOver();
            });
    }

    public void makeOpponentMove(String boxReceived) {
        Platform.runLater(() -> {
        if(gameLogic.isMoveValid(boxReceived, boxMap, availableMoves)) {
            boxSelector(boxReceived, Color.RED);
            gameLogic.getOpponentMoves().add(boxReceived);
            gameLogic.removeMove(boxReceived, availableMoves);
            setIsItYourTurn(true);
            setTurnText();
            System.out.println("Is it your turn after opponent move: " + isIsItYourTurn());
        }
            isGameOver();
        });
    }

    public void isGameOver() {
        if (gameLogic.winCheck(gameLogic.getOpponentMoves())) {
            opponentWin();
            setGameRunning(false);
        } else if (gameLogic.winCheck(gameLogic.getUserMoves())) {
            userWin();
            setGameRunning(false);
        } else if (boxMap.isEmpty()) {
            setWinningMessage("It's a tie!");
            setGameRunning(false);
        }
    }

    private void userWin() {
        setWinningMessage("You won!");
        userScore += 1;
        setUserScorePrintout("Your score: " + userScore);
        disableAllMoves();
    }

    private void opponentWin() {
        setWinningMessage("Opponent won!");
        opponentScore +=1;
        setOpponentScorePrintout("Opponent score: " + opponentScore);
        disableAllMoves();
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

    private void setTurnText(){
        if(isIsItYourTurn())
            setIsItYourTurnPrintOut("It is your turn");
        else{
            setIsItYourTurnPrintOut("It is the opponents turn");
        }
    }


    public void resetScore() {
        userScore = 0;
        opponentScore = 0;
        setOpponentScorePrintout("Opponent score: " + opponentScore);
        setUserScorePrintout("Your score: " + userScore);
        resetGameBoard();
    }


    public void resetGameBoard() {
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
        gameLogic.getUserMoves().clear();
        gameLogic.getOpponentMoves().clear();
        availableMoves = factoryMethods.getAvailableMoves();
        setGameRunning(true);
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

    public boolean isIsItYourTurn() {
        return isItYourTurn.get();
    }

    public BooleanProperty isItYourTurnProperty() {
        return isItYourTurn;
    }

    public void setIsItYourTurn(boolean isItYourTurn) {
        this.isItYourTurn.set(isItYourTurn);
    }

    public boolean isGameRunning() {
        return gameRunning.get();
    }

    public BooleanProperty gameRunningProperty() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning.set(gameRunning);
    }


    public StringProperty isItYourTurnPrintOutProperty() {
        return isItYourTurnPrintOut;
    }

    public void setIsItYourTurnPrintOut(String isItYourTurnPrintOut) {
        this.isItYourTurnPrintOut.set(isItYourTurnPrintOut);
    }
}
