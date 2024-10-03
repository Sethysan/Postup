package com.techelevator.controller;

import com.techelevator.dao.ForumsDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Forum;
import com.techelevator.model.ForumDto;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class ForumController {
    @Autowired
    private ForumsDao forumsDao;

    @Autowired
    private UserDao userDao;

    @GetMapping("/forums")
    public List<Forum> getListOfForums(@RequestParam(defaultValue="false") boolean isMostActive) {
        if(isMostActive){
            return forumsDao.getActiveForum();
        }
        return forumsDao.getForums();
    }

    @GetMapping("/forums/{forumId}")
    public Forum getForumById(@PathVariable int forumId) {
        return forumsDao.getForumById(forumId);
    }

    @GetMapping("/forums/topic")
    public List<Forum> getForumsByTopic(@RequestParam String topic) {
        return forumsDao.getForumsByTopic(topic);
    }

    @PostMapping("/forums/create")
    @PreAuthorize("isAuthenticated()")
    public void createForum(@RequestBody ForumDto forum, Principal user) {
        forumsDao.createForum(forum.getTopic(),forum.getDescription(),  user.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/forums/{id}/delete")
    public void deleteForum(@PathVariable long id, Principal user) {
        forumsDao.deleteForum(id, user.getName());
    }
}
