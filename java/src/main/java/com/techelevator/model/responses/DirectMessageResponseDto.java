package com.techelevator.model.responses;

import java.sql.Timestamp;

public class DirectMessageResponseDto {
    private UserSnippetDto sender;
    String message;
    Timestamp receivedOn;

    public UserSnippetDto getSender() {
        return sender;
    }

    public void setSender(UserSnippetDto sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(Timestamp receivedOn) {
        this.receivedOn = receivedOn;
    }
}
