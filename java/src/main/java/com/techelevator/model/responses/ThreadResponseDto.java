package com.techelevator.model.responses;

import java.util.List;

public class ThreadResponseDto {
    ReplyResponseDto root;
    List<ReplyResponseDto> replies;

    public ReplyResponseDto getRoot() {
        return root;
    }

    public void setRoot(ReplyResponseDto root) {
        this.root = root;
    }



    public List<ReplyResponseDto> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyResponseDto> replies) {
        this.replies = replies;
    }
}
