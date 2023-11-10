package com.example.tictactoev6;
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
    //todo nice to haves:
    // disable startServer efter att den är gjord

    GameLogic gameLogic;
    FactoryMethods factoryMethods = new FactoryMethods();
    private  int opponentScore = 0;
    private  int userScore = 0;
    private final StringProperty winningMessageProperty = new SimpleStringProperty("Waiting for other players to join");
    private final StringProperty opponentScorePrintout = new SimpleStringProperty("Opponent score: " + opponentScore);
    private final StringProperty userScorePrintout = new SimpleStringProperty("Your score: " + userScore);
    private final StringProperty isItYourTurnPrintOut = new SimpleStringProperty("");
    private final BooleanProperty isServerRunning = new SimpleBooleanProperty(false);
    private final BooleanProperty haveJoined = new SimpleBooleanProperty(false);
    private final BooleanProperty isItYourTurn = new SimpleBooleanProperty(true);
    private final BooleanProperty gameRunning = new SimpleBooleanProperty(false);

    public Model(){
        gameLogic = new GameLogic(this);
        gameLogic.initializeAvailableMoves();
    }


    public void makeMove(String boxId) {
            Platform.runLater(() -> {
                if (gameLogic.isMoveValid(boxId, gameLogic.getAvailableMoves()) && isIsItYourTurn()) {
                    boxSelector(boxId, Color.BLUE);
                    gameLogic.getUserMoves().add(boxId);
                    gameLogic.removeMove(boxId, gameLogic.getAvailableMoves());
                    setIsItYourTurn(false);
                    updateTurnInfo();
                    System.out.println("Is it your turn after move: " + isIsItYourTurn());
                } else{
                    System.out.println("Not valid");
                }
                if(isGameRunning())
                    gameLogic.isGameOver();
            });
    }

    public void makeOpponentMove(String boxReceived) {
        Platform.runLater(() -> {
        if(gameLogic.isMoveValid(boxReceived, gameLogic.getAvailableMoves())) {
            boxSelector(boxReceived, Color.RED);
            gameLogic.getOpponentMoves().add(boxReceived);
            gameLogic.removeMove(boxReceived, gameLogic.getAvailableMoves());
            setIsItYourTurn(true);
            updateTurnInfo();
            System.out.println("Is it your turn after opponent move: " + isIsItYourTurn());
        }
        if(isGameRunning())
            gameLogic.isGameOver();
        });
    }

    public void userWin() {
        setWinningMessage("You won!");
        userScore += 1;
        setUserScorePrintout("Your score: " + userScore);
        disableAllMoves();
    }

    public void opponentWin() {
        setWinningMessage("Opponent won!");
        opponentScore +=1;
        setOpponentScorePrintout("Opponent score: " + opponentScore);
        disableAllMoves();
    }
    public void tie(){
        setWinningMessage("It's a tie!");
    }

    // ok
    public void boxSelector(String id, Color color) {
        Canvas canvas = gameLogic.getBoxmap().get(id);
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
        gameLogic.getAvailableMoves().clear();
    }

    private void updateTurnInfo(){
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
            gameLogic.getBoxmap().forEach((box, canvas) -> {
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
        gameLogic.initializeAvailableMoves();
        setGameRunning(true);
    }




    // GETTER AND SETTER



    public StringProperty winningMessageProperty() {
        return winningMessageProperty;
    }

    public void setWinningMessage(String message) {
        winningMessageProperty.set(message);
    }

    public StringProperty userScorePrintoutProperty() {
        return userScorePrintout;
    }

    public void setUserScorePrintout(String userScorePrintout) {
        this.userScorePrintout.set(userScorePrintout);
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


    public void setIsItYourTurn(boolean isItYourTurn) {
        this.isItYourTurn.set(isItYourTurn);
    }

    public boolean isGameRunning() {
        return gameRunning.get();
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



    public boolean isIsServerRunning() {
        return isServerRunning.get();
    }

    public BooleanProperty isServerRunningProperty() {
        return isServerRunning;
    }

    public void setIsServerRunning(boolean isServerRunning) {
        this.isServerRunning.set(isServerRunning);
    }

    public boolean isHaveJoined() {
        return haveJoined.get();
    }

    public BooleanProperty haveJoinedProperty() {
        return haveJoined;
    }

    public void setHaveJoined(boolean haveJoined) {
        this.haveJoined.set(haveJoined);
    }
}
