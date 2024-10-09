package com.techelevator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ForumSubscriptionManager {
    JdbcTemplate jdbcTemplate;
    private Map<String, Set<String>> forumSubscribers = new HashMap<>();

    ForumSubscriptionManager(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        String sql = "SELECT * FROM favorite_forums ORDER BY forum_id, user_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        this.forumSubscribers = mapForumSubscribers(results);
    }

    public void subscribe(String forumId, String userId) {
        forumSubscribers.computeIfAbsent(forumId, k -> new HashSet<>()).add(userId);
    }

    public Set<String> getSubscribers(String forumId) {
        return forumSubscribers.getOrDefault(forumId, new HashSet<>());
    }

    private Map<String, Set<String>> mapForumSubscribers(SqlRowSet row) {
        Map<String, Set<String>> subsribers = new HashMap<>();
        Set<String> users;
        String forum = "";
        String temp = "";

        while(row.next()){
            forum = "" + row.getLong("forum_id");
            if(forum.equals(temp)){
                subsribers.get(forum).add("" + row.getLong("user_id"));
            }
            else {
                subsribers.put(forum, new HashSet<>());
            }
            temp = forum;
        }
        System.out.println(subsribers);
        return subsribers;
    }
}
