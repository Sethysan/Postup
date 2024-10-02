package com.techelevator.controller;

import com.techelevator.dao.ReplyDao;
import com.techelevator.model.request.CreateReplyDto;
import com.techelevator.model.responses.ReplyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/api")
public class ReplyController {

    @Autowired
    private ReplyDao replyDao;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{id}/replies")
    public ReplyResponseDto createReply(@PathVariable long id, @RequestBody CreateReplyDto reply){
        return new ReplyResponseDto();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/post/{postId}/replies/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable long postId, @PathVariable long replyId, @RequestBody CreateReplyDto reply){
        return new ReplyResponseDto();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("replies/{Id}")
    public void deleteReply(@PathVariable long id){
    }
}
