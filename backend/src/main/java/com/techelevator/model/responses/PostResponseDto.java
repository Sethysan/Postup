package com.techelevator.model.responses;

import java.sql.Timestamp;

public class PostResponseDto {

    private long id;
    private long forum_id;
    private String title;
    private String description;
    private String image;
    private int upvotes;
    private int downvotes;
    private String creator_username;
    private boolean hasUpvoted;
    private boolean hasDownvoted;
    private Timestamp timeOfCreation;
    private String creator_image;
    private int replyCount;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getForum_id() {
        return forum_id;
    }

    public void setForum_id(long forum) {
        this.forum_id = forum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCreator_username() {
        return creator_username;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Timestamp timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getCreator_image() {
        return creator_image;
    }

    public void setCreator_image(String creator_image) {
        this.creator_image = creator_image;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "PostResponseDto{" +
                "id=" + id +
                ", forum_id=" + forum_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                ", creator_username='" + creator_username + '\'' +
                ", creator_image='" + creator_image + '\'' +
                ", hasUpvoted=" + hasUpvoted +
                ", hasDownvoted=" + hasDownvoted +
                '}';
    }


}
