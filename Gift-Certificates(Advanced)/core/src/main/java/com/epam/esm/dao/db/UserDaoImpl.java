package com.epam.esm.dao.db;

import com.epam.esm.dao.UserDao;
import com.epam.esm.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * This class is an implementation of UserDao.
 */
@Repository
public class UserDaoImpl extends UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.from(User.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }
}
