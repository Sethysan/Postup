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
    @GetMapping("/moderation/user")
    public List<Moderation> getListOfUsersModeration(Principal user) {
        return moderationDao.getModerationsByUsername(user.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/moderation/user/{id}")
    public List<Moderation> getListOfUsersModeration(@PathVariable int id) {
        return moderationDao.getModerationsByUsername(userDao.getUserById(id).getUsername());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/moderation/forum/{id}/promote")
    public void promoteOrAddUserToMod(@PathVariable long id, @RequestBody UserSnippetDto username, Principal user) {
        Moderation check = moderationDao.findModOfForum(id, user.getName());
        String role = userDao.getUserForAdmin(user.getName()).getRole();

        if (userDao.getUserForAdmin(user.getName()).getRole().equals("ROLE_ADMIN") || check != null) {
            if(moderationDao.findModOfForum(id, username.getUsername()) == null) moderationDao.makeUserModeratorOfForum(id, username.getUsername());
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a Moderator of forum.");
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User doesn't have permission for request.");
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/moderation/user/{id}/promote/admin")
    public void promoteUserToAdmin(@PathVariable int id, Principal user) {
        Set<Authority> rolesForUserToPromote = userDao.getUserById(id).getAuthorities();
        if (!rolesForUserToPromote.contains("ROLE_BANNED")) {
            if (!userDao.getUserForAdmin((id)).getRole().equals("ROLE_ADMIN")) userDao.promoteUserToAdmin(id);
            else throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already an Admin.");
        } else throw new ResponseStatusException(HttpStatus.CONFLICT, "User is currently Banned their role cannot be changed");
    }
}
