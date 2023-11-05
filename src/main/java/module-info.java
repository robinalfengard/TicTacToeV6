module com.example.tictactoev6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tictactoev6 to javafx.fxml;
    exports com.example.tictactoev6;
}