package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface TagDao extends Dao<Tag> {

    Tag findByName(String name);

    List<Tag> findTagsByCertificateId(int idCertificate);

    void deleteTagByIdFromGiftCertificateHasTag(int id);
}
