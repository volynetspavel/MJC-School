package com.epam.esm.dao.db;

import com.epam.esm.constant.CertificateTableColumn;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of CertificateDao.
 */
@Repository
public class CertificateDaoImpl implements CertificateDao {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String TAGS = "tags";
    private static final String PERCENT = "%";

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
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public Certificate findById(Integer id) {
        return entityManager.find(Certificate.class, id);
    }

    @Override
    public Integer getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        criteriaQuery.from(Certificate.class);
        return (int) entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .count();
    }

    @Override
    public List<Certificate> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        criteriaQuery.from(Certificate.class);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Certificate> findCertificatesBySeveralTags(List<String> tagNames, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> certificateRoot = criteriaQuery.from(Certificate.class);

        Join<Certificate, Tag> tagJoin = certificateRoot.join(TAGS);

        CriteriaBuilder.In<String> inClause = criteriaBuilder.in(tagJoin.get(NAME));
        for (String name : tagNames) {
            inClause.value(name);
        }

        criteriaQuery.select(certificateRoot)
                .where(inClause)
                .groupBy(certificateRoot.get(ID))
                .having(criteriaBuilder.equal(criteriaBuilder.count(tagJoin.get(NAME)), tagNames.size()));

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Certificate> findCertificatesByParams(String tagName, String partOfCertificateName,
                                                      String partOfCertificateDescription, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> certificateRoot = criteriaQuery.from(Certificate.class);

        List<Predicate> predicateList = new ArrayList<>();
        Predicate predicate;

        if (!tagName.isEmpty()) {
            predicate = criteriaBuilder.like(certificateRoot.join(TAGS).get(NAME),
                    PERCENT + tagName + PERCENT);
            predicateList.add(predicate);
        }
        if (!partOfCertificateName.isEmpty()) {
            predicate = criteriaBuilder.like(certificateRoot.get(CertificateTableColumn.NAME),
                    PERCENT + partOfCertificateName + PERCENT);
            predicateList.add(predicate);
        }
        if (!partOfCertificateDescription.isEmpty()) {
            predicate = criteriaBuilder.like(certificateRoot.get(CertificateTableColumn.DESCRIPTION),
                    PERCENT + partOfCertificateDescription + PERCENT);
            predicateList.add(predicate);
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[0]))
                .orderBy(criteriaBuilder.asc(certificateRoot.get(ID)));

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
