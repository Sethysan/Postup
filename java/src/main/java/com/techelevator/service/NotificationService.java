package com.techelevator.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNewPostMessage(String message) {
        messagingTemplate.convertAndSend("/topic/posts/new", message);
    }

    public void sendUserSubscriptionMessage(String message) {
        messagingTemplate.convertAndSend("/topic/user/subscriptions", message);
    }
}

