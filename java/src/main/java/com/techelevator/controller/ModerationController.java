package com.techelevator.controller;

import com.techelevator.dao.ModerationDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Authority;
import com.techelevator.model.Moderation;
import com.techelevator.model.responses.UserSnippetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ModuleReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class ModerationController {

    @Autowired
    ModerationDao moderationDao;
    @Autowired
    UserDao userDao;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/moderation/forum/{id}")
    public List<Moderation> getListOfModeratorOnForum(@PathVariable long id) {
        return moderationDao.getListOfModeratorsOfForum(id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/moderation/user") //Todo only admin should have access maybe.
    public List<Moderation> getListOfUsersModeration(Principal user) {
        return moderationDao.getModerationsByUsername(user.getName());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/moderation/forum/{id}/promote")
    public void promoteOrAddUserToMod(@PathVariable long id, @RequestBody UserSnippetDto username, Principal user) {
        boolean isMod = false;
        List<Moderation> list = moderationDao.getListOfModeratorsOfForum(id);
        Set<Authority> roles = userDao.getUserByUsername(user.getName()).getAuthorities();

        // storing the check if the current user has admin permission to make person moderator
        boolean isAdmin = false;

        for (Authority role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }

        for (Moderation mod : list) {
            if (mod.getUsername().equals(user.getName())) {
                isMod = true;
                break;
            }
        }
        // to check if they are an admin or moderator of this forum
        if (isAdmin || isMod) {
            List<Moderation> mod = new ArrayList<>();

            // to see if the user is an mod of the forum already or not
            for (Moderation moderator : list) {
                if (moderator.getUsername().equals(username)){
                    mod.add(moderator);
                }
            }
            // confirming previous comment
            if (mod.isEmpty()) {
                moderationDao.makeUserModeratorOfForum(id, username.getUsername());
            }
            else {
                //throw an exception with message or throw back message or just exception
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a Moderator of forum.");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User doesn't have permission for request.");
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/moderation/user/{id}/promote/admin")
    public void promoteUserToAdmin(@PathVariable int id, Principal user) {
        Set<Authority> rolesForCurrentUser = userDao.getUserByUsername(user.getName()).getAuthorities();
        Set<Authority> rolesForOtherUser = userDao.getUserById(id).getAuthorities();
        boolean isAdmin = false;
        boolean notAdmin = false;

        for (Authority role : rolesForCurrentUser) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        for (Authority role : rolesForOtherUser) {
            if (role.getName().equals("ROLE_ADMIN")) {
                notAdmin = true;
                break;
            }
        }


        if (isAdmin) {
            if (!userDao.getUserById(id).getAuthorities().contains("ROLE_BANNED")) {
                if (!notAdmin) {
                    //promote to admin
                    userDao.promoteUserToAdmin(id);
                }
                else {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already an Admin.");
                }
            }
            else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User is currently Banned their role cannot be changed");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admins can promote Others to Admin.");
        }


        //Both of these need to be discussed first
        //TODO make an banning user path and method
        //TODO make an promoting an user back to a user path and method
    }
}
