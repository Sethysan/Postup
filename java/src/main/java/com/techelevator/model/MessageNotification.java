package com.techelevator.model;


import com.techelevator.model.responses.MessageOutput;

public class MessageNotification {
    String to;
    String from;
    String message;

    public MessageNotification(){}

    public MessageNotification(String to, String from, String message){
        this.to = to;
        this.from = from;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageNotification{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
