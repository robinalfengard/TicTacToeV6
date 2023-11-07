package com.example.tictactoev6;
import javafx.scene.canvas.Canvas;

import java.util.List;
import java.util.Map;

public class GameLogic {
    FactoryMethods factoryMethods =  new FactoryMethods();

    public boolean isMoveValid(String move, Map<String, Canvas> possibleMoves)  {
        return possibleMoves.get(move).getId().equals(move);
    }

    public void removeMove(String move, Map<String, Canvas> possibleMoves){
        possibleMoves.remove(move);
    }

    public boolean winCheck(List<String> movesToCheck) {
        return factoryMethods.winningCombinations().stream()
                .anyMatch(movesToCheck::containsAll
                );
    }






}
