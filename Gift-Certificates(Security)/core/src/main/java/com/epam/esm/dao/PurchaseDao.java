package com.epam.esm.dao;

import com.epam.esm.dao.crud.ReadableDao;
import com.epam.esm.dao.crud.InsertableDao;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;

import java.math.BigInteger;
import java.util.List;

/**
 * This class is a basic class of purchase dao-layer for interacting with database.
 */
public abstract class PurchaseDao implements ReadableDao<Purchase, BigInteger>, InsertableDao<Purchase> {

    public abstract List<Purchase> findPurchasesByUser(User user, int offset, int limit);
}
