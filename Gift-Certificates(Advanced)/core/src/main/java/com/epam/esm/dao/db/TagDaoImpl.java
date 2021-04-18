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
    private static final String SQL_SELECT_MOST_POPULAR_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS =
            "select t.id, t.name from tag t\n" +
                    "join gift_certificate_has_tag gct on gct.tag_id = t.id\n" +
                    "join purchase_gift_certificate pgc on pgc.gift_certificate_id = gct.gift_certificate_id\n" +
                    "join purchase p on p.id = pgc.purchase_id\n" +
                    "where user_id \n" +
                    "= (select user_id from purchase group by user_id order by sum(cost) desc limit 1)\n" +
                    "group by t.name\n" +
                    "order by count(*) desc \n" +
                    "limit 1";

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
    public List<Tag> findAll(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        criteriaQuery.from(Tag.class);
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
                .getResultStream().count();

    }

    @Override
    public Tag getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        return (Tag) entityManager.createNativeQuery(
                SQL_SELECT_MOST_POPULAR_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS, Tag.class).getSingleResult();
    }
}
