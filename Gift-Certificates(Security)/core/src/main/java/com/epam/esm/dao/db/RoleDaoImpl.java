package com.epam.esm.dao.db;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * This class is an implementation of RoleDao.
 */
@Repository
public class RoleDaoImpl extends RoleDao {

    private static final String NAME = "name";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Role findByName(String roleName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get(NAME), roleName));
        return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
    }
}
