package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDao;
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
    private AbstractDao<Tag> abstractDao;

    @Autowired
    public TagServiceImpl(AbstractDao<Tag> abstractDao){
        this.abstractDao = abstractDao;
    }

    @Override
    public void insert(Tag entity) {
        abstractDao.insert(entity);
    }

    @Override
    public void delete(String name) {
        abstractDao.delete(name);
    }

    @Override
    public void update(Tag entity) {
        abstractDao.update(entity);
    }

    @Override
    public List<Tag> findAll() {
        return abstractDao.findAll();
    }

    @Override
    public Tag findById(int id) {
        return abstractDao.findById(id);
    }
}
