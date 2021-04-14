package com.epam.esm.dao;

import com.epam.esm.model.Purchase;

import java.math.BigInteger;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface PurchaseDao extends Dao<Purchase> {

    Purchase findById(BigInteger id);

    default Purchase findById(int id) {
        return null;
    }
}
