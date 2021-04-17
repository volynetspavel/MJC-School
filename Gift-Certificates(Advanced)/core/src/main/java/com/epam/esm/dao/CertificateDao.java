package com.epam.esm.dao;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface CertificateDao extends Dao<Certificate> {

    Certificate findByName(String name);

    List<Certificate> findCertificatesBySeveralTags(List<Tag> tags);
}
