package com.techelevator.model.responses;

import com.techelevator.model.MessageNotification;

import java.sql.Timestamp;

public class MessageOutput extends MessageNotification {
    String time;

    public MessageOutput(){
        super();
    }

    public MessageOutput(String from, String message, String time) {
        super("", from, message);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
