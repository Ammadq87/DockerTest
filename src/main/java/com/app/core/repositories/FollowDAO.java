package com.app.core.repositories;

import com.app.core.models.follow.FollowPair;
import com.app.core.models.follow.FollowerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowDAO extends JpaRepository<FollowPair, Long> {

    @Query("""
        SELECT NEW com.app.core.models.follow.FollowerInfo(F.id, F.userB, B.name, B.username, B.email, B.dateOfBirth,
        B.createdOn, F.followedOn) FROM FollowPair F INNER JOIN User B ON F.userB = B.id WHERE F.userB = :userId
        """)
    List<FollowerInfo> getFollowing(@Param("userId") String userId);

    @Query("""
            SELECT NEW com.app.core.models.follow.FollowerInfo(F.id, F.userB, B.name, B.username, B.email, B.dateOfBirth,
            B.createdOn, F.followedOn) FROM FollowPair F INNER JOIN User B ON F.userB = B.id WHERE F.userA = :userId
            """)
    List<FollowerInfo> getFollowers(@Param("userId") String userId);

    @Query("SELECT F AS COUNT FROM FollowPair F WHERE F.userA = :userA AND F.userB = :userB")
    List<FollowPair> getFollowPair(@Param("userA") String userA, @Param("userB") String userB);

    @Query("SELECT COUNT(*) AS FOLLOWERS FROM FollowPair f GROUP BY f.userB HAVING f.userB = :id")
    int getFollowerCount(@Param("id") String id);

    @Query("SELECT COUNT(*) AS Following FROM FollowPair f GROUP BY f.userA HAVING f.userA = :id")
    int getFollowingCount(@Param("id") String id);
}
