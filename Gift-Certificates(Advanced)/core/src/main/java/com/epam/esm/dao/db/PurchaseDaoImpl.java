package com.epam.esm.dao.db;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**
 * This class is an implementation of TagDao.
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
    public void delete(Purchase entity) {

    }

    @Override
    public void update(Purchase entity) {

    }

    @Override
    public List<Purchase> findAll() {
        return null;
    }

    @Override
    public Purchase findById(BigInteger id) {
        return entityManager.find(Purchase.class, id);
    }
}
