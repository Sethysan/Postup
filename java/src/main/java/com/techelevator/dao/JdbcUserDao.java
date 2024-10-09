package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import com.techelevator.service.ImageDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private ImageDownloader imageDownloader;

    @Autowired

    public JdbcUserDao(JdbcTemplate jdbcTemplate, ImageDownloader imageDownloader) {
        this.jdbcTemplate = jdbcTemplate;
        this.imageDownloader = imageDownloader;
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT user_id, username, password_hash, role, user_image FROM users WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash, role, user_image FROM users WHERE user_id <> 1";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        User user = null;
        String sql = "SELECT * FROM users WHERE username = LOWER(TRIM(?));";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if (rowSet.next()) {
                user = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User createUser(RegisterUserDto user) {
        User newUser = null;
        String insertUserSql = "INSERT INTO users (username, password_hash, role, user_image) values (LOWER(TRIM(?)), ?, ?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(user.getPassword());
        String ssRole = user.getRole().toUpperCase().startsWith("ROLE_") ? user.getRole().toUpperCase() : "ROLE_" + user.getRole().toUpperCase();

        try {
            int newUserId = jdbcTemplate.queryForObject(insertUserSql, int.class, user.getUsername(), password_hash, ssRole, user.getUserImage());
            newUser = getUserById(newUserId);
            if (user.getUserImage() != null && !user.getUserImage().isEmpty()) {
                String destinationFolder = "src/main/resources/images/";
                String fileName = "user_" + newUserId + ".jpg";
                try {
                    imageDownloader.saveImageFromUrl(newUser.getUserImage(), destinationFolder, fileName);
                } catch (RuntimeException e) {
                    System.err.println("Failed to download image for user " + user.getUsername() + ": " + e.getMessage());
                }
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newUser;
    }

    @Override
    public void updateUserImage(long id, String userImage) {
        String sql = "UPDATE users SET user_image = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(sql, userImage, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    public void promoteUserToAdmin(int userId) {
        String sql = "UPDATE users SET role = 'ROLE_ADMIN' WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void banUser(long id) {
        String sql = "UPDATE users SET role = 'ROLE_BANNED' WHERE user_id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public void unbanUser(long id) {
        String sql = "UPDATE users SET role = 'ROLE_USER' WHERE user_id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public List<User> getUsersFiltered() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash, role, user_image FROM users WHERE role = 'ROLE_USER';";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    public List<User> getUsersForAdmins() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash, role, user_image FROM users WHERE role = 'ROLE_USER' OR role = 'ROLE_BANNED';";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                list.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return list;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setUserImage(rs.getString("user_image"));
        user.setActivated(true);
        return user;
    }
}
