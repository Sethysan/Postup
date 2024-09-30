package com.techelevator.controller;

import com.techelevator.dao.ForumsDao;
import com.techelevator.model.Forum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class ForumController {

    private ForumsDao forumsDao;

    public ForumController(ForumsDao forumsDao) {
        this.forumsDao = forumsDao;
    }

    @GetMapping("/forums")
    public List<Forum> getListOfForums() {
        return forumsDao.getForums();
    }

    @PostMapping("/forums")
    public void createForum(@RequestBody String forum, Principal user) {
        forumsDao.createForum(forum, user.getName());
    }
}
