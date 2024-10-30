package com.techelevator.model.responses;

import com.techelevator.model.Forum;
import com.techelevator.model.PostSnippet;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsDto{
    Forum forum;
    List<PostSnippet> posts = new ArrayList<>();

    public void addPost(PostSnippet post){
        this.posts.add(post);
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public List<PostSnippet> getPosts() {
        return posts;
    }

    public void setPosts(List<PostSnippet> post) {
        this.posts = post;
    }
}
