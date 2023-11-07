package com.example.tictactoev6;

import java.util.List;

public class FactoryMethods {
    public List<List<String>> winningCombinations(){
        return List.of(
                //Horizontal
                List.of("box1", "box2", "box3"),
                List.of("box4", "box5", "box6"),
                List.of("box7", "box8", "box9"),

                //Vertical
                List.of("box1", "box4", "box7"),
                List.of("box2", "box5", "box8"),
                List.of("box3", "box6", "box9"),

                // Diagonal
                List.of("box1", "box5", "box9"),
                List.of("box3", "box5", "box7")
        );
    }
}
