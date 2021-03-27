package com.epam.esm.dao.db;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import com.epam.esm.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is an implementation of TagDao.
 */
@Repository
public class TagDaoImpl extends TagDao {
    private static final String SQL_INSERT_TAG = "INSERT INTO tag (name) VALUES (?);";
    private static final String SQL_DELETE_TAG_BY_NAME = "DELETE FROM tag WHERE name = ?;";
    private static final String SQL_UPDATE_TAG_NAME = "UPDATE tag SET name=? WHERE id=?;";
    private static final String SQL_FIND_ALL_TAGS = "SELECT * FROM tag";
    private static final String SQL_FIND_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate1) {
        this.jdbcTemplate = jdbcTemplate1;
    }

    @Override
    public void insert(Tag entity) {
        jdbcTemplate.update(SQL_INSERT_TAG, entity.getName());
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(SQL_DELETE_TAG_BY_NAME, name);
    }

    @Override
    public void update(Tag entity) {
        jdbcTemplate.update(SQL_UPDATE_TAG_NAME, entity.getName(), entity.getId());
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
}
