package com.app.core.repositories;

import com.app.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountDAO extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<List<User>> findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<List<User>> findUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:param%")
    Optional<List<User>> findAccountsBySearchParam(@Param("param") String param);

}
