package com.epam.esm.dao;

import com.epam.esm.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This class is a basic class of certificate dao-layer for interacting with database.
 */
@Repository
public interface CertificateDao extends JpaRepository<Certificate, Integer>, JpaSpecificationExecutor<Certificate> {

    Optional<Certificate> findFirstByName(String name);

}
