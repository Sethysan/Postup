package com.techelevator.model.responses;

public class UserResponseDto extends UserSnippetDto{
    String role;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
