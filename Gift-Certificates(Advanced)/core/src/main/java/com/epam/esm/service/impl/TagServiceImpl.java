package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is an implementation of TagService.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    private TagDao tagDao;
    private TagMapper tagMapper;

    private int offset;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper tagMapper) {
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
    }

    @Transactional
    @Override
    public TagDto insert(TagDto tagDto) throws ResourceAlreadyExistException {
        if (tagDao.findByName(tagDto.getName()) != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + tagDto.getName() + ") has already existed.");
        }
        Tag tag = tagMapper.toEntity(tagDto);
        tag.setId(null);
        Tag newTag = tagDao.insert(tag);
        return tagMapper.toDto(newTag);
    }

    @Transactional
    @Override
    public void delete(int id) throws ResourceNotFoundException {
        Tag tag = tagDao.findById(id);
        checkEntityOnNull(tag, id);

        tagDao.delete(tag);
    }

    @Transactional
    @Override
    public TagDto update(TagDto newTagDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
        int id = newTagDto.getId();
        String name = newTagDto.getName();

        Tag oldTag = tagDao.findById(id);
        checkEntityOnNull(oldTag, id);

        Tag tagWithSameName = tagDao.findByName(name);
        if (tagWithSameName != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + tagWithSameName.getName() + ") has already existed.");
        }

        Tag newTag = tagMapper.toEntity(newTagDto);
        tagDao.update(newTag);
        Tag updatesTagFromDataBase = tagDao.findById(id);
        return tagMapper.toDto(updatesTagFromDataBase);
    }

    @Override
    public List<TagDto> findAll(Map<String, String> params) throws ResourceNotFoundException {
        int limit = tagDao.getCount();

        if (params.containsKey(SIZE) && params.containsKey(PAGE)) {
            limit = Integer.parseInt(params.get(SIZE));
            offset = (Integer.parseInt(params.get(PAGE)) - 1) * limit;
        }

        List<Tag> tags = tagDao.findAll(offset, limit);
        checkListOnEmptyOrNull(tags);

        return migrateListFromEntityToDto(tags);
    }

    @Override
    public TagDto findById(int id) throws ResourceNotFoundException {
        Tag tag = tagDao.findById(id);
        checkEntityOnNull(tag, id);
        return tagMapper.toDto(tag);
    }

    @Override
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException {
        Tag tag = tagDao.getMostPopularTagOfUserWithHighestCostOfAllOrders();
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource not found.");
        }
        return tagMapper.toDto(tag);
    }

    private List<TagDto> migrateListFromEntityToDto(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tagMapper.toDto(tag))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findTagBYUserIdWithHighestCostOfAllOrders(int userId) throws ResourceNotFoundException {
        Tag tag = tagDao.findTagBYUserIdWithHighestCostOfAllOrders(userId);
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource for user (id = " + userId + ") not found ");
        }
        return tagMapper.toDto(tag);
    }
}
