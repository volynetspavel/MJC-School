package com.epam.esm.dao.db;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.Certificate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * This class is an implementation of CertificateDao.
 */
@Repository
public class CertificateDaoImpl implements CertificateDao {

    private static final String NAME = "name";

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
}
