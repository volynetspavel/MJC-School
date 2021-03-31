package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;

/**
 * This class is a layer for interacting with TagDao.
 */
public abstract class TagService implements AbstractService<Tag>{

    public abstract void delete(int id) throws ResourceNotFoundException;

    public abstract Tag findByName(String name) throws ResourceNotFoundException;
}
