package com.epam.esm.dao;

import com.epam.esm.config.TestDBConfig;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for testing methods from dao layer for tag.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @DisplayName("Testing method findById() on positive result")
    @Test
    void testFindByIdSuccess() {
        int id = 2;
        String name = "beauty";

        Tag actual = createTag(name);
        actual.setId(id);

        Tag expected = tagDao.findById(id);
        Assertions.assertEquals(actual, expected);
    }

    @DisplayName("Testing method findById() on negative result")
    @Test
    void testFindByIdReturnNull() {
        int id = 10;
        Assertions.assertNull(tagDao.findById(id));
    }

    @DisplayName("Testing method delete() by id of tag on positive result")
    @Test
    void testDeleteByIdSuccess() {
        int id = 4;
        tagDao.delete(id);

        //after execute method delete, expected null, because same tag hasn't already exist
        Assertions.assertNull(tagDao.findById(id));
    }

    @DisplayName("Testing method insert() on positive result")
    @Test
    void testInsertSuccess() {
        String name = "travel";
        Tag tag = createTag(name);

        tagDao.insert(tag);
        Assertions.assertEquals(name, tagDao.findByName(name).getName());
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    void testUpdateSuccess() {
        String newName = "sport for kids";

        String updatedTagName = "travel";
        Tag updatedTag = tagDao.findByName(updatedTagName);
        updatedTag.setName(newName);

        tagDao.update(updatedTag);
        Assertions.assertEquals(newName, tagDao.findByName(newName).getName());
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void testFindAllSuccess() {
        List expectedTags = tagDao.findAll();

        String name1 = "extreme";
        String name2 = "beauty";
        String name3 = "rest";
        String name4 = "sport";

        Tag tag1 = createTag(name1);
        Tag tag2 = createTag(name2);
        Tag tag3 = createTag(name3);
        Tag tag4 = createTag(name4);

        List actualTags = new ArrayList<Tag>() {
            {
                add(tag1);
                add(tag2);
                add(tag3);
                add(tag4);
            }
        };
        Assertions.assertEquals(actualTags, expectedTags);
    }

    @DisplayName("Testing method findAll() on negative result")
    @Test
    void testFindAllReturnNull() {
        Assertions.assertNull(tagDao.findAll());
    }

    private Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}
