package com.epam.esm.dao;

import com.epam.esm.model.Order;

import java.math.BigInteger;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface OrderDao extends Dao<Order> {

    Order findById(BigInteger id);

    default Order findById(int id) {
        return null;
    }
}
