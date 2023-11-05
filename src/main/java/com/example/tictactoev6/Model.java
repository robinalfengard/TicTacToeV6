package com.example.tictactoev6;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
    private String userInput;
    private final StringProperty messageReceived = new SimpleStringProperty("This should update");
    private final StringProperty usersOnline = new SimpleStringProperty("0");
    private final StringProperty messageSent = new SimpleStringProperty("");

    public Model(){

    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
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

    public void updateGui(String message) {
        System.out.println(usersOnline);
        System.out.println("Message to update gui with: " + message);
        Platform.runLater(() -> setMessageReceived(message));
        setUsersOnline("5");
        System.out.println(usersOnline);
    }
}
