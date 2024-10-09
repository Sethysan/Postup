package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.Authority;
import com.techelevator.model.Moderation;
import com.techelevator.model.ReplyDto;
import com.techelevator.model.User;
import com.techelevator.model.request.CreateReplyDto;
import com.techelevator.model.responses.PostResponseDto;
import com.techelevator.model.responses.ReplyResponseDto;
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
@RequestMapping(path="/api")
public class ReplyController {

    @Autowired
    private ReplyDao replyDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private ForumsDao forumsDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ModerationDao moderationDao;

    @GetMapping("/posts/{id}/replies")
    public List<ReplyResponseDto> getRepliesByPost(@PathVariable long id, Principal principal){
        long user = -1;
        if(principal != null){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        System.out.println("user: " + user);
        return replyDao.getPostThreads(id, user);
    }

    @GetMapping("/replies/{id}")
    public ReplyResponseDto getReplyById(@PathVariable long id, Principal principal){
        long user = -1;
        if(principal != null){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        return replyDao.getReplyById(id, user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{id}/replies")
    public List<ReplyResponseDto> getUserReplies(@PathVariable long id, Principal principal){
        long user = -1;
        if(principal != null){
            user = userDao.getUserByUsername(principal.getName()).getId();
        }
        return replyDao.getReplyByUser(id, user);
    }

//    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{id}/replies")
    public ReplyResponseDto createReply(@PathVariable long id, @RequestBody CreateReplyDto reply, Principal principal){
        reply.setPost(id);
        reply.setUser(userDao.getUserByUsername(principal.getName()).getId());
        return replyDao.createReply(reply);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/post/{postId}/replies/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable long postId, @PathVariable long replyId, @RequestBody CreateReplyDto reply){
        return new ReplyResponseDto();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("replies/{id}")
    public void deleteReply(@PathVariable long id, Principal user){
        if (checkUserRoleForReply(id, user.getName())) {
            replyDao.deleteReply(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/replies/{id}/upvote")
    public void upvotePost(@PathVariable long id, Principal principal) {
        if(!replyDao.addVote(id, userDao.getUserByUsername(principal.getName()).getId(), true)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/replies/{id}/upvote/unlike")
    public void unvotingLike(@PathVariable long id, Principal principal) {
        replyDao.undoVote(id, userDao.getUserByUsername(principal.getName()).getId(), true);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/replies/{id}/downvote")
    public void downvotePost(@PathVariable long id, Principal principal) {
        if(!replyDao.addVote(id, userDao.getUserByUsername(principal.getName()).getId(), false)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/replies/{id}/downvote/undislike")
    public void unvotingDislike(@PathVariable long id, Principal principal) {
        replyDao.undoVote(id, userDao.getUserByUsername(principal.getName()).getId(),false);
    }

    public boolean checkUserRoleForReply(long replyId, String username) {
        boolean hasPermission = false;
        boolean isAMod = false;
        boolean isAAdmin = false;

        ReplyResponseDto reply = getReplyById(replyId, null);
        PostResponseDto post = postDao.getPostById(reply.getPostId(), -1);
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
        if (user.getId() == reply.getUser().getId()) {
            hasPermission = true;

        } else if (post.getCreator_username().equals(username)){
            hasPermission = true;

        } else if (isAMod) {
            hasPermission = true;

        } else if (isAAdmin) {
            hasPermission = true;

        }
        return hasPermission;
    }
}
