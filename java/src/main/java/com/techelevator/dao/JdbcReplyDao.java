package com.techelevator.dao;

import com.techelevator.model.request.CreateReplyDto;
import com.techelevator.model.responses.ReplyResponseDto;
import com.techelevator.model.responses.ThreadResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReplyDao implements ReplyDao{

    @Override
    public List<ReplyResponseDto> getReplies() {
        return List.of();
    }

    @Override
    public List<ReplyResponseDto> getRepliesByThread(long threadId) {
        return List.of();
    }

    @Override
    public ReplyResponseDto getReplyById(long id) {
        return null;
    }

    @Override
    public ReplyResponseDto createReply(CreateReplyDto reply) {
        return null;
    }

    @Override
    public ReplyResponseDto updateReply(long id, CreateReplyDto reply) {
        return null;
    }

    @Override
    public List<ReplyResponseDto> getPostThreads(long postId) {
        List<ReplyResponseDto> threads = new ArrayList<>();
    }
}
