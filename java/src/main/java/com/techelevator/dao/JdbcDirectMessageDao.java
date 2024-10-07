package com.techelevator.dao;

import com.techelevator.model.request.CreateDirectMessageDto;
import com.techelevator.model.responses.DirectMessageResponseDto;
import com.techelevator.model.responses.UserSnippetDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDirectMessageDao implements DirectMessageDao{
    private JdbcTemplate jdbcTemplate;

    JdbcDirectMessageDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DirectMessageResponseDto getMessage(long messageId) {
        DirectMessageResponseDto dm = null;
        String sql = "SELECT * FROM direct_message JOIN users AS sender ON sender.user_id = direct_message.sent_from JOIN users AS receiver ON receiver.user_id = direct_message.sent_to WHERE direct_message.message_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, messageId);
        if(results.next()){
            dm = mapRowToDirectMessage(results);
        }
        return dm;
    }

    @Override
    public List<DirectMessageResponseDto> getMessagesByContact(long user, long contact) {
        List<DirectMessageResponseDto> dm = new ArrayList<>();
        String sql = "SELECT * FROM direct_message JOIN users AS sender ON sender.user_id = direct_message.sent_from JOIN users AS receiver ON receiver.user_id = direct_message.sent_to WHERE (direct_message.sent_to = ? OR direct_message.sent_from = ?) AND (direct_message.sent_to = ? OR direct_message.sent_from = ?) ORDER BY time_sent DESC";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user, user, contact, contact);
        while(results.next()){
            dm.add(mapRowToDirectMessage(results));
        }
        return dm;
    }

    @Override
    public List<UserSnippetDto> getContacts(long user) {
        List<UserSnippetDto> contacts = new ArrayList<>();
        String sql = "SELECT * FROM users JOIN direct_message WHERE sent_from = users.user_id OR sent_to = users.user_id WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);
        while(results.next()){
            contacts.add(mapRowToContact(results));
        }
        return contacts;
    }

    @Override
    public DirectMessageResponseDto createMessage(CreateDirectMessageDto message) {
        String sql = "INSERT INTO direct_message (sent_to, sent_from, message) VALUES (?, ?, ?) RETURNING message_id";
        long messageId = jdbcTemplate.queryForObject(sql, long.class, message.getsentTo(), message.getUser(), message.getMessage());
        return this.getMessage(messageId);
    }

    @Override
    public DirectMessageResponseDto updateMessage(long messageId, CreateDirectMessageDto message) {
        String sql = "UPDATE direct_message SET message = ?";
        jdbcTemplate.update(sql, message.getMessage());
        return this.getMessage(messageId);
    }

    @Override
    public void deleteMessage(long messageId) {
        String sql = "DELETE FROM direct_message WHERE message_id = ?";
        jdbcTemplate.update(sql, messageId);
    }

    private DirectMessageResponseDto mapRowToDirectMessage(SqlRowSet row) {

    }

    private UserSnippetDto mapRowToContact(SqlRowSet results) {
    }
}
