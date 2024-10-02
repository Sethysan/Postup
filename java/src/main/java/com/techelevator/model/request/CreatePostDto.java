package com.techelevator.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreatePostDto {
    private String title;
    private String description;
    private String image;
    private String creator_username;
    private long forum_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public long getForum_id() {
        return forum_id;
    }
}
