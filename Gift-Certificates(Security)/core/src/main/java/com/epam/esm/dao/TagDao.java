package com.epam.esm.dao;

import com.epam.esm.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This class is a basic class of tag dao-layer for interacting with database.
 */
@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByName(String name);

    @Query(value = "SELECT t.id, t.name FROM tag t\n" +
            "JOIN gift_certificate_has_tag gct ON gct.tag_id = t.id\n" +
            "JOIN purchase_gift_certificate pgc ON pgc.gift_certificate_id = gct.gift_certificate_id\n" +
            "JOIN purchase p ON p.id = pgc.purchase_id\n" +
            "WHERE user_id \n" +
            "= (SELECT user_id FROM purchase GROUP BY user_id ORDER BY sum(cost) DESC LIMIT 1)\n" +
            "GROUP BY t.name\n" +
            "ORDER BY count(*) DESC \n" +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Tag> getMostPopularTagOfUserWithHighestCostOfAllOrders();

    @Query(value = "SELECT t.id, t.name\n" +
            "FROM tag t\n" +
            "JOIN gift_certificate_has_tag gct ON gct.tag_id = t.id\n" +
            "JOIN gift_certificate gc ON gc.id = gct.gift_certificate_id\n" +
            "JOIN purchase_gift_certificate pgc ON pgc.gift_certificate_id = gc.id\n" +
            "JOIN purchase p ON p.id = pgc.purchase_id\n" +
            "WHERE p.user_id = ?\n" +
            "GROUP BY t.name\n" +
            "ORDER BY sum(price) DESC \n" +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Tag> findTagBYUserIdWithHighestCostOfAllOrders(int userId);
}
