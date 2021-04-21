package com.epam.esm.dao;

import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;

import java.math.BigInteger;
import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface PurchaseDao extends Dao<Purchase, BigInteger> {

    Purchase findById(BigInteger id);

    @Override
    default void delete(Purchase entity) {
    }

    @Override
    default void update(Purchase entity) {
    }

    List<Purchase> findPurchasesByUser(User user, int offset, int limit);
}
