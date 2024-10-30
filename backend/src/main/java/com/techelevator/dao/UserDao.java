package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import com.techelevator.model.responses.UserResponseDto;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(RegisterUserDto user);

    void promoteUserToAdmin(int userId);
    void banUser(long id);
    void unbanUser(long id);
    List<User> getUsersFiltered();
    List<UserResponseDto> getUsersForAdmins();
    UserResponseDto getUserForAdmin(String username);
    UserResponseDto getUserForAdmin(int id);

    void updateUserImage(long id, String user_image);



}
