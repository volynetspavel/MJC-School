package com.epam.esm.dao;

import com.epam.esm.model.Certificate;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface CertificateDao extends Dao<Certificate> {

    Certificate findByName(String name);

    void insertLinkBetweenCertificateAndTag(int idNewCertificate, int idTag);

    List<Certificate> findAllByTagId(int id);

    List<Certificate> searchByPartOfName(String partOfName);

    List<Certificate> searchByPartOfDescription(String partOfDescription);
}
