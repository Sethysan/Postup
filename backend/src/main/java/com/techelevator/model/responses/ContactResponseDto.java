package com.techelevator.model.responses;

import java.sql.Timestamp;

public class ContactResponseDto extends UserSnippetDto {
    int unread;
    Timestamp latest;
    int totalMessages;

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public Timestamp getLatest() {
        return latest;
    }

    public void setLatest(Timestamp latest) {
        this.latest = latest;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }
}
