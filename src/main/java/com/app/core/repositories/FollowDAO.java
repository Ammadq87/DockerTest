package com.app.core.repositories;

import com.app.core.models.follow.FollowPair;
import com.app.core.models.follow.FollowerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowDAO extends JpaRepository<FollowPair, Long> {

    @Query("""
        SELECT NEW com.app.core.models.follow.FollowerInfo(F.followPairID.userB, B.name, B.username, B.email, B.dateOfBirth,
        B.createdOn, F.followedOn) FROM FollowPair F INNER JOIN User B ON F.followPairID.userA = B.username WHERE F.followPairID.userB = :username
        """)
    List<FollowerInfo> getFollowers(@Param("username") String username);

    @Query("""
            SELECT NEW com.app.core.models.follow.FollowerInfo(F.followPairID.userB, B.name, B.username, B.email, B.dateOfBirth,
            B.createdOn, F.followedOn) FROM FollowPair F INNER JOIN User B ON F.followPairID.userB = B.username WHERE F.followPairID.userA = :username
            """)
    List<FollowerInfo> getFollowing(@Param("username") String username);

    @Query("SELECT F AS COUNT FROM FollowPair F WHERE F.followPairID.userA = :userA AND F.followPairID.userB = :userB")
    List<FollowPair> getFollowPair(@Param("userA") String userA, @Param("userB") String userB);
}
