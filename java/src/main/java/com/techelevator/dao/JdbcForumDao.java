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
        String sql = "SELECT * FROM forums";

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

    public Forum getForumByTopic(String topic) {
        Forum forum = null;
        String sql = "SELECT * FROM forums WHERE topic = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, topic);

        if (result.next()) {
            forum = mapRowToForum(result);
        }
        return forum;
    }

    public void createForum(String topic, String author) {
        String sql = "INSERT INTO forums (topic, author) VALUES (?,?);";
        jdbcTemplate.update(sql, topic, author);
    }

    private Forum mapRowToForum(SqlRowSet rs) {
        Forum forum = new Forum();
        forum.setId(rs.getInt("forum_id"));
        forum.setTopic(rs.getString("topic"));
        forum.setAuthor(rs.getString("author"));
        forum.setTimeOfCreation(rs.getTimestamp("time_of_creation"));
        return forum;
    }
}
