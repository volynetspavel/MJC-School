package com.epam.esm.dao;

import com.epam.esm.model.Purchase;

import java.math.BigInteger;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface PurchaseDao extends Dao<Purchase, BigInteger> {

    Purchase findById(BigInteger id);

}
