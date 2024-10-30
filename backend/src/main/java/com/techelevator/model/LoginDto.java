package com.techelevator.model;
/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, LoginDto represents the data a
    client must pass to the server for a login endpoint, and LoginResponseDto represents the object that's returned
    from the server to the client from a login endpoint.
 */
public class LoginDto {

   private String username;
   private String password;
   private String user_image;


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

   public String getUserImage() {
      return trimQuotes(this.user_image);
   }

   public void setUserImage(String userImage) {
      this.user_image = trimQuotes(userImage);
   }
   public String trimQuotes(String input) {
      if (input != null && input.startsWith("\"") && input.endsWith("\"")) {
         return input.substring(1, input.length() - 1);
      }
      return input;
   }

   @Override
   public String toString() {
      return "LoginDTO{" +
              "username='" + username + '\'' +
              ", password='" + password + '\'' +
              ", user_image='" + user_image + '\'' +
              '}';
   }
}
