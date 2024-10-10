package com.techelevator.dao;

import com.techelevator.model.Moderation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcModerationDao implements ModerationDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcModerationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Moderation> getModerationsByUsername(String name){
        List<Moderation> list = new ArrayList<>();
        String sql = "SELECT * FROM moderation WHERE username = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
        while (results.next()) {
            Moderation mod = mapRowToModeration(results);
            list.add(mod);
        }
        System.out.println("sending back to client " + list);
        return list;
    }

    public List<Moderation> getListOfModeratorsOfForum(long id){
        List<Moderation> list = new ArrayList<>();
        String sql = "SELECT * FROM moderation WHERE forum_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while (results.next()) {
            Moderation mod = mapRowToModeration(results);
            list.add(mod);
        }
        return list;
    }

    public void makeUserModeratorOfForum(long forumId, String name) {
        String sql = "INSERT INTO moderation (forum_id, username) VALUES (?,?);";
        jdbcTemplate.update(sql, forumId, name);
    }
    @Transactional
    public void removeOrDeleteModerator(long forumId, String name) {
        String sql = "DELETE FROM moderation WHERE forum_id = ? AND username = ?;";
        jdbcTemplate.update(sql, forumId, name);
    }

    public Moderation findModOfForum(long id, String username) {
        Moderation mod = null;
        String sql = "SELECT * FROM moderation WHERE forum_id = ? and username = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id, username);
        if (result.next()) {
            mod = mapRowToModeration(result);
        }
        return mod;
    }

    private Moderation mapRowToModeration(SqlRowSet rs) {
        Moderation mod = new Moderation();
        mod.setForumId(rs.getInt("forum_id"));
        mod.setUsername(rs.getString("username"));
        mod.setRole(rs.getString("role"));
        return mod;
    }

}
