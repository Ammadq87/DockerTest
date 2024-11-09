package com.app.core.services;

import com.app.core.exceptions.AccountException;
import com.app.core.models.follow.FollowPair;
import com.app.core.models.follow.FollowerInfo;
import com.app.core.repositories.FollowDAO;
import com.app.core.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class FollowService {
    private static final Logger log = LoggerFactory.getLogger(FollowService.class);
    private final FollowDAO followDAO;

    @Autowired
    public FollowService(FollowDAO followDAO) {this.followDAO = followDAO;}

    public List<FollowerInfo> getFollowers(String userId) {
        Utils.checkNull(userId, "Invalid or no userId provided", AccountException.class);
        return followDAO.getFollowers(userId);
    }

    public List<FollowerInfo> getFollowing(String userId) {
        Utils.checkNull(userId, "Invalid or no userId provided", AccountException.class);
        return followDAO.getFollowing(userId);
    }

    public void followAccount(FollowPair followPair) throws AccountException {
        try {

            if (isAlreadyFollowing(followPair)) {
                log.debug("Already following {}", followPair.getUserB());
                return;
            }

            followPair.setId(UUID.randomUUID().toString());
            followPair.setFollowedOn(LocalDateTime.now());
            followDAO.save(followPair);
        } catch (RuntimeException e) {
            throw new AccountException("Error followPair account");
        }
    }

    public void unfollowAccount(String userA, String userB) {

        List<FollowPair> followPairs = followDAO.getFollowPair(userA, userB);

        if (followPairs.isEmpty()) {
            log.debug("Cannot unfollow, already not following {}", userB);
        } else {
            followDAO.delete(followPairs.getFirst());
        }
    }

    private boolean isAlreadyFollowing(FollowPair followPair) {
        try {
            List<FollowPair> list = followDAO.getFollowPair(followPair.getUserA(), followPair.getUserB());
            return list.isEmpty();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
