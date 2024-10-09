package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, CredentialsDto represents the data a client must
    pass to the server for a login endpoint, and TokenDto represents the object that's returned from the server
    to the client from a login endpoint.
 */
public class RegisterUserDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

    @JsonProperty("user_image")
    private String user_image;
    @NotEmpty(message = "Please select a role for this user.")
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserImage() {
        return user_image;
    }

    public void setUserImage(String user_image) {
        this.user_image = user_image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", user_image='" + user_image + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
