package com.techelevator.dao;
import com.techelevator.model.request.CreatePostDto;
import com.techelevator.model.responses.PostResponseDto;
import java.util.List;

public interface PostDao {

    public PostResponseDto getPostById(long id, long user);
    public List<PostResponseDto> getPosts(long forum, long user, String keyword, int limit, boolean sorBytPopularity, boolean today);
    public PostResponseDto createPost(CreatePostDto post);
    public PostResponseDto updatePost(long id, CreatePostDto post);
    public void deletePost(long id);

//route means the path to take in the method in the jdbc for example 1 is add for dislike and 0 is for dislike.
    boolean addVote(long postId, long userId, int route);
    void unvote(long postId, long userId, int route);
}
