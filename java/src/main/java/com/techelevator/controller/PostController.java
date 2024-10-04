package com.techelevator.controller;

import com.techelevator.dao.ModerationDao;
import com.techelevator.dao.PostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Authority;
import com.techelevator.model.Moderation;
import com.techelevator.model.User;
import com.techelevator.model.request.CreatePostDto;
import com.techelevator.model.responses.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostDao postDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ModerationDao moderationDao;

    // public List<PostResponseDto> getPosts(long forum, String keyword, int limit, boolean sorBytPopularity)
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(@RequestParam(defaultValue="") String keyword, @RequestParam(defaultValue="-1") int limit, @RequestParam(defaultValue="") String filter, @RequestParam(defaultValue="false") boolean today, Principal principal){
        long user = -1;
        if(principal != null){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        boolean sortByPopularity = false;
        if(filter.equals("popularity")){
            sortByPopularity = true;
        }
        return postDao.getPosts(-1, user, keyword, limit, sortByPopularity, today);
    }

    @GetMapping("/forum/{id}/posts")
    public List<PostResponseDto> getPostsByForum(@PathVariable long id, @RequestParam(defaultValue="") String keyword, @RequestParam(defaultValue="-1") int limit, @RequestParam(defaultValue="") String filter, Principal principal){
        long user = -1;
        if(principal != null){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        boolean sortByPopularity = false;
        if(filter.equals("popularity")){
            sortByPopularity = true;
        }
        return postDao.getPosts(id, user, keyword, limit, sortByPopularity, false);
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPostById(@PathVariable long id, Principal principal){
        long user = -1;
        if(principal != null){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        return postDao.getPostById(id, user);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/forum/{id}/posts")
    public PostResponseDto createNewPost(@PathVariable long id, @RequestBody CreatePostDto post, Principal principal){
            post.setForum_id(id);
            post.setCreator_username(principal.getName());
            System.out.println(principal.getName());
            return postDao.createPost(post);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable long id, @RequestBody CreatePostDto post){
        return postDao.updatePost(id, post);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("posts/{id}/delete")
    public void deletePost(@PathVariable long id, Principal user) {
        if (checkUserRole(id, user.getName())) {
            postDao.deletePost(id);
        }
        else {
            //throw an exception 401 or forbidden
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/posts/{id}/upvote")
    public void upvotePost(@PathVariable long id, Principal principal) {
        if(!postDao.addVote(id, userDao.getUserByUsername(principal.getName()).getId(), 0)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/posts/{id}/upvote/unlike")
    public void unvotingLike(@PathVariable long id, Principal principal) {
        postDao.unvote(id, userDao.getUserByUsername(principal.getName()).getId(), 0);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/posts/{id}/downvote")
    public void downvotePost(@PathVariable long id, Principal principal) {
        if(!postDao.addVote(id, userDao.getUserByUsername(principal.getName()).getId(), 1)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/posts/{id}/downvote/undislike")
    public void unvotingDislike(@PathVariable long id, Principal principal) {
        postDao.unvote(id, userDao.getUserByUsername(principal.getName()).getId(),1);
    }

    public boolean checkUserRole(long postId, String username) {
        boolean hasPermission = false;
        boolean isAMod = false;
        boolean isAAdmin = false;

        PostResponseDto post = getPostById(postId, null);
        List<Moderation> moderator = moderationDao.getListOfModeratorsOfForum(post.getForum_id());
        User user = userDao.getUserByUsername(username);

        Set<Authority> roles = userDao.getUserByUsername(username).getAuthorities();

        for (Authority role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isAAdmin = true;
            }
        }

        for (Moderation mod : moderator) {
            if (mod.getUsername().equals(username)) {
                isAMod = true;
                break;
            }
        }
        if (post.getCreator_username().equals(username)){
            hasPermission = true;
        } else if (isAMod) {
            hasPermission = true;
        } else if (isAAdmin) {
            hasPermission = true;
        }
        return hasPermission;
    }
}
