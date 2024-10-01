package com.techelevator.model.responses;

import java.sql.Timestamp;
import java.util.List;

public class ReplyResponseDto {
    private long id;
    private long postId;
    private long root;
    private String description;
    private Timestamp created;
    private List<ReplyResponseDto> reply;
    private UserSnippetDto user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getRoot() {
        return root;
    }

    public void setRoot(long root) {
        this.root = root;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public UserSnippetDto getUser() {
        return user;
    }

    public void setUser(UserSnippetDto user) {
        this.user = user;
    }

    public List<ReplyResponseDto> getReply() {
        return reply;
    }

    public void setReply(List<ReplyResponseDto> reply) {
        this.reply = reply;
    }
}
