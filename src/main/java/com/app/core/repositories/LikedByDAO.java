package com.app.core.repositories;

import com.app.core.models.content.LikedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikedByDAO extends JpaRepository<LikedBy, Integer> {

    @Query("SELECT LB FROM LikedBy LB WHERE LB.username = :username AND LB.contentId = :contentId")
    Optional<LikedBy> getLiked(@Param("username") String username, @Param("contentId") String contentId);
}
