package com.epam.esm.dao.db;

import com.epam.esm.constant.CertificateTableColumn;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is an implementation of CertificateDao.
 */
@Repository
public class CertificateDaoImpl extends CertificateDao {
    private static final String SQL_INSERT_CERITFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date ) " +
            "VALUES (:name, :description, :price, :duration, :create_date, :last_update_date);";
    private static final String SQL_DELETE_CERITFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?;";
    private static final String SQL_UPDATE_CERITFICATE = "UPDATE gift_certificate SET " +
            "name=COALESCE(?,name), " +
            "description=COALESCE(?,description), " +
            "price=COALESCE(?,price), " +
            "duration =COALESCE(?,duration), " +
            "last_update_date=COALESCE(?,last_update_date) WHERE id=?;";
    private static final String SQL_FIND_ALL_CERITFICATES = "SELECT * FROM gift_certificate";
    private static final String SQL_FIND_CERITFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?;";
    private static final String SQL_FIND_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificate WHERE name = ?;";

    private static final String SQL_INSERT_CERTIFICATE_ID_AND_TAG_ID = "INSERT INTO gift_certificate_has_tag " +
            "(gift_certificate_id, tag_id) VALUES(?,?);";
    private static final String SQL_DELETE_CERTIFICATE_BY_ID_FROM_GIFT_CERTIFICATE_HAS_TAG =
            "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id = ?;";
    private static final String SQL_FIND_ALL_CERITFICATES_BY_TAG_ID =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate " +
                    "JOIN gift_certificate_has_tag " +
                    "ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id " +
                    "WHERE gift_certificate_has_tag.tag_id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateDaoImpl(JdbcTemplate jdbcTemplate1,
                              NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate1;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int insert(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(CertificateTableColumn.NAME, certificate.getName())
                .addValue(CertificateTableColumn.DESCRIPTION, certificate.getDescription())
                .addValue(CertificateTableColumn.PRICE, certificate.getPrice())
                .addValue(CertificateTableColumn.DURATION, certificate.getDuration())
                .addValue(CertificateTableColumn.CREATE_DATE, certificate.getCreateDate())
                .addValue(CertificateTableColumn.LAST_UPDATE_DATE, certificate.getLastUpdateDate());

        namedParameterJdbcTemplate.update(SQL_INSERT_CERITFICATE, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERITFICATES,
                new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public List<Certificate> findAllByTagId(int id) {
        return jdbcTemplate.query(SQL_FIND_ALL_CERITFICATES_BY_TAG_ID,
                new BeanPropertyRowMapper<>(Certificate.class), id);
    }

    @Override
    public List<Certificate> searchByPartOfName(String partOfName) {
        String SQL_FIND_ALL_CERITFICATES_BY_PART_OF_NAME = buildSqlForSearchCertificateByPartOfName(partOfName);
        return jdbcTemplate.query(SQL_FIND_ALL_CERITFICATES_BY_PART_OF_NAME,
                new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public List<Certificate> searchByPartOfDescription(String partOfDescription) {
        String SQL_FIND_ALL_CERITFICATES_BY_PART_OF_DESCRIPTION =
                buildSqlForSearchCertificateByPartOfDescription(partOfDescription);
        return jdbcTemplate.query(SQL_FIND_ALL_CERITFICATES_BY_PART_OF_DESCRIPTION,
                new BeanPropertyRowMapper<>(Certificate.class));
    }

    private String buildSqlForSearchCertificateByPartOfName(String partOfName) {
        return "SELECT * FROM gift_certificate WHERE gift_certificate.name LIKE '%" + partOfName + "%';";
    }

    private String buildSqlForSearchCertificateByPartOfDescription(String partOfDescription) {
        return "SELECT * FROM gift_certificate WHERE gift_certificate.description LIKE '%" +
                partOfDescription + "%';";
    }

    @Override
    public Certificate findById(int id) {
        return jdbcTemplate.query(SQL_FIND_CERITFICATE_BY_ID, new BeanPropertyRowMapper<>(Certificate.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public Certificate findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE_BY_NAME, new BeanPropertyRowMapper<>(Certificate.class), name)
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE_BY_ID_FROM_GIFT_CERTIFICATE_HAS_TAG, id);
        jdbcTemplate.update(SQL_DELETE_CERITFICATE_BY_ID, id);
    }

    @Override
    public void update(Certificate certificate) {
        jdbcTemplate.update(SQL_UPDATE_CERITFICATE,
                certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getLastUpdateDate(), certificate.getId());
    }

    @Override
    public void insertLinkBetweenCertificateAndTag(int idNewCertificate, int idTag) {
        jdbcTemplate.update(SQL_INSERT_CERTIFICATE_ID_AND_TAG_ID, idNewCertificate, idTag);
    }
}
