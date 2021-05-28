package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.crud.DeletableService;
import com.epam.esm.service.crud.InsertableService;
import com.epam.esm.service.crud.ReadableService;
import com.epam.esm.service.crud.UpdatableService;

/**
 * This class is a layer for interacting with TagDao.
 */
public abstract class TagService implements ReadableService<TagDto, Tag>, InsertableService<TagDto>,
        UpdatableService<TagDto>, DeletableService<TagDto> {

    public abstract TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException;

    public abstract TagDto findTagBYUserIdWithHighestCostOfAllOrders(int userId) throws ResourceNotFoundException;
}
