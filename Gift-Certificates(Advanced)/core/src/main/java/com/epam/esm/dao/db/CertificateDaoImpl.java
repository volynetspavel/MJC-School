package com.epam.esm.dao.db;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an implementation of CertificateDao.
 */
@Repository
public class CertificateDaoImpl implements CertificateDao {

    private static final String NAME = "name";
    private static final String SQL_FIND_CERTIFICATES_BY_SEVERAL_TAGS = "SELECT * FROM gift_certificate gc\n" +
            "JOIN gift_certificate_has_tag gct ON gct.gift_certificate_id = gc.id\n" +
            "JOIN tag t ON t.id = gct.tag_id\n" +
            "WHERE t.name IN (?)\n" +
            "GROUP BY gc.name\n" +
            "HAVING count(t.name) = ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Certificate insert(Certificate certificate) {
        entityManager.persist(certificate);
        return findByName(certificate.getName());
    }

    @Override
    public void update(Certificate certificate) {
        entityManager.merge(certificate);
    }

    @Override
    public Certificate findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get(NAME), name));
        return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public Certificate findById(int id) {
        return entityManager.find(Certificate.class, id);
    }

    @Override
    public List<Certificate> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        criteriaQuery.from(Certificate.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Certificate> findCertificatesBySeveralTags(List<Tag> tags) {
        String sql = buildSqlFindCertificatesBySeveralTags(tags);
        return (List<Certificate>) entityManager.createNativeQuery(sql, Certificate.class).getResultList();
    }

    private String buildSqlFindCertificatesBySeveralTags(List<Tag> tags){
        String names = tags.stream()
                .map(tag -> ("'" + tag.getName() + "'"))
                .collect(Collectors.joining(", "));

        return SQL_FIND_CERTIFICATES_BY_SEVERAL_TAGS.replace("?", names) + tags.size();

    }
}
