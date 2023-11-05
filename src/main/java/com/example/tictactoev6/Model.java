package com.example.tictactoev6;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Map;

public class Model {
    private final StringProperty messageReceived = new SimpleStringProperty("This should update");
    private final StringProperty usersOnline = new SimpleStringProperty("0");
    private final StringProperty messageSent = new SimpleStringProperty("");
    private  Map<String, Canvas> boxMap;


    public Model(){

    }

    public void updateGui(String message) {
        System.out.println(usersOnline);
        System.out.println("Message to update gui with: " + message);
        Platform.runLater(() -> setMessageReceived(message));
        setUsersOnline("5");
        System.out.println(usersOnline);
    }

    public void makeMove(String boxId) {
        boxSelector(boxId);
    }

    public void MakeOpponentMove(String message) {
       boxSelector(message);
    }

    public void boxSelector(String id) {
        System.out.println(id);
        Canvas canvas = boxMap.get(id);
        if (canvas != null) {
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.RED); // Set the fill color
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            });
            // Colorize the canvas as needed
            // canvas.getGraphicsContext2D().setFill(Color.RED);
            // canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }



    public String getMessageReceived() {
        return messageReceived.get();
    }


    public StringProperty messageReceivedProperty() {
        return messageReceived;
    }

    public void setMessageReceived(String messageReceived) {
        this.messageReceived.set(messageReceived);
    }

    public String getUsersOnline() {
        return usersOnline.get();
    }

    public StringProperty usersOnlineProperty() {
        return usersOnline;
    }

    public void setUsersOnline(String usersOnline) {
        this.usersOnline.set(usersOnline);
    }

    public String getMessageSent() {
        return messageSent.get();
    }

    public StringProperty messageSentProperty() {
        return messageSent;
    }

    public void setMessageSent(String messageSent) {
        this.messageSent.set(messageSent);
    }


    public void setBoxMap(Map<String, Canvas> boxMap) {
        this.boxMap = boxMap;
    }
}
