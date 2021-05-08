package com.epam.esm.dao.db;

import com.epam.esm.dao.UserDao;
import com.epam.esm.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * This class is an implementation of UserDao.
 */
@Repository
public class UserDaoImpl extends UserDao {

    private static final String ID = "id";
    private static final String EMAIL = "email";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(ID)));
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Integer getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.from(User.class);

        return (int) entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .count();
    }

    @Transactional
    @Override
    public User findByEmail(String userEmail) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get(EMAIL), userEmail));
        return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
    }

    @Override
    public User insert(User user) {
        entityManager.persist(user);
        return user;
    }
}
