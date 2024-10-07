package com.techelevator.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreateDirectMessageDto {
    @JsonIgnore
    long user;
    long sendTo;
    String message;

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getSendTo() {
        return sendTo;
    }

    public void setSendTo(long sendTo) {
        this.sendTo = sendTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
