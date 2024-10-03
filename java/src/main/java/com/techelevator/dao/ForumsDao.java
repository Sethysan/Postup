package com.techelevator.dao;

import com.techelevator.model.Forum;

import java.util.List;

public interface ForumsDao {

    List<Forum> getForums();

    Forum getForumById(long forumId);

    List<Forum> getForumsByTopic(String topic);

    long createForum(String topic, String Description, String author);
    void deleteForum(long id, String name);
}
