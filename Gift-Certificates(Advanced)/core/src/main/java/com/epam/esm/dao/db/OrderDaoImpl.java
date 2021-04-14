package com.epam.esm.dao.db;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**
 * This class is an implementation of TagDao.
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int insert(Order entity) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order findById(BigInteger id) {
        return entityManager.find(Order.class, id);
    }
}
