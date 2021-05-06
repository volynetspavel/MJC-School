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

    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String SQL_SELECT_MOST_POPULAR_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS =
            "SELECT t.id, t.name FROM tag t\n" +
                    "JOIN gift_certificate_has_tag gct ON gct.tag_id = t.id\n" +
                    "JOIN purchase_gift_certificate pgc ON pgc.gift_certificate_id = gct.gift_certificate_id\n" +
                    "JOIN purchase p ON p.id = pgc.purchase_id\n" +
                    "WHERE user_id \n" +
                    "= (SELECT user_id FROM purchase GROUP BY user_id ORDER BY sum(cost) DESC LIMIT 1)\n" +
                    "GROUP BY t.name\n" +
                    "ORDER BY count(*) DESC \n" +
                    "LIMIT 1";
    private static final String SQL_SELECT_TAG_BY_USER_ID_WITH_HIGHEST_COST_OF_ALL_ORDERS =
            "SELECT t.id, t.name\n" +
                    "FROM tag t\n" +
                    "JOIN gift_certificate_has_tag gct ON gct.tag_id = t.id\n" +
                    "JOIN gift_certificate gc ON gc.id = gct.gift_certificate_id\n" +
                    "JOIN purchase_gift_certificate pgc ON pgc.gift_certificate_id = gc.id\n" +
                    "JOIN purchase p ON p.id = pgc.purchase_id\n" +
                    "WHERE p.user_id = ?\n" +
                    "GROUP BY t.name\n" +
                    "ORDER BY sum(price) DESC \n" +
                    "LIMIT 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(NAME), name));
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst()
                .orElse(null);
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
    public List<Tag> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);

        criteriaQuery.orderBy(criteriaBuilder.asc(tagRoot.get(ID)));
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Tag findById(Integer id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Integer getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        criteriaQuery.from(Tag.class);
        return (int) entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .count();
    }

    @Override
    public Tag getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        return (Tag) entityManager
                .createNativeQuery(SQL_SELECT_MOST_POPULAR_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS, Tag.class)
                .getSingleResult();
    }

    @Override
    public Tag findTagBYUserIdWithHighestCostOfAllOrders(int userId) {
        return (Tag) entityManager
                .createNativeQuery(SQL_SELECT_TAG_BY_USER_ID_WITH_HIGHEST_COST_OF_ALL_ORDERS, Tag.class)
                .setParameter(1, userId)
                .getSingleResult();
    }
}
