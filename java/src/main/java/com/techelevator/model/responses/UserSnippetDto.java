package com.techelevator.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: FOR JASIR! Please look over and add to as needed for different dtos.
public class UserSnippetDto {
    private long id;
    private String username;

    public long getId() {
        return id;
    }

    @JsonIgnore public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore public void setUsername(String username) {
        this.username = username;
    }
}
