package com.techelevator.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: FOR JASIR! Please look over and add to as needed for different dtos.
public class UserSnippetDto {
    private long id;
    private String username;
    private String user_image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    @Override
    public String toString() {
        return "UserSnippetDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", user_image='" + user_image + '\'' +
                '}';
    }
}
