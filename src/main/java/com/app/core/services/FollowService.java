package com.app.core.services;

import com.app.core.exceptions.AccountException;
import com.app.core.models.follow.FollowPair;
import com.app.core.models.follow.FollowerInfo;
import com.app.core.repositories.FollowDAO;
import com.app.core.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class FollowService {
    private final FollowDAO followDAO;

    @Autowired
    public FollowService(FollowDAO followDAO) {this.followDAO = followDAO;}

    public List<FollowerInfo> getFollowers(String username) {
        Utils.checkNull(username, "Invalid or no username provided", AccountException.class);
        return followDAO.getFollowers(username);
    }

    public List<FollowerInfo> getFollowing(String username) {
        Utils.checkNull(username, "Invalid or no username provided", AccountException.class);
        return followDAO.getFollowing(username);
    }

    public void followAccount(FollowPair followPair) throws AccountException {
        try {

            List<FollowPair> list = followDAO.getFollowPair(followPair.getFollowPairID().getUserA(), followPair.getFollowPairID().getUserB());

            if (!list.isEmpty()) {
                followDAO.delete(list.getFirst());
            } else {
                followPair.setFollowedOn(LocalDateTime.now());
                followDAO.save(followPair);
            }

            log.debug("{} {}", list.isEmpty() ? "Followed: " : "Unfollowed: ", followPair.getFollowPairID().getUserB());
        } catch (RuntimeException e) {
            throw new AccountException("Error followPair account");
        }
    }
}
