package com.techelevator.dao;

import com.techelevator.model.Forum;

import java.util.List;

public interface ForumsDao {

    List<Forum> getForums();

    Forum getForumById(int forumId);

    Forum getForumByTopic(String topic);

    void createForum(String topic, String author);
}
