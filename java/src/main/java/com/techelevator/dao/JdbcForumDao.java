package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Forum;
import com.techelevator.model.PostSnippet;
import com.techelevator.model.responses.SearchResultsDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcForumDao implements ForumsDao {


    private JdbcTemplate jdbcTemplate;

    public JdbcForumDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Forum> getForums(long user) {
        List<Forum> list = new ArrayList<>();
        String sql = "SELECT forums.*, COUNT(favorite_forums.forum_id) AS favorited FROM forums LEFT JOIN favorite_forums ON favorite_forums.forum_id = forums.forum_id AND favorite_forums.user_id = ? GROUP BY forums.forum_id ORDER BY forum_id DESC";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);
            while (results.next()) {
                Forum forum = mapRowToForum(results);
                list.add(forum);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return list;
    }

    public List<Forum> getActiveForum(long user) {
        List<Forum> list = new ArrayList<>();
        String sql = "SELECT forums.*, MAX(posts.time_of_creation) AS most_recent_post, COUNT(favorite_forums.forum_id) AS favorited FROM forums LEFT JOIN favorite_forums ON favorite_forums.forum_id = forums.forum_id AND favorite_forums.user_id = ? JOIN posts ON posts.forum_id = forums.forum_id GROUP BY forums.forum_id ORDER BY most_recent_post DESC LIMIT 5";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);
        while (results.next()) {
            list.add(mapRowToForum(results));
        }
        return list;
    }

    public Forum getForumById(long forumId, long user) {
        Forum forum = null;
        String sql = "SELECT forums.*, COUNT(favorite_forums.forum_id) AS favorited FROM forums LEFT JOIN favorite_forums ON favorite_forums.forum_id = forums.forum_id AND favorite_forums.user_id = ? WHERE forums.forum_id = ? GROUP BY forums.forum_id ;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user, forumId);

        if (result.next()) {
            forum = mapRowToForum(result);
        }
        return forum;
    }

    public List<Forum> getForumsByTopic(String topic) {
        List<Forum> list = new ArrayList<>();
        String sql = "SELECT * FROM forums, COUNT(favorite_forums.forum_id) AS favorited FROM forums LEFT JOIN favorite_forums ON favorite_forums.forum_id = forums.forum_id AND favorite_forums.user_id = ? WHERE topic = ? GROUP BY forums.forum_id";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, topic);

        while (result.next()) {
            Forum forum = mapRowToForum(result);
            list.add(forum);
        }
        return list;
    }

    @Override
    public List<Forum> getFavoriteForums(long user) {
        List<Forum> favorites = new ArrayList<>();
        String sql = "SELECT forums.*, \n" +
                "       favorite_forums.user_id, \n" +
                "       MAX(posts.time_of_creation) AS most_recent_post, \n" +
                "       COUNT(favorite_forums.forum_id) AS favorited \n" +
                "FROM forums \n" +
                "LEFT JOIN favorite_forums ON favorite_forums.forum_id = forums.forum_id AND favorite_forums.user_id = ?\n" +
                "LEFT JOIN posts ON posts.forum_id = forums.forum_id \n" +
                "WHERE favorite_forums.user_id = ?\n" +
                "GROUP BY forums.forum_id, favorite_forums.user_id \n" +
                "ORDER BY most_recent_post;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user, user);

        while(results.next()){
            favorites.add(mapRowToForum(results));
        }
        return favorites;
    }

    public long createForum(String topic, String description, String author) {
        String sql = "INSERT INTO forums (topic,description , author) VALUES (?,?,?) RETURNING forum_id;";
        try {
            long id = jdbcTemplate.queryForObject(sql, long.class, topic, description, author);
            return id;
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }

    }

    @Transactional
    public void deleteForum(long id, String name) {
        Forum forum = getForumById(id, -1);

        String sql = "DELETE FROM comment_replies WHERE parent_id IN (SELECT reply_id FROM replies " +
                "WHERE post_id IN (SELECT post_id FROM post WHERE forum_id = ?));";
        String sql0 = "DELETE FROM moderation WHERE forum_id = ?;";
        String sql1 = "DELETE FROM replies WHERE post_id IN (SELECT post_id FROM post WHERE forum_id = ?);";
        String sql2 = "DELETE FROM posts WHERE forum_id = ?;";
        String sql3 = "DELETE FROM forum WHERE forum_id = ?;";

        jdbcTemplate.update(sql, id);
        jdbcTemplate.update(sql0, id);
        jdbcTemplate.update(sql1, id);
        jdbcTemplate.update(sql2, id);
        jdbcTemplate.update(sql3, id);
    }

    @Override
    public List<SearchResultsDto> getForumsBySearch(String searchTerm) {
        List<SearchResultsDto> list = new ArrayList<>();

        String sql = "SELECT forums.*, posts.description AS post_description, posts.post_id, posts.title, \n" +
                "COUNT(favorite_forums.forum_id) AS favorited FROM forums LEFT JOIN favorite_forums ON favorite_forums.forum_id = forums.forum_id AND favorite_forums.user_id = ? \n" +
                "LEFT JOIN posts ON posts.forum_id = forums.forum_id \n" +
                "AND (posts.description ILIKE ? OR posts.title ILIKE ?) \n" +
                "WHERE (forums.description ILIKE ? OR forums.topic ILIKE ?) \n" +
                "OR (posts.description ILIKE ? OR posts.title ILIKE ?) \n" +
                "ORDER BY posts.description DESC, forums.forum_id;";

        searchTerm = "%" + searchTerm + "%";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, searchTerm, searchTerm, searchTerm, searchTerm, searchTerm, searchTerm);
        list = mapRowToSearchResults(result);
        return list;
    }

    @Override
    public void addFavorite(long forum, long user) {
        String sql = "INSERT INTO favorite_forums(forum_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, forum, user);
    }

    @Override
    public void removeFavorite(long forum, long user) {
        String sql = "DELETE FROM favorite_forums WHERE forum_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, forum, user);
    }

    private Forum mapRowToForum(SqlRowSet rs) {
        Forum forum = new Forum();
        forum.setId(rs.getInt("forum_id"));
        forum.setTopic(rs.getString("topic"));
        forum.setDescription(rs.getString("description"));
        forum.setAuthor(rs.getString("author"));
        forum.setTimeOfCreation(rs.getTimestamp("time_of_creation"));
        forum.setFavorited(rs.getInt("favorited") > 0);
        return forum;
    }

    private List<SearchResultsDto> mapRowToSearchResults(SqlRowSet row) {
        Map<Long, SearchResultsDto> map = new HashMap<>();
        List<SearchResultsDto> results = new ArrayList<>();
        SearchResultsDto match = new SearchResultsDto();
        PostSnippet snippet = new PostSnippet();
        Long forumId;

        while (row.next()) {
            // get forum_id
            forumId = row.getLong("forum_id");
            if (map.get(forumId) == null) {
                match.setForum(mapRowToForum(row));
                map.put(forumId, match);
                results.add(match);
                match = new SearchResultsDto();
            }
            if (row.getString("post_description") != null) {
                snippet.setDescription(row.getString("post_description"));
                snippet.setTitle(row.getString("title"));
                snippet.setId((row.getLong("post_id")));
                map.get(forumId).addPost(snippet);
                snippet = new PostSnippet();
            }

        }
        return results;
    }
}
