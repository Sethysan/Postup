package com.techelevator.model.responses;

public class PostResponseDto {

    private long id;
    private long forum_id;
    private String title;
    private String description;
    private int upvotes;
    private int downvotes;
    private String creator_username;

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

    @Override
    public String toString() {
        return "PostResponseDto{" +
                "id=" + id +
                ", forum_id=" + forum_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                ", creator_username='" + creator_username + '\'' +
                '}';
    }
}
