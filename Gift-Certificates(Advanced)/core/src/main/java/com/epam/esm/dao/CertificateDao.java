package com.epam.esm.dao;

import com.epam.esm.model.Certificate;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface CertificateDao extends Dao<Certificate> {

    Certificate findByName(String name);
}
