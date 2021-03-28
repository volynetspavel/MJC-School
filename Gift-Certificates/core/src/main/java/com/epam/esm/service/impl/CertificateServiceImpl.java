package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is an implementation of CertificateService.
 */
@Service
public class CertificateServiceImpl extends CertificateService {
    private AbstractDao<Certificate> abstractDao;

    @Autowired
    public CertificateServiceImpl(AbstractDao<Certificate> abstractDao){
        this.abstractDao = abstractDao;
    }

    @Override
    public void insert(Certificate entity) {
        abstractDao.insert(entity);
    }

    @Override
    public void delete(String name) {
        abstractDao.delete(name);
    }

    @Override
    public void update(Certificate entity) {
        abstractDao.update(entity);
    }

    @Override
    public List<Certificate> findAll() {
        return abstractDao.findAll();
    }

    @Override
    public Certificate findById(int id) {
        return abstractDao.findById(id);
    }
}
