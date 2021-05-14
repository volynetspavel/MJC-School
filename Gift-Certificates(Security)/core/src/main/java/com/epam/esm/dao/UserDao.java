package com.epam.esm.dao;

import com.epam.esm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This class is a basic class of user dao-layer for interacting with database.
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String userEmail);

}
