package com.techelevator.controller;

import com.techelevator.dao.DirectMessageDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.request.CreateDirectMessageDto;
import com.techelevator.model.responses.ContactResponseDto;
import com.techelevator.model.responses.DirectMessageResponseDto;
import com.techelevator.model.responses.UserSnippetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/contacts")
@CrossOrigin
public class MessageController {

    @Autowired
    DirectMessageDao directMessageDao;
    @Autowired
    UserDao userDao;

    @GetMapping("/{id}/messages")
    public List<DirectMessageResponseDto> getMessagesByUser(@PathVariable long id, Principal principal){
        List<DirectMessageResponseDto> messages = directMessageDao.getMessagesByContact(userDao.getUserByUsername(principal.getName()).getId(), id);
        if(!messages.isEmpty() && !messages.get(messages.size()-1).isRead()){
            directMessageDao.markRead(id);
        }
        return messages;
    }

    @GetMapping("/")
    public List<ContactResponseDto> getContacts(Principal principal){
        return directMessageDao.getContacts(userDao.getUserByUsername(principal.getName()).getId());
    }

    @PostMapping("/{id}")
    public DirectMessageResponseDto createMessage(@RequestBody CreateDirectMessageDto dm, @PathVariable long id, Principal principal){
        dm.setUser(userDao.getUserByUsername(principal.getName()).getId());
        dm.setSendTo(id);
        return directMessageDao.createMessage(dm);
    }

    @PutMapping("/{id}/messages/{message}")
    public DirectMessageResponseDto updateMessage(@RequestBody CreateDirectMessageDto dm, @PathVariable long message){
        return directMessageDao.updateMessage(message, dm);
    }

    @DeleteMapping("/{id}/messages/{message}")
    public void deleteMessage(@PathVariable long message){
        directMessageDao.deleteMessage(message);
    }
}
