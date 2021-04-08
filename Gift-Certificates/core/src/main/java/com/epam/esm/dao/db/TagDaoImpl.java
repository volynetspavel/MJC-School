package com.epam.esm.dao.db;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is an implementation of TagDao.
 */
@Repository
public class TagDaoImpl extends TagDao {

    private static final String SQL_INSERT_TAG = "INSERT INTO tag (name) VALUES (?);";
    private static final String SQL_DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?;";
    private static final String SQL_UPDATE_TAG_NAME = "UPDATE tag SET name=? WHERE id=?;";
    private static final String SQL_FIND_ALL_TAGS = "SELECT * FROM tag";
    private static final String SQL_FIND_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?;";
    private static final String SQL_FIND_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?;";

    private static final String SQL_DELETE_TAG_BY_ID_FROM_GIFT_CERTIFICATE_HAS_TAG =
            "DELETE FROM gift_certificate_has_tag WHERE tag_id = ?;";
    private static final String SQL_FIND_TAG_BY_CERTIFICATE_ID =
            "SELECT id, name  FROM gift_certificate_db.tag " +
                    "JOIN gift_certificate_db.gift_certificate_has_tag " +
                    "ON tag.id = gift_certificate_has_tag.tag_id " +
                    "WHERE gift_certificate_has_tag.gift_certificate_id = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate1) {
        this.jdbcTemplate = jdbcTemplate1;
    }

    @Override
    public int insert(Tag entity) {
        jdbcTemplate.update(SQL_INSERT_TAG, entity.getName());
        return findByName(entity.getName()).getId();
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE_TAG_BY_ID_FROM_GIFT_CERTIFICATE_HAS_TAG, id);
        jdbcTemplate.update(SQL_DELETE_TAG_BY_ID, id);
    }

    @Override
    public void update(Tag newTag) {
        jdbcTemplate.update(SQL_UPDATE_TAG_NAME, newTag.getName(), newTag.getId());
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag findById(int id) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public Tag findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Tag> findTagsByCertificateId(int idCertificate) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_CERTIFICATE_ID,
                new BeanPropertyRowMapper<>(Tag.class), idCertificate);
    }
}
