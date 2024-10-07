package com.techelevator.model.responses;

import java.sql.Timestamp;

public class DirectMessageResponseDto {
    long id;
    private UserSnippetDto sender;
    String message;
    Timestamp receivedOn;
    boolean read;

    public UserSnippetDto getSender() {
        return sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
