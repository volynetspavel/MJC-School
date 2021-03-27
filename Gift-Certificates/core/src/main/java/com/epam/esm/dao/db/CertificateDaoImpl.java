package com.epam.esm.dao.db;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.Certificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is an implementation of CertificateDao.
 */
@Repository
public class CertificateDaoImpl extends CertificateDao {
    private static final String SQL_INSERT_CERITFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_time ) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE_CERITFICATE_BY_NAME = "DELETE FROM gift_certificate WHERE name = ?;";
    private static final String SQL_UPDATE_CERITFICATE_NAME = "UPDATE gift_certificate " +
            "SET name=?, description=?, price=?, duration=?, create_date=?, last_update_time=? WHERE id=?;";
    private static final String SQL_FIND_ALL_CERITFICATES = "SELECT * FROM gift_certificate";
    private static final String SQL_FIND_CERITFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDaoImpl(JdbcTemplate jdbcTemplate1) {
        this.jdbcTemplate = jdbcTemplate1;
    }

    @Override
    public void insert(Certificate entity) {
        jdbcTemplate.update(SQL_INSERT_CERITFICATE,
                entity.getName(), entity.getDescription(), entity.getPrice(),
                entity.getDuration(), entity.getCreateTime(), entity.getLastUpdateTime());
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update(SQL_DELETE_CERITFICATE_BY_NAME, id);
    }

    @Override
    public void update(Certificate entity) {
        jdbcTemplate.update(SQL_UPDATE_CERITFICATE_NAME,
                entity.getName(), entity.getDescription(), entity.getPrice(),
                entity.getDuration(), entity.getCreateTime(), entity.getLastUpdateTime(),
                entity.getId());
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERITFICATES, new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public Certificate findById(int id) {
        return jdbcTemplate.query(SQL_FIND_CERITFICATE_BY_ID, new BeanPropertyRowMapper<>(Certificate.class), id)
                .stream().findAny().orElse(null);
    }
}
