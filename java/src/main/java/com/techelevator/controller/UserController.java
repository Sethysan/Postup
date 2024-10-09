package com.techelevator.controller;

import com.techelevator.dao.ModerationDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Authority;
import com.techelevator.model.Moderation;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ModerationDao moderationDao;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/filtered/{id}")
    public List<User> getUsersFiltered(@PathVariable long id) {
        List<User> list = userDao.getUsersFiltered();
        List<User> filteredList = new ArrayList<>();

        for (User user : list) {
            if (moderationDao.findModOfForum(id, user.getUsername()) == null) {
                filteredList.add(user);
            }
        }
        return filteredList;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/admin")
    public List<User> getUsersToPromoteOrBan() {
        return userDao.getUsersForAdmins();
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

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/user/{id}/unban")
    public void unbanUser(@PathVariable long id, Principal user) {
        Set<Authority> roles = userDao.getUserByUsername(user.getName()).getAuthorities();
        boolean isAdmin = false;

        for (Authority role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            userDao.unbanUser(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to unban this User!");
        }
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/{id}/upload-image")
    public void uploadUserImage(@PathVariable long id, @RequestParam("image") MultipartFile image, Principal user) {
        User currentUser = userDao.getUserByUsername(user.getName());
        if (currentUser.getId() == id || currentUser.getAuthorities().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN"))) {
            try {
                // Save the file locally or upload to cloud storage
                String fileName = saveImage(image);
                // Update the user's image URL in the database
                userDao.updateUserImage(id, fileName);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading image");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to upload an image for this user.");
        }
    }
    private String saveImage(MultipartFile image) {
        // Logic to save the image file and return its URL or file path
        String fileName = "path/to/save/" + image.getOriginalFilename();
        // Save the file using image.transferTo(new File(fileName));
        return fileName;
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/user/{id}/image")
    public void updateUserImage(@PathVariable long id, @RequestBody String userImage, Principal user) {
        User currentUser = userDao.getUserByUsername(user.getName());
        if (currentUser.getId() == id || currentUser.getAuthorities().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN"))) {
            userDao.updateUserImage(id, userImage);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to update this user's image.");
        }
    }
}
