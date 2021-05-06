package com.epam.esm.dao;

import com.epam.esm.dao.crud.ReadableDao;
import com.epam.esm.dao.crud.DeletableDao;
import com.epam.esm.dao.crud.InsertableDao;
import com.epam.esm.dao.crud.UpdatableDao;
import com.epam.esm.model.Certificate;

import java.util.List;

/**
 * This class is a basic class of certificate dao-layer for interacting with database.
 */
public abstract class CertificateDao implements ReadableDao<Certificate, Integer>,
        InsertableDao<Certificate>, UpdatableDao<Certificate>, DeletableDao<Certificate> {

    public abstract Certificate findByName(String name);

    public abstract List<Certificate> findCertificatesBySeveralTags(List<String> tagNames, int offset, int limit);

    public abstract List<Certificate> findCertificatesByParams(String tagName, String partOfCertificateName,
                                                               String partOfCertificateDescription, int pageSize, int offset);
}
