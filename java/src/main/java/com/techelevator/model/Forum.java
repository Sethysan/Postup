package com.techelevator.model;

import java.sql.Timestamp;

public class Forum {
    private long id;
    private String topic;
    private String description;
    private String author;
    private Timestamp timeOfCreation;
    private Timestamp mostRecentPost;
    boolean isFavorited;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Timestamp timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public Timestamp getMostRecentPost() {
        return mostRecentPost;
    }

    public void setMostRecentPost(Timestamp mostRecentMost) {
        this.mostRecentPost = mostRecentMost;
    }
}
