package com.epam.esm.dao.db;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.model.Purchase;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigInteger;
import java.util.List;

/**
 * This class is an implementation of PurchaseDao.
 */
@Repository
public class PurchaseDaoImpl implements PurchaseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Purchase insert(Purchase purchase) {
        entityManager.persist(purchase);
        return findById(purchase.getId());
    }

    @Override
    public List<Purchase> findAll(int pageNumber, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        criteriaQuery.from(Purchase.class);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageNumber)
                .setMaxResults(pageSize)
                .getResultList();
    }


    @Override
    public void delete(Purchase entity) {

    }

    @Override
    public void update(Purchase entity) {

    }

    @Override
    public Purchase findById(BigInteger id) {
        return entityManager.find(Purchase.class, id);
    }

    @Override
    public BigInteger getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        criteriaQuery.from(Purchase.class);
        return BigInteger.valueOf(entityManager.createQuery(criteriaQuery)
                .getResultStream().count());

    }
}
