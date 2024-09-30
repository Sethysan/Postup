package com.techelevator.controller;

import com.techelevator.dao.ForumsDao;
import com.techelevator.model.Forum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class ForumController {
    @Autowired
    private ForumsDao forumsDao;

    @GetMapping("/forums")
    public List<Forum> getListOfForums() {
        return forumsDao.getForums();
    }

    @PostMapping("/forums/create")
    public void createForum(@RequestBody String forum, Principal user) {
        forumsDao.createForum(forum, user.getName());
    }
}
