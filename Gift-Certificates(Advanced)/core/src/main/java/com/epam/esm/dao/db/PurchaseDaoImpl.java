package com.epam.esm.dao.db;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.model.Purchase;
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
    public int insert(Purchase entity) {
        return 0;
    }

    @Override
    public void delete(int id) {

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
