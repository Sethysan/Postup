package com.techelevator.dao;
import com.techelevator.model.request.CreateReplyDto;
import com.techelevator.model.responses.ReplyResponseDto;
import com.techelevator.model.responses.UserSnippetDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class JdbcReplyDao implements ReplyDao {
    JdbcTemplate jdbcTemplate;

    JdbcReplyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReplyResponseDto> getReplies(long userId) {
        List<ReplyResponseDto> threads = new ArrayList<>();
        String sql = "SELECT replies.*, comment_replies.*, users.username, COUNT(reply_upvote.reply_id) AS likes, COUNT(reply_downvote.reply_id) AS dislikes, COUNT(upvote.user_id) AS upvotes_from_user, COUNT(downvote.user_id) AS downvotes_from_user\n" +
                "FROM replies \n" +
                "JOIN users ON users.user_id = replies.user_id LEFT JOIN comment_replies ON comment_replies.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_upvote ON reply_upvote.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_downvote ON replies.reply_id = reply_downvote.reply_id \n" +
                "LEFT JOIN users AS upvote ON reply_upvote.user_id = upvote.user_id AND upvote.user_id = ?\n" +
                "LEFT JOIN users AS downvote ON reply_upvote.user_id = downvote.user_id AND upvote.user_id = ?\n" +
                "GROUP BY replies.reply_id, comment_replies.parent_id, comment_replies.reply_id, users.username\n" +
                "ORDER BY replies.reply_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        if (results.next()) {
            threads = mapRowToThread(results);
        }
        return threads;
    }

    @Override
    public ReplyResponseDto getReplyById(long id, long userId) {
        ReplyResponseDto reply = new ReplyResponseDto();
        String sql = "SELECT replies.*, comment_replies.*,users.username, COUNT(reply_upvote.reply_id) AS likes, COUNT(reply_downvote.reply_id) AS dislikes, COUNT(upvote.user_id) AS upvotes_from_user, COUNT(downvote.user_id) AS downvotes_from_user\n" +
                "FROM replies \n" +
                "JOIN users ON users.user_id = replies.user_id LEFT JOIN comment_replies ON comment_replies.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_upvote ON reply_upvote.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_downvote ON replies.reply_id = reply_downvote.reply_id \n" +
                "LEFT JOIN users AS upvote ON reply_upvote.user_id = upvote.user_id AND upvote.user_id = ?\n" +
                "LEFT JOIN users AS downvote ON reply_upvote.user_id = downvote.user_id AND upvote.user_id = ?\n" +
                "WHERE replies.reply_id = ? " +
                "GROUP BY replies.reply_id, comment_replies.parent_id, comment_replies.reply_id, users.username\n" +
                "ORDER BY replies.reply_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId, id);
        if (results.next()) {
            reply = mapRowToReply(results);
        }
        System.out.println(reply);
        return reply;
    }

    @Override
    public List<ReplyResponseDto> getReplyByUser(long userId, long loggedInUserId) {
        List<ReplyResponseDto> replies = new ArrayList<>();
        String sql = "SELECT replies.*, comment_replies.*, users.username, COUNT(reply_upvote.reply_id) AS likes, COUNT(reply_downvote.reply_id) AS dislikes, COUNT(upvote.user_id) AS upvotes_from_user, COUNT(downvote.user_id) AS downvotes_from_user\n" +
                "FROM replies \n" +
                "JOIN users ON users.user_id = replies.user_id LEFT JOIN comment_replies ON comment_replies.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_upvote ON reply_upvote.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_downvote ON replies.reply_id = reply_downvote.reply_id \n" +
                "LEFT JOIN users AS upvote ON reply_upvote.user_id = upvote.user_id AND upvote.user_id = ?\n" +
                "LEFT JOIN users AS downvote ON reply_upvote.user_id = downvote.user_id AND upvote.user_id = ?\n" +
                "WHERE replies.user_id = ? " +
                "GROUP BY replies.reply_id, comment_replies.parent_id, comment_replies.reply_id, users.username\n" +
                "ORDER BY replies.reply_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, loggedInUserId, loggedInUserId, userId);
        while (results.next()) {
            replies.add(mapRowToReply(results));
        }
        return replies;
    }

    @Override
    public List<ReplyResponseDto> getPostThreads(long postId, long userId) {
        List<ReplyResponseDto> threads = new ArrayList<>();
        String sql = "SELECT replies.*, comment_replies.*, users.username, COUNT(reply_upvote.reply_id) AS likes, COUNT(reply_downvote.reply_id) AS dislikes, COUNT(upvote.user_id) AS upvotes_from_user, COUNT(downvote.user_id) AS downvotes_from_user\n" +
                "FROM replies \n" +
                "JOIN users ON users.user_id = replies.user_id LEFT JOIN comment_replies ON comment_replies.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_upvote ON reply_upvote.reply_id = replies.reply_id \n" +
                "LEFT JOIN reply_downvote ON replies.reply_id = reply_downvote.reply_id \n" +
                "LEFT JOIN users AS upvote ON reply_upvote.user_id = upvote.user_id AND upvote.user_id = ?\n" +
                "LEFT JOIN users AS downvote ON reply_upvote.user_id = downvote.user_id AND upvote.user_id = ?\n" +
                "WHERE replies.post_id = ?\n" +
                "GROUP BY replies.reply_id, comment_replies.parent_id, comment_replies.reply_id, users.username \n" +
                "ORDER BY replies.reply_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId, postId);
        threads = mapRowToThread(results);
        return threads;
    }

    @Transactional
    @Override
    public ReplyResponseDto createReply(CreateReplyDto reply) {
        String sql = "INSERT INTO replies (description, post_id, user_id) VALUES (?, ?, ?) RETURNING reply_id";
        long replyId = jdbcTemplate.queryForObject(sql, long.class, reply.getDescription(), reply.getPost(), reply.getUser());
        if(reply.getRespondsTo() > 0){
            sql = "INSERT INTO comment_replies (parent_id, reply_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, reply.getRespondsTo(), replyId);
        }
        return getReplyById(replyId, -1);
    }

    @Override
    public ReplyResponseDto updateReply(long id, CreateReplyDto reply) {
        return null;
    }

    @Transactional
    public void deleteReply(long replyId) {
       String sql = "UPDATE replies SET user_id = 1, description = 'removed' WHERE reply_id = ?";
       jdbcTemplate.update(sql, replyId);
    }

    public boolean addVote(long postId, long replyId, int route) {
        String sql = "INSERT INTO post_upvote(post_id, user_id) VALUES (?, ?)";
        if (route == 1) {
            sql = "INSERT INTO post_downvote(post_id, user_id) VALUES (?, ?)";
        }
        try {
            jdbcTemplate.update(sql, postId, replyId);
        }
        catch (DuplicateKeyException e){
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

    private List<ReplyResponseDto> mapRowToThread(SqlRowSet results) {
        Map<Long, ReplyResponseDto> replyMap = new HashMap<>();
        List<ReplyResponseDto> rootReplies = new ArrayList<>();
        // store first result
        ReplyResponseDto reply = new ReplyResponseDto();
//        // store it map for future reference
//        replyMap.put(reply.getId(), reply);
//        // store the root replies that are direct replies to the post
//        rootReplies.add(reply);

        // we are iterating through results
        while (results.next()) {
            // Get parent_id of the current reply
            Long parent = results.getLong("parent_id");
            // initialize the reply
            reply = mapRowToReply(results);
            // place the reply in the map
            replyMap.put(reply.getId(), reply);
                // Check if the parent is null (this means it's a root reply)
            if (parent <= 0) {
                    // if so, this is a root reply. add to root
                rootReplies.add(reply);
                continue;
            }
                    // fetch reply from map if there is a parent
                ReplyResponseDto parentReply = replyMap.get(parent);
                if (parentReply != null) {
                    parentReply.addReplies(reply);
                    if(results.isLast() && !rootReplies.contains(parentReply)){
                        rootReplies.add(parentReply);
                    }
                }
//            System.out.println(reply);
            }
        return rootReplies;
        }

        private ReplyResponseDto mapRowToReply (SqlRowSet row){
            ReplyResponseDto reply = new ReplyResponseDto();
            reply.setId(row.getInt("reply_id"));
            reply.setDescription(row.getString("description"));
            reply.setCreated(row.getTimestamp("time_of_creation"));
            reply.setPostId(row.getInt("post_id"));
            reply.setRoot(row.getLong("parent_id"));
            reply.setUpvotes(row.getInt("likes"));
            reply.setHasUpvoted(row.getInt("upvotes_from_user") == 1);
            reply.setDownvotes(row.getInt("dislikes"));
            reply.setHasDownvoted(row.getInt("downvotes_from_user") == 1);
            UserSnippetDto user = new UserSnippetDto();
            user.setId(row.getInt("user_id"));
            user.setUsername(row.getString("username"));
            reply.setUser(user);
            return reply;
        }
    }