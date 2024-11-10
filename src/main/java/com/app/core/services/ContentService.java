package com.app.core.services;

import com.app.core.models.content.Content;
import com.app.core.models.content.LikedBy;
import com.app.core.repositories.ContentDAO;
import com.app.core.repositories.LikedByDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class ContentService {

    private final ContentDAO contentDAO;
    private final LikedByDAO likedByDAO;

    @Autowired
    public ContentService(ContentDAO contentDAO, LikedByDAO likedByDAO) { this.contentDAO = contentDAO;
        this.likedByDAO = likedByDAO;
    }

    public void createContent(Content content) {
        content.setId(UUID.randomUUID().toString());
        content.setCreatedOn(LocalDateTime.now());
        contentDAO.save(content);
    }

    public List<Content> getContentByUsername(String username) {
        return null;
    }

    public List<Content> getFeedForUser(String username) {
        return contentDAO.getFeed(username);
    }

    public void likeContent(String username, String contentId) {
        var post = contentDAO.getById(contentId);
        var likedBy = likedByDAO.getLiked(username, contentId);

        if (likedBy.isPresent()) {
            post.setLikeCount(post.getLikeCount() - 1);
            likedByDAO.delete(likedBy.get());
        } else {
            var liked = LikedBy.builder().likedOn(LocalDateTime.now()).username(username).contentId(contentId).build();
            likedByDAO.save(liked);

            post.setLikeCount(post.getLikeCount() + 1);
            contentDAO.save(post);
        }
    }

}
