package com.techelevator.dao;

import com.techelevator.model.request.CreateDirectMessageDto;
import com.techelevator.model.responses.ContactResponseDto;
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
        String sql = "SELECT DISTINCT * FROM direct_message JOIN users AS sender ON sender.user_id = direct_message.sent_from JOIN users AS receiver ON receiver.user_id = direct_message.sent_to WHERE (direct_message.sent_to = ? OR direct_message.sent_from = ?) AND (direct_message.sent_to = ? OR direct_message.sent_from = ?) ORDER BY time_sent DESC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user, user, contact, contact);
        while(results.next()){
            dm.add(mapRowToDirectMessage(results));
        }
        return dm;
    }

    @Override
    public List<ContactResponseDto> getContacts(long user) {
        List<ContactResponseDto> contacts = new ArrayList<>();
        String sql = "SELECT users.*, COUNT(CASE WHEN direct_message.has_read = false THEN 1 END) AS unread_messages, MAX(direct_message.message_id) AS latest_message, MAX(direct_message.time_sent) AS latest_time, COUNT(direct_message.message_id) AS total_messages\n" +
                "FROM users\n" +
                "LEFT JOIN direct_message ON direct_message.sent_from = users.user_id OR direct_message.sent_to = users.user_id\n" +
                "WHERE (direct_message.sent_to = ? OR direct_message.sent_from = ?) AND users.user_id != ?\n" +
                "GROUP BY users.user_id\n" +
                "ORDER BY unread_messages DESC, latest_message DESC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user, user, user);
        while(results.next()){
            contacts.add(mapRowToContact(results));
        }
        return contacts;
    }

    @Override
    public DirectMessageResponseDto createMessage(CreateDirectMessageDto message) {
        String sql = "INSERT INTO direct_message (sent_to, sent_from, message) VALUES (?, ?, ?) RETURNING message_id";
        long messageId = jdbcTemplate.queryForObject(sql, long.class, message.getSendTo(), message.getUser(), message.getMessage());
        return this.getMessage(messageId);
    }

    public void markRead(long id){
        String sql = "UPDATE direct_message SET has_read = ? WHERE sent_from = ?";
        jdbcTemplate.update(sql, true, id);
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
        DirectMessageResponseDto dm = new DirectMessageResponseDto();
        dm.setId(row.getLong("message_id"));
        dm.setMessage(row.getString("message"));
        UserSnippetDto user = new UserSnippetDto();
        user.setUsername(row.getString("username"));
        user.setId(row.getLong("sent_from"));
        dm.setSender(user);
        dm.setReceivedOn(row.getTimestamp("time_sent"));
        dm.setRead(row.getBoolean("has_read"));
        return dm;
    }

    private ContactResponseDto mapRowToContact(SqlRowSet row) {
        ContactResponseDto user = new ContactResponseDto();
        user.setUsername(row.getString("username"));
        user.setId(row.getLong("user_id"));
        user.setLatest(row.getTimestamp("latest_time"));
        user.setUnread(row.getInt("unread_messages"));
        user.setTotalMessages(row.getInt("total_messages"));
        return user;
    }
}
