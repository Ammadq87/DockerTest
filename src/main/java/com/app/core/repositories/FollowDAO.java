package com.app.core.repositories;
import com.app.core.models.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FollowDAO extends JpaRepository<Following, Long> {

    @Query("SELECT COUNT(*) AS COUNT FROM Following f WHERE f.userA = :userA AND f.userB = :userB")
    int isAlreadyFollowing(@Param("userA") String userA, @Param("userB") String userB);

    @Query("SELECT COUNT(*) AS FOLLOWERS FROM Following f GROUP BY f.userB HAVING f.userB = :id")
    int getFollowerCount(@Param("id") String id);

    @Query("SELECT COUNT(*) AS Following FROM Following f GROUP BY f.userA HAVING f.userA = :id")
    int getFollowingCount(@Param("id") String id);
}
