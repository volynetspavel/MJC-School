package com.epam.esm.dao;

import com.epam.esm.dao.crud.ReadableDao;
import com.epam.esm.dao.crud.DeletableDao;
import com.epam.esm.dao.crud.InsertableDao;
import com.epam.esm.dao.crud.UpdatableDao;
import com.epam.esm.model.Tag;

/**
 * This class is a basic class of tag dao-layer for interacting with database.
 */
public abstract class TagDao implements ReadableDao<Tag, Integer>, InsertableDao<Tag>, UpdatableDao<Tag>, DeletableDao<Tag> {

    public abstract Tag findByName(String name);

    public abstract Tag getMostPopularTagOfUserWithHighestCostOfAllOrders();

    public abstract Tag findTagBYUserIdWithHighestCostOfAllOrders(int userId);
}
