package com.techelevator.dao;

import com.techelevator.model.request.CreateReplyDto;
import com.techelevator.model.responses.ReplyResponseDto;
import com.techelevator.model.responses.ThreadResponseDto;

import java.util.List;

public interface ReplyDao {
    public List<ReplyResponseDto> getReplies(long userId);
    public ReplyResponseDto getReplyById(long id, long userId);
    public List<ReplyResponseDto> getReplyByUser(long userId, long loggedInUserId);
    public ReplyResponseDto createReply(CreateReplyDto reply);
    public ReplyResponseDto updateReply(long id, CreateReplyDto reply);
    public void deleteReply(long replyId);
    public List<ReplyResponseDto> getPostThreads(long postId, long userId);
    public boolean addVote(long replyId, long userId, boolean route);
    public void undoVote(long replyId, long userId, boolean route);
}
