package com.app.core.services;

import com.app.core.exceptions.AccountException;
import com.app.core.models.Following;
import com.app.core.repositories.FollowDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FollowService {
    private final FollowDAO followDAO;

    @Autowired
    public FollowService(FollowDAO followDAO) {this.followDAO = followDAO;}

    public void followAccount(Following following) throws AccountException {
        try {

            boolean isAlreadyFollowing = followDAO.isAlreadyFollowing(following.getUserA(), following.getUserB()) > 0;

            if (isAlreadyFollowing) {
                return;
            }

            following.setId(UUID.randomUUID().toString());
            following.setFollowedOn(LocalDateTime.now());
            followDAO.save(following);
        } catch (RuntimeException e) {
            throw new AccountException("Error following account");
        }
    }

    public void unfollowAccount(Following unfollow) throws AccountException {
        // To be filled soon
    }
}
