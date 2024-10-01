package com.techelevator.dao;

import com.techelevator.model.request.CreatePostDto;
import com.techelevator.model.responses.PostResponseDto;

import java.util.*;

//post_id SERIAL,
//        description varchar(1000),
//        image varchar(255),
//        likes int,
//        dislikes int,
//        author varchar,
//        forum_id int,

// for now ignore images


public interface PostDao {

    public PostResponseDto getPostById(long id);
    public List<PostResponseDto> getPosts(long forum, String keyword, int limit, boolean sorBytPopularity);
    public PostResponseDto createPost(CreatePostDto post);
    public PostResponseDto updatePost(long id, CreatePostDto post);
    public void deletePost(long id);
}
