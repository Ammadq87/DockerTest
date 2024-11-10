package com.app.core.repositories;

import com.app.core.models.content.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentDAO extends JpaRepository<Content, String> {

    @Query("""
    SELECT C FROM Content C
    INNER JOIN (
        SELECT F.followPairID.userB as userB
        FROM FollowPair F
        INNER JOIN User B ON F.followPairID.userB = B.username
        WHERE F.followPairID.userA = :username
    ) FL ON FL.userB = C.username
    ORDER BY C.createdOn DESC
""")
    List<Content> getFeed(@Param("username") String username);

}
