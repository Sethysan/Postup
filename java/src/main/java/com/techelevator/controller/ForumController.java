package com.techelevator.controller;

import com.techelevator.dao.ForumsDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Forum;
import com.techelevator.model.ForumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
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
        forumsDao.createForum(forum.getTopic(), forum.getDescription(), user.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/forums/{id}/delete")
    public void deleteForum(@PathVariable long id, Principal user) {
        forumsDao.deleteForum(id, user.getName());
    }

    @GetMapping("/forums/search")
    public List<Forum> searchForums(@RequestParam String searchTerm) {
        return forumsDao.getForumsBySearch(searchTerm);
    }
}
