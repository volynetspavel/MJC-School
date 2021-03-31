package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is an implementation of TagService.
 */
@Service
public class TagServiceImpl extends TagService {
    private TagDao tagDao;

    public TagServiceImpl() {
    }

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public void insert(Tag tag) throws ResourceAlreadyExistException {
        if (tagDao.findByName(tag.getName()) != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + tag.getName() + ") has already existed.");
        }
        tagDao.insert(tag);
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
    public void update(Tag newTag) throws ResourceNotFoundException, ResourceAlreadyExistException {
        int id = newTag.getId();
        String name = newTag.getName();

        Tag oldTag = tagDao.findById(id);
        Tag tagWithSameName = tagDao.findByName(name);

        if (oldTag == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        if (tagWithSameName != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + tagWithSameName.getName() + ") has already existed.");
        }
        tagDao.update(newTag);
    }

    @Override
    public List<Tag> findAll() throws ResourceNotFoundException {
        List<Tag> tags = tagDao.findAll();
        if (tags == null || tags.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found");
        }
        return tags;
    }

    @Override
    public Tag findById(int id) throws ResourceNotFoundException {
        Tag tag = tagDao.findById(id);
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        return tag;
    }

    @Override
    public Tag findByName(String name) throws ResourceNotFoundException {
        Tag tag = tagDao.findByName(name);
        if (tag == null) {
            throw new ResourceNotFoundException("Requested resource not found (name = " + name + ")");
        }
        return tag;
    }
}
