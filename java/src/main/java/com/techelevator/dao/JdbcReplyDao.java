package com.techelevator.dao;
import com.techelevator.model.request.CreateReplyDto;
import com.techelevator.model.responses.ReplyResponseDto;
import com.techelevator.model.responses.UserSnippetDto;
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
    public List<ReplyResponseDto> getReplies() {
        List<ReplyResponseDto> threads = new ArrayList<>();
        String sql = "SELECT * FROM replies JOIN users ON users.user_id = replies.user_id LEFT JOIN comment_replies ON comment_replies.reply_id = replies.reply_id ORDER BY replies.reply_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()) {
            threads = mapRowToThread(results);
        }
        return threads;
    }

    @Override
    public ReplyResponseDto getReplyById(long id) {
        ReplyResponseDto reply = new ReplyResponseDto();
        String sql = "SELECT * FROM replies JOIN users ON users.user_id = replies.user_id WHERE reply_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            reply = mapRowToReply(results);
        }
        System.out.println(reply);
        return reply;
    }

    @Override
    public List<ReplyResponseDto> getReplyByUser(long userId) {
        List<ReplyResponseDto> replies = new ArrayList<>();
        String sql = "SELECT * FROM replies WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            replies.add(mapRowToReply(results));
        }
        return replies;
    }

    @Override
    public List<ReplyResponseDto> getPostThreads(long postId) {
        List<ReplyResponseDto> threads = new ArrayList<>();
        String sql = "SELECT * FROM replies JOIN users ON users.user_id = replies.user_id LEFT JOIN comment_replies ON comment_replies.reply_id = replies.reply_id WHERE replies.post_id = ? ORDER BY replies.reply_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, postId);
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
        return getReplyById(replyId);
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
                    if(results.isLast()){
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
            UserSnippetDto user = new UserSnippetDto();
            user.setId(row.getInt("user_id"));
            user.setUsername(row.getString("username"));
            reply.setUser(user);
            return reply;
        }
    }