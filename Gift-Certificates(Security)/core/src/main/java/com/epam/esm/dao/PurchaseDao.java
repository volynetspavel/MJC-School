package com.epam.esm.dao;

import com.epam.esm.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * This class is a basic class of purchase dao-layer for interacting with database.
 */
@Repository
public interface PurchaseDao extends JpaRepository<Purchase, BigInteger> {

    List<Purchase> findAllByUserId(int userId);

}
