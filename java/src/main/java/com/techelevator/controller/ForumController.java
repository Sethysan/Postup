package com.techelevator.controller;

import com.techelevator.dao.ForumsDao;
import com.techelevator.dao.ModerationDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Forum;
import com.techelevator.model.ForumDto;
import com.techelevator.model.responses.SearchResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ForumController {
    @Autowired
    private ForumsDao forumsDao;
    @Autowired
    private ModerationDao moderationDao;
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
    public Forum getForumById(@PathVariable long forumId) {
        return forumsDao.getForumById(forumId);
    }

    @GetMapping("/forums/topic")
    public List<Forum> getForumsByTopic(@RequestParam String topic) {
        return forumsDao.getForumsByTopic(topic);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forums/create")
    @PreAuthorize("isAuthenticated()")
    public void createForum(@RequestBody ForumDto forum, Principal user) {
        long id = forumsDao.createForum(forum.getTopic(), forum.getDescription(), user.getName());
        if (id != -1) {
            moderationDao.makeUserModeratorOfForum(id , user.getName());
        }
        else {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/forums/{id}/delete")
    public void deleteForum(@PathVariable long id, Principal user) {
        if (getForumById(id).getAuthor().equals(user.getName()) || userDao.getUserByUsername(user.getName()).getAuthorities().contains("ROLE_ADMIN")) {
            forumsDao.deleteForum(id, user.getName());
        }
        else {
            //throw an 401 or forbidden
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/forums/search")
    public List<SearchResultsDto> searchForums(@RequestParam String searchTerm) {
        return forumsDao.getForumsBySearch(searchTerm);
    }


}
