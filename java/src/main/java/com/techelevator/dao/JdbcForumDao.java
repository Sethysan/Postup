package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Forum;
import org.springframework.data.relational.core.sql.From;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcForumDao implements ForumsDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcForumDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Forum> getForums(){
        List<Forum> list = new ArrayList<>();
        String sql = "SELECT * FROM forums;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Forum forum = mapRowToForum(results);
                list.add(forum);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return list;
    }

    public Forum getForumById(int forumId) {
        Forum forum = null;
        String sql = "SELECT * FROM forums WHERE forum_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, forumId);

        if (result.next()) {
            forum = mapRowToForum(result);
        }
        return forum;
    }

    public List<Forum> getForumsByTopic(String topic) {
        List<Forum> list = new ArrayList<>();
        String sql = "SELECT * FROM forums WHERE topic = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, topic);

        while (result.next()) {
            Forum forum = mapRowToForum(result);
            list.add(forum);
        }
        return list;
    }

    public void createForum(String topic,String Description,String author) {
        String sql = "INSERT INTO forums (topic,description , author) VALUES (?,?,?);";
        jdbcTemplate.update(sql, topic,Description , author);
    }

    public void deleteForum(long id) {
        String sql = "DELETE FROM comment_replies WHERE parent_id IN (SELECT reply_id FROM replies " +
                "WHERE post_id IN (SELECT post_id FROM post WHERE forum_id = ?));";
        String sql1 = "DELETE FROM replies WHERE post_id IN (SELECT post_id FROM post WHERE forum_id = ?);";
        String sql2 = "DELETE FROM posts WHERE forum_id = ?;";
        String sql3 = "DELETE FROM forum WHERE forum_id = ?;";
    }

    private Forum mapRowToForum(SqlRowSet rs) {
        Forum forum = new Forum();
        forum.setId(rs.getInt("forum_id"));
        forum.setTopic(rs.getString("topic"));
        forum.setDescription(rs.getString("description"));
        forum.setAuthor(rs.getString("author"));
        forum.setTimeOfCreation(rs.getTimestamp("time_of_creation"));
        return forum;
    }
}
