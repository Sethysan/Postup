package com.techelevator.controller;

import com.techelevator.dao.*;
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
    public List<ReplyResponseDto> getRepliesByPost(@PathVariable long id){
        return replyDao.getPostThreads(id);
    }

    @GetMapping("/replies/{id}")
    public ReplyResponseDto getReplyById(@PathVariable long id){
        return replyDao.getReplyById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{id}/replies")
    public List<ReplyResponseDto> getUserReplies(@PathVariable long id){
        return replyDao.getReplyByUser(id);
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

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("replies/{Id}")
    public void deleteReply(@PathVariable long id, Principal user){
        if (checkUserRoleForReply(id, user.getName())) {
            replyDao.deleteReply(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public boolean checkUserRoleForReply(long replyId, String username) {
        boolean hasPermission = false;
        boolean isAMod = false;
        boolean isAAdmin = false;

        ReplyResponseDto reply = getReplyById(replyId);
        PostResponseDto post = postDao.getPostById(reply.getPostId());
        List<Moderation> moderator = moderationDao.getListOfModeratorsOfForum(post.getForum_id());
        User user = userDao.getUserByUsername(username);

        if (user.getAuthorities().contains("ROLE_ADMIN")) {
            isAAdmin = true;
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
