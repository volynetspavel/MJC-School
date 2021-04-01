package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;

/**
 * This class is a layer for interacting with TagDao.
 */
public abstract class TagService implements AbstractService<TagDto>{

    public abstract void delete(String name) throws ResourceNotFoundException;

    public abstract TagDto findByName(String name) throws ResourceNotFoundException;
}
