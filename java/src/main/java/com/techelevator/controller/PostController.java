package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.model.request.CreatePostDto;
import com.techelevator.model.responses.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostDao postDao;

    // public List<PostResponseDto> getPosts(long forum, String keyword, int limit, boolean sorBytPopularity)
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(@RequestParam(defaultValue="") String keyword, @RequestParam(defaultValue="-1") int limit, @RequestParam(defaultValue="") String filter){
        boolean sortByPopularity = false;
        if(filter.equals("popularity")){
            sortByPopularity = true;
        }
        return postDao.getPosts(-1, keyword, limit, sortByPopularity);
    }

    @GetMapping("forum/{id}/posts")
    public List<PostResponseDto> getPostsByForum(@PathVariable long id, @RequestParam(defaultValue="") String keyword, @RequestParam(defaultValue="-1") int limit, @RequestParam(defaultValue="") String filter){
        boolean sortByPopularity = false;
        if(filter.equals("popularity")){
            sortByPopularity = true;
        }
        return postDao.getPosts(id, keyword, limit, sortByPopularity);
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPostById(@PathVariable long id){
        return postDao.getPostById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/forum/{id}/posts")
    public PostResponseDto createNewPost(@PathVariable long id, @RequestBody CreatePostDto post, Principal principal){
            post.setForum_id(id);
            post.setCreator_username(principal.getName());
            return postDao.createPost(post);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable long id, @RequestBody CreatePostDto post){
        return postDao.updatePost(id, post);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @DeleteMapping("posts/{id}/delete")
    public void deletePost(@PathVariable long id){
        postDao.deletePost(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("posts/{id}/upvote")
    public void upvotePost(@PathVariable long id) {
        postDao.addVote(id, 0);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("posts/{id}/upvote/unlike")
    public void unvotingLike(@PathVariable long id) {
        postDao.unvote(id, 0);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("posts/{id}/downvote")
    public void downvotePost(@PathVariable long id) {
        postDao.addVote(id, 1);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("posts/{id}/downvote/undislike")
    public void unvotingDislike(@PathVariable long id) {
        postDao.unvote(id, 1);
    }
}
