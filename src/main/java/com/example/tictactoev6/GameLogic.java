package com.example.tictactoev6;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLogic {
    FactoryMethods factoryMethods =  new FactoryMethods();
    private final List<String> userMoves = new ArrayList<>();
    private final List<String> opponentMoves = new ArrayList<>();

    public boolean isMoveValid(String move, Map<String, Canvas> possibleMoves, List<String> copyOfMoves)  {
        String moveToTry = possibleMoves.get(move).getId();
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

    public List<String> getUserMoves() {
        return userMoves;
    }

    public List<String> getOpponentMoves() {
        return opponentMoves;
    }
}
