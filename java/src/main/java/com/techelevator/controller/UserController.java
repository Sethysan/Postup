package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.Authority;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserDao userDao;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/filtered")
    public List<User> getUsersFiltered() {
        return userDao.getUsersFiltered();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/user/{id}/ban")
    public void banUser(@PathVariable long id, Principal user) {
        Set<Authority> roles = userDao.getUserByUsername(user.getName()).getAuthorities();
        boolean isAdmin = false;

        for (Authority role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            userDao.banUser(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to ban this User");
        }
    }
}
