package com.techelevator.dao;

import com.techelevator.model.Forum;
import com.techelevator.model.responses.SearchResultsDto;

import java.util.List;

public interface ForumsDao {

    List<Forum> getForums(long user);

    public List<Forum> getActiveForum(long user);

    Forum getForumById(long forumId, long user);

    List<Forum> getForumsByTopic(String topic);

    List<Forum> getFavoriteForums(long user);

    long createForum(String topic, String Description, String author);
    void deleteForum(long id, String name);
    List<SearchResultsDto> getForumsBySearch(String searchTerm, long user);
    public void addFavorite(long forum, long user);
    public void removeFavorite(long forum, long user);
}
