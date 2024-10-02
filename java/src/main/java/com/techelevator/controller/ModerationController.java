package com.techelevator.controller;

import com.techelevator.dao.ModerationDao;
import com.techelevator.model.Moderation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ModuleReader;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class ModerationController {

    @Autowired
    ModerationDao moderationDao;


    @GetMapping("/moderation/forum/{id}")
    public List<Moderation> getListOfModeratorOnForum(@PathVariable long id) {
        return moderationDao.getListOfModeratorsOfForum(id);
    }

    @GetMapping("/moderation/user") //Todo only admin should have access maybe.
    public List<Moderation> getListOfUsersModeration(Principal user) {
        return moderationDao.getModerationsByUsername(user.getName());
    }

    @PostMapping("/moderation/forum/{id}/promote") //possible issue of getting id because this is for the when someone creates a forum
    public void promoteOrAddUserToMod(@PathVariable long id, Principal user) {
        moderationDao.makeUserModeratorOfForum(id, user.getName());
    }
}
