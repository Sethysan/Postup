package com.techelevator.model.responses;

import com.techelevator.model.PostNotification;

import java.sql.Timestamp;

public class PostOutput extends PostNotification {
    String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
