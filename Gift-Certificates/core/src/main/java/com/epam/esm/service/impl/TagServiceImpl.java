package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an implementation of TagService.
 */
@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;
    private TagMapper tagMapper;

    public TagServiceImpl() {
    }

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper tagMapper) {
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDto insert(TagDto tagDto) throws ResourceAlreadyExistException {
        if (tagDao.findByName(tagDto.getName()) != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + tagDto.getName() + ") has already existed.");
        }
        Tag tag = tagMapper.toEntity(tagDto);
        int idNewTag = tagDao.insert(tag);

        return tagMapper.toDto(tagDao.findById(idNewTag));
    }

    @Override
    public void delete(String name) throws ResourceNotFoundException {
        Tag tag = tagDao.findByName(name);
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource not found (name = " + name + ")");
        }
        tagDao.delete(tag.getId());
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {
        Tag tag = tagDao.findById(id);
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        tagDao.delete(id);
    }

    @Override
    public TagDto update(TagDto newTagDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
        int id = newTagDto.getId();
        String name = newTagDto.getName();

        Tag oldTag = tagDao.findById(id);
        Tag tagWithSameName = tagDao.findByName(name);

        if (oldTag == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        if (tagWithSameName != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + tagWithSameName.getName() + ") has already existed.");
        }

        Tag newTag = tagMapper.toEntity(newTagDto);
        tagDao.update(newTag);

        return tagMapper.toDto(tagDao.findById(id));
    }

    @Override
    public List<TagDto> findAll() throws ResourceNotFoundException {
        List<Tag> tags = tagDao.findAll();
        if (tags == null || tags.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found");
        }
        return tags.stream()
                .map(tag -> tagMapper.toDto(tag))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(int id) throws ResourceNotFoundException {
        Tag tag = tagDao.findById(id);
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        return tagMapper.toDto(tag);
    }

    @Override
    public TagDto findByName(String name) throws ResourceNotFoundException {
        Tag tag = tagDao.findByName(name);
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource not found (name = " + name + ")");
        }
        return tagMapper.toDto(tag);
    }
}
