package com.techelevator.controller.socket;

import com.techelevator.model.MessageNotification;
import com.techelevator.model.PostNotification;
import com.techelevator.model.responses.MessageOutput;
import com.techelevator.model.responses.PostOutput;
import com.techelevator.service.ForumSubscriptionManager;
import com.techelevator.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@org.springframework.stereotype.Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ForumSubscriptionManager forumSubscriptionManager;

    @MessageMapping("/secured/chat")
    public void sendSpecificChat( @Payload MessageNotification message, Principal user, @Header("simpSessionId") String sessionId) {
        MessageOutput output = new MessageOutput(message.getFrom(), message.getMessage(),new SimpleDateFormat("HH:mm").format(new Date()));
        System.out.println("message recieved");
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/secured/user/queue/specific-user", output);
    }

    @MessageMapping("secured/post")
    public void sendSpecificPost(@Payload PostNotification post, Principal user, @Header("simpSessionId") String sessionId) {
        PostOutput output = new PostOutput();
        output.setId(post.getId());
        output.setForum(post.getForum());
        output.setDescription(post.getDescription());
        output.setTitle(post.getTitle());
        output.setTime(new SimpleDateFormat("HH:mm").format(new Date()));
        System.out.println("message recieved");
        Set<String> subscribers = forumSubscriptionManager.getSubscribers(post.getForum());

        // Send notification to each subscriber
        for (String userId : subscribers) {
            simpMessagingTemplate.convertAndSendToUser(userId, "secured/queue/posts", output);
        }
    }
}