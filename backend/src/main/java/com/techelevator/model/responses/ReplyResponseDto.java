package com.techelevator.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplyResponseDto {
    private long id;
    private long postId;
    private long root;
    private String description;
    private Timestamp created;
    private List<ReplyResponseDto> replies = new ArrayList<>();
    private UserSnippetDto user;
    private int upvotes;
    private int downvotes;
    private boolean hasUpvoted;
    private boolean hasDownvoted;
    private int replyCount;

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

    public List<ReplyResponseDto> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyResponseDto> replies) {
        this.replies = replies;
    }

    public UserSnippetDto getUser() {
        return user;
    }

    public void setUser(UserSnippetDto user) {
        this.user = user;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public boolean isHasUpvoted() {
        return hasUpvoted;
    }

    public void setHasUpvoted(boolean hasUpvoted) {
        this.hasUpvoted = hasUpvoted;
    }

    public boolean isHasDownvoted() {
        return hasDownvoted;
    }

    public void setHasDownvoted(boolean hasDownvoted) {
        this.hasDownvoted = hasDownvoted;
    }

    @JsonIgnore public void addReplies(ReplyResponseDto reply) {
        this.replies.add(reply);
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "ReplyResponseDto{" +
                "id=" + id +
                ", postId=" + postId +
                ", root=" + root +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", replies=" + replies +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplyResponseDto that = (ReplyResponseDto) o;
        return id == that.id && postId == that.postId && root == that.root && upvotes == that.upvotes && downvotes == that.downvotes && hasUpvoted == that.hasUpvoted && hasDownvoted == that.hasDownvoted && Objects.equals(description, that.description) && Objects.equals(created, that.created) && Objects.equals(replies, that.replies) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, root, description, created, replies, user, upvotes, downvotes, hasUpvoted, hasDownvoted);
    }
}
