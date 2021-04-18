package com.epam.esm.dao;

import com.epam.esm.model.Certificate;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface CertificateDao extends Dao<Certificate, Integer> {

    Certificate findByName(String name);

    List<Certificate> findCertificatesBySeveralTags(List<String> tagNames);

    List<Certificate> findCertificatesByParams(String tagName, String partOfCertificateName,
                                               String partOfCertificateDescription, int pageSize, int pageNumber);
}
