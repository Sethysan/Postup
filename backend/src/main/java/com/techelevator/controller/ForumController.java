package com.techelevator.controller;

import com.techelevator.dao.ForumsDao;
import com.techelevator.dao.ModerationDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Authority;
import com.techelevator.model.Forum;
import com.techelevator.model.ForumDto;
import com.techelevator.model.User;
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
import java.util.Set;

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
    public List<Forum> getListOfForums(@RequestParam(defaultValue="false") boolean isMostActive, Principal principal) {
        long user = -1;
        if(principal != null && principal.getName().isBlank()){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        if(isMostActive){
            return forumsDao.getActiveForum(user);
        }
        return forumsDao.getForums(user);
    }

    @GetMapping("/forums/{forumId}")
    public Forum getForumById(@PathVariable long forumId, Principal principal) {
        long user = -1;
        if(principal != null && principal.getName().isBlank()){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        return forumsDao.getForumById(forumId, user);
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

        Set<Authority> roles = userDao.getUserByUsername(user.getName()).getAuthorities();

        // storing the check if the current user has admin permission to make person moderator
        boolean isAdmin = false;

        for (Authority role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }

        if (getForumById(id, null).getAuthor().equals(user.getName()) || isAdmin) {
            forumsDao.deleteForum(id, user.getName());
        }
        else {
            //throw an 401 or forbidden
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/forums/search")
    public List<SearchResultsDto> searchForums(@RequestParam String searchTerm, Principal principal) {
        long user = -1;
        if(principal != null ) {
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        return forumsDao.getForumsBySearch(searchTerm, user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/forums/favorites")
    public List<Forum> getFavoritedForums(Principal principal){
        long user = -1;
        if(principal != null){
            User yup = userDao.getUserByUsername(principal.getName());
            if(yup != null){
                user = yup.getId();
            }
        }
        return forumsDao.getFavoriteForums(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/forum/{id}/favorites")
    public void addFavorite(@PathVariable long id,  Principal principal){
        forumsDao.addFavorite(id, userDao.getUserByUsername(principal.getName()).getId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/forum/{id}/favorites")
    public void removeFavorite(@PathVariable long id,  Principal principal){
        forumsDao.removeFavorite(id, userDao.getUserByUsername(principal.getName()).getId());
    }
}
