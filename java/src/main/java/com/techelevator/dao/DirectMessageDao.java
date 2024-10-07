package com.techelevator.dao;

import com.techelevator.model.request.CreateDirectMessageDto;
import com.techelevator.model.responses.DirectMessageResponseDto;
import com.techelevator.model.responses.UserSnippetDto;

import java.util.List;

public interface DirectMessageDao {
    public DirectMessageResponseDto getMessage(long messageId);
    public List<DirectMessageResponseDto> getMessagesByContact(long user, long contact);
    public List<UserSnippetDto> getContacts(long user);
    public DirectMessageResponseDto createMessage(CreateDirectMessageDto message);
    public DirectMessageResponseDto updateMessage(long messageId, CreateDirectMessageDto message);
    public void deleteMessage(long messageId);
}
