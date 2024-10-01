package com.techelevator.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreatePostDto {
    private String description;
    private String creator_username;
    private long forum_id;

    public String getDescription() {
        return description;
    }

    public String getCreator_username() {
        return creator_username;
    }

    public long getForum_Id() {
        return forum_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
    }

    @JsonIgnore
    public void setForum_id(long forum) {
        this.forum_id = forum;
    }
}
