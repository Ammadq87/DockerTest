package com.app.core.repositories;

import com.app.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthDAO extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = :email or u.username = :username")
    Optional<User> findUserByEmailOrUsername(String email, String username);

    @Query("SELECT u FROM User u WHERE u.email = :email and u.password = :password")
    Optional<User> findUserByEmailAndPassword(String email, String password);
}
