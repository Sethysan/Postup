package com.techelevator.dao;
import com.techelevator.model.request.CreatePostDto;
import com.techelevator.model.responses.PostResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPostDao implements PostDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcPostDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PostResponseDto getPostById(long id) {
        PostResponseDto post = null;
        String sql = "SELECT * FROM posts WHERE post_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            post = mapRowToPost(results);
        }
        return post;
    }

    @Override
    public List<PostResponseDto> getPosts(long forum, String keyword, int limit, boolean sorBytPopularity, boolean today) {
        List<PostResponseDto> posts = new ArrayList<>();
        String sql = "SELECT posts.*, COUNT(replies.description) FROM posts LEFT JOIN replies ON replies.post_id = posts.post_id WHERE (posts.description ILIKE ? OR replies.description ILIKE ?) ";
        if(forum > 0){
            sql += " AND posts.forum_id = " + forum;
        }
        if(today){
            sql += " AND (CAST(posts.time_of_creation AS Date) = CURRENT_DATE OR CAST(replies.time_of_creation AS DATE) = CURRENT_DATE) ";
        }
        sql += " GROUP BY posts.post_id ";
        sql += sorBytPopularity ? " ORDER BY posts.likes - posts.dislikes DESC" : " ORDER BY post_id DESC";
        if(limit > 0){
            sql += " LIMIT " + limit;
        }
        if(!keyword.isBlank()){
            keyword = "%" + keyword;
        }
        keyword += "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, keyword, keyword);
        while(results.next()){
            posts.add(mapRowToPost(results));
        }
        return posts;
    }

    @Override
    public PostResponseDto createPost(CreatePostDto post) {
        String sql = "INSERT INTO posts(title, description, image, author, likes, dislikes, forum_id) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING post_id";
        long id = jdbcTemplate.queryForObject(sql, long.class, post.getTitle(), post.getDescription(), post.getImage(), post.getCreator_username(), 0, 0, post.getForum_Id());
        return this.getPostById(id);
    }

    @Override
    public PostResponseDto updatePost(long id, CreatePostDto post) {
        String sql = "UPDATE posts SET description = ? WHERE post_id = ?";
        jdbcTemplate.update(sql, post.getDescription(), id);
        return this.getPostById(id);
    }

    // added at transactional to turn DAO function into a transaction. Equiv of setting BEGIN TRANSACTION at the begining of a sql statement
    @Transactional
    @Override
    public void deletePost(long id) {
        String sql1 = "DELETE FROM replies WHERE post_id = ?";
        String sql2 = "DELETE FROM posts WHERE post_id = ?";
        jdbcTemplate.update(sql1, id);
        jdbcTemplate.update(sql2, id);
    }

    public void addVote(long id, int route) {
        String sql = "UPDATE posts SET likes = likes + 1 WHERE post_id = ?;";
        if (route == 1) {
            sql = "UPDATE posts SET dislikes = dislikes + 1 WHERE post_id = ?;";
        }
        jdbcTemplate.update(sql, id);
    }

    public void unvote(long id, int route) {
        String sql = "UPDATE posts SET likes = likes - 1 WHERE post_id = ?;";
        if (route == 1) {
            sql = "UPDATE posts SET dislikes = dislikes - 1 WHERE post_id = ?;";
        }
        jdbcTemplate.update(sql, id);
    }

    private PostResponseDto mapRowToPost(SqlRowSet row) {
        PostResponseDto post = new PostResponseDto();
        post.setTitle(row.getString("title"));
        post.setDescription(row.getString("description"));
        post.setDownvotes(row.getInt("dislikes"));
        post.setUpvotes(row.getInt("likes"));
        post.setCreator_username(row.getString("author"));
        post.setId(row.getLong("post_id"));
        post.setForum_id(row.getLong("forum_id"));
        post.setTitle(row.getString("title"));
        return post;
    }
}
