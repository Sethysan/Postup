package com.techelevator.dao;

import com.techelevator.model.Moderation;

import java.util.List;

public interface ModerationDao {

    List<Moderation> getModerationsByUsername(String name);

    List<Moderation> getListOfModeratorsOfForum(long id);

    void makeUserModeratorOfForum(long forumId,String name);

    void removeOrDeleteModerator(long forumId, String name);
}
