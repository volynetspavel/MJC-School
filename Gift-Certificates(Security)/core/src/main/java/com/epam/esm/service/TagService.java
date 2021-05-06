package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;

/**
 * This class is a layer for interacting with TagDao.
 */
public interface TagService extends Service<TagDto, Tag> {

    TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException;

    TagDto findTagBYUserIdWithHighestCostOfAllOrders(int userId) throws ResourceNotFoundException;
}
