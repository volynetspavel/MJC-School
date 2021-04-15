package com.epam.esm.dao.db;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * This class is an implementation of TagDao.
 */
@Repository
public class TagDaoImpl implements TagDao {

    private static final String NAME = "name";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(NAME), name));
        return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
    }

    @Override
    public Tag insert(Tag tag) {
        entityManager.persist(tag);
        return findByName(tag.getName());
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public void update(Tag tag) {
        entityManager.merge(tag);
    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        criteriaQuery.from(Tag.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Tag findById(int id) {
        return entityManager.find(Tag.class, id);
    }
}
