package com.techelevator.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreateReplyDto {
    private String description;
    private long post;
    private long respondsTo = -1;
    private long user;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRespondsTo() {
        return respondsTo;
    }

    public void setRespondsTo(long respondsTo) {
        this.respondsTo = respondsTo;
    }

    public long getUser() {
        return user;
    }

    @JsonIgnore public void setUser(long user) {
        this.user = user;
    }

    public long getPost() {
        return post;
    }

    public void setPost(long post) {
        this.post = post;
    }
}
