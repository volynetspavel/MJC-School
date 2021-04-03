package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;

/**
 * This class is a layer for interacting with TagDao.
 */
public interface TagService extends AbstractService<TagDto> {

    void delete(String name) throws ResourceNotFoundException;

    TagDto findByName(String name) throws ResourceNotFoundException;
}
