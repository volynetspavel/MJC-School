package com.epam.esm.dao.db;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

/**
 * This class is an implementation of PurchaseDao.
 */
@Repository
public class PurchaseDaoImpl implements PurchaseDao {

    private static final String USER = "user";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Purchase insert(Purchase purchase) {
        entityManager.persist(purchase);
        return findById(purchase.getId());
    }

    @Override
    public List<Purchase> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        criteriaQuery.from(Purchase.class);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Purchase findById(BigInteger id) {
        return entityManager.find(Purchase.class, id);
    }

    @Override
    public List<Purchase> findPurchasesByUser(User user, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> purchaseRoot = criteriaQuery.from(Purchase.class);

        Predicate predicate = criteriaBuilder.equal(purchaseRoot.get(USER), user);
        criteriaQuery.select(purchaseRoot)
                .where(predicate);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public BigInteger getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        criteriaQuery.from(Purchase.class);
        return BigInteger.valueOf(entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .count());
    }
}
