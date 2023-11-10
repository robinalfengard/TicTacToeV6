package com.example.tictactoev6;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLogic {
    FactoryMethods factoryMethods =  new FactoryMethods();
    private final List<String> userMoves = new ArrayList<>();
    private final List<String> opponentMoves = new ArrayList<>();
    private List<String> availableMoves;
    Model model;
    private Map<String, Canvas> boxmap;

    public GameLogic(Model model) {
        this.model = model;
    }

    public void initializeAvailableMoves() {
        availableMoves = factoryMethods.getAvailableMoves();
    }

    public boolean isMoveValid(String move,  List<String> copyOfMoves)  {
        String moveToTry = boxmap.get(move).getId();
        return copyOfMoves.contains(moveToTry);
    }

    public void removeMove(String move, List<String> availableMoves){
        availableMoves.remove(move);
    }

    public boolean winCheck(List<String> movesToCheck) {
        return factoryMethods.winningCombinations().stream()
                .anyMatch(movesToCheck::containsAll
                );
    }

    public void isGameOver() {
        if (winCheck(getOpponentMoves())) {
            model.setGameRunning(false);
            model.opponentWin();
        } else if (winCheck(getUserMoves())) {
            model.setGameRunning(false);
            model.userWin();
        } else if (boxmap.isEmpty()) {
            model.setGameRunning(false);
            model.tie();
        }
    }

    public List<String> getUserMoves() {
        return userMoves;
    }

    public List<String> getOpponentMoves() {
        return opponentMoves;
    }

    public List<String> getAvailableMoves() {
        return availableMoves;
    }

    public void setBoxMap(Map<String, Canvas> boxMap) {
        this.boxmap = boxMap;
    }

    public Map<String, Canvas> getBoxmap() {
        return boxmap;
    }
}
