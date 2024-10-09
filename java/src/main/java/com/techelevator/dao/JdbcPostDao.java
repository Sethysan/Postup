package com.techelevator.dao;

import com.techelevator.model.request.CreatePostDto;
import com.techelevator.model.responses.PostResponseDto;
import com.techelevator.service.ImageDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPostDao implements PostDao {
    private JdbcTemplate jdbcTemplate;
    private ImageDownloader imageDownloader;

    @Autowired
    public JdbcPostDao(JdbcTemplate jdbcTemplate, ImageDownloader imageDownloader) {
        this.jdbcTemplate = jdbcTemplate;
        this.imageDownloader = imageDownloader;
    }

    @Override
    public PostResponseDto getPostById(long id, long user) {
        PostResponseDto post = null;
//        todo: add user image
//        added user_image!
        String sql = "SELECT posts.*,\n" +
                "users.user_image, COUNT(post_upvote.post_id) AS likes,\n" +
                "COUNT(post_downvote.post_id) AS dislikes,\n" +
                "COUNT(upvote.user_id) AS upvotes_from_user,\n" +
                "COUNT(downvote.user_id) AS downvotes_from_user\n" +
                "FROM posts\n" +
                //     Join to get user_image from the author
                "LEFT JOIN users ON posts.author = users.username\n" +
                "LEFT JOIN post_upvote ON posts.post_id = post_upvote.post_id \n" +
                "LEFT JOIN post_downvote ON posts.post_id = post_downvote.post_id\n" +
                "LEFT JOIN users AS upvote ON post_upvote.user_id = upvote.user_id AND upvote.user_id = ?\n" +
                "LEFT JOIN users AS downvote ON post_downvote.user_id = downvote.user_id AND downvote.user_id = ?\n" +
                "LEFT JOIN replies ON posts.post_id = replies.post_id\n" + // Fetch comments related to the post"
                "WHERE posts.post_id = ?\n" +
                "GROUP BY posts.post_id, users.user_image;"; //Add users.user_image to the GROUP BY clause

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user, user, id);
        if (results.next()) {
            post = mapRowToPost(results);
        }
        System.out.println("postbyid: " + post);
        return post;
    }

    @Override
    public List<PostResponseDto> getPosts(long forum, long user, String keyword, int limit, boolean sortByPopularity, boolean today) {
        List<PostResponseDto> posts = new ArrayList<>();
//        todo: add user_image
//        added user_image!
        String sql = "SELECT posts.*, users.user_image,\n" +
                "COUNT(replies.description) AS reply_count,\n" +
                "COUNT(post_upvote.post_id) AS likes,\n" +
                "COUNT(post_downvote.post_id) AS dislikes,\n" +
                "COUNT(upvote.user_id) AS upvotes_from_user,\n" +
                "COUNT(downvote.user_id) AS downvotes_from_user\n" +
                "FROM posts\n" +
                "LEFT JOIN users ON posts.author = users.username\n" + //join users table to get user_image
                "LEFT JOIN replies ON replies.post_id = posts.post_id LEFT JOIN post_upvote ON\n" +
                " posts.post_id = post_upvote.post_id\n" +
                "LEFT JOIN post_downvote ON posts.post_id = post_downvote.post_id\n" +
                "LEFT JOIN users AS upvote ON post_upvote.user_id = upvote.user_id AND upvote.user_id = ? \n" +
                "LEFT JOIN users AS downvote ON post_downvote.user_id = downvote.user_id AND downvote.user_id = ?\n" +
                "WHERE (posts.description ILIKE ? OR replies.description ILIKE ?)";
        if (forum > 0) {
            sql += " AND posts.forum_id = " + forum;
        }
        if (today) {
            sql += " AND (CAST(posts.time_of_creation AS Date) = CURRENT_DATE\n" +
                    "OR CAST(replies.time_of_creation AS DATE) = CURRENT_DATE\n" +
                    "OR CAST(post_upvote.time_of_creation AS DATE) = CURRENT_DATE\n" + "" +
                    "OR CAST(post_downvote.time_of_creation AS DATE) = CURRENT_DATE)";
        }
        sql += " GROUP BY posts.post_id, users.user_image "; //added user_image
        sql += sortByPopularity ? " ORDER BY COUNT(post_upvote.post_id) - COUNT(post_downvote.post_id) DESC" : " ORDER BY post_id DESC";
        if (limit > 0) {
            sql += " LIMIT " + limit;
        }
        if (!keyword.isBlank()) {
            keyword = "%" + keyword;
        }
        keyword += "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user, user, keyword, keyword);
        while (results.next()) {
            posts.add(mapRowToPost(results));
        }
        System.out.println(posts);
        return posts;
    }

    @Override
    public PostResponseDto createPost(CreatePostDto post) {
        String sql = "INSERT INTO posts(title, description, image, author, forum_id) VALUES (?, ?, ?, ?, ?) RETURNING post_id";
        long id = jdbcTemplate.queryForObject(sql, long.class, post.getTitle(), post.getDescription(), post.getImage(), post.getCreator_username(), post.getForum_Id());

        // Save the image to the file system
        if (post.getImage() != null && !post.getImage().isEmpty()) {
            String destinationFolder = "src/main/resources/images/";
            String fileName = "post_" + id + ".jpg";
            imageDownloader.saveImageFromUrl(post.getImage(), destinationFolder, fileName);
        }
        return this.getPostById(id, -1);
    }

    @Override
    public PostResponseDto updatePost(long id, CreatePostDto post) {
        String sql = "UPDATE posts SET title = ?, description = ?, image = ? WHERE post_id = ?;";

        jdbcTemplate.update(sql, post.getTitle(), post.getDescription(), post.getImage(), id);

        // Save the image to the file system
        if (post.getImage() != null && !post.getImage().isEmpty()) {
            String destinationFolder = "src/main/resources/images";
            String fileName = "post_" + id + ".jpg";
            imageDownloader.saveImageFromUrl(post.getImage(), destinationFolder, fileName);
        }
        sql = "SELECT * FROM users WHERE user_name = ?;";
        long user = -1;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, post.getCreator_username());
        if (results.next()) {
            user = results.getLong("user_id");
        }
        return this.getPostById(id, user);
    }

    // added at transactional to turn DAO function into a transaction. Equiv of setting BEGIN TRANSACTION at the begining of a sql statement
    @Transactional
    @Override
    public void deletePost(long id) {
        String sql = "DELETE FROM comment_replies WHERE parent_id IN (SELECT reply_id FROM replies WHERE post_id = ?);";
        String sql1 = "DELETE FROM replies WHERE post_id = ?";
        String sql2 = "DELETE FROM posts WHERE post_id = ?";
        jdbcTemplate.update(sql, id);
        jdbcTemplate.update(sql1, id);
        jdbcTemplate.update(sql2, id);
    }

    public boolean addVote(long postId, long replyId, int route) {
        String sql = "INSERT INTO post_upvote(post_id, user_id) VALUES (?, ?)";
        if (route == 1) {
            sql = "INSERT INTO post_downvote(post_id, user_id) VALUES (?, ?)";
        }
        try {
            jdbcTemplate.update(sql, postId, replyId);
//            todo: add new exception
        } catch (DuplicateKeyException e) {
            return false;
        }
        return true;
    }

    public void unvote(long postId, long replyId, int route) {
        String sql = "DELETE FROM post_upvote WHERE post_id = ? AND user_id = ?";
        if (route == 1) {
            sql = "DELETE FROM post_downvote WHERE post_id = ? AND user_id = ?";
        }
        jdbcTemplate.update(sql, postId, replyId);
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
        post.setHasUpvoted(row.getInt("upvotes_from_user") == 1);
        post.setHasDownvoted(row.getInt("downvotes_from_user") == 1);
        post.setImage(row.getString("image"));
        post.setTimeOfCreation(row.getTimestamp("time_of_creation"));
        post.setCreator_image(row.getString("user_image"));
        return post;
    }
}
