package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public abstract class TagDao extends AbstractDao<Tag>{

    public abstract Tag findByName(String name);

    public abstract List<Tag> findTagsByCertificateId(int idCertificate);

    public abstract void deleteTagByIdFromGiftCertificateHasTag(int id);
}
