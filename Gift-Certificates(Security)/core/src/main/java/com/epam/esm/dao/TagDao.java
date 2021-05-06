package com.epam.esm.dao;

import com.epam.esm.model.Tag;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface TagDao extends Dao<Tag, Integer> {

    Tag findByName(String name);

    void delete(Tag tag);

    Tag getMostPopularTagOfUserWithHighestCostOfAllOrders();

    Tag findTagBYUserIdWithHighestCostOfAllOrders(int userId);
}
