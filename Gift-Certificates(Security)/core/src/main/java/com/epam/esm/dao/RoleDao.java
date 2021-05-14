package com.epam.esm.dao;

import com.epam.esm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This class is a basic class of role dao-layer for interacting with database.
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String roleName);

}
