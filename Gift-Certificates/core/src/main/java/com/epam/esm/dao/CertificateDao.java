package com.epam.esm.dao;

import com.epam.esm.model.Certificate;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public abstract class CertificateDao extends AbstractDao<Certificate> {

    public abstract Certificate findByName(String name);

    public abstract void insertLinkBetweenCertificateAndTag(int idNewCertificate, int idTag);

    public abstract List<Certificate> findAllByTagId(int id);
}
