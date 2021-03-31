package com.epam.esm.service;

import com.epam.esm.config.TestDBConfig;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
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
 * Class for testing methods from service layer for tag entity.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @DisplayName("Testing method findById() on positive result")
    @Test
    public void testFindByIdSuccess() throws ResourceNotFoundException {
        int id = 1;
        String name = "extreme";

        Tag actual = new Tag();
        actual.setId(id);
        actual.setName(name);

        Tag expected = tagService.findById(id);
        Assertions.assertEquals(actual, expected);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    public void testFindByIdThrowsException() {
        int id = 10;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagService.findById(id);
        });
    }

    @DisplayName("Testing method delete() by id of tag on positive result")
    @Test
    public void testDeleteByIdSuccess() throws ResourceNotFoundException {
        int id = 2;
        tagService.delete(id);

        //after execute method delete, expected ResourceNotFoundException, because same tag hasn't already exist
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagService.findById(id);
        });
    }

    @DisplayName("Testing method delete() by id of tag on negative result")
    @Test
    public void testDeleteByIdThrowsException() {
        int id = 1;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagService.delete(id);
        });
    }

    @DisplayName("Testing method insert() on positive result")
    @Test
    public void testInsertSuccess() throws ResourceAlreadyExistException, ResourceNotFoundException {
        Tag tag = new Tag();
        String name = "travel";
        tag.setName(name);

        tagService.insert(tag);
        Assertions.assertEquals(name, tagService.findByName(name).getName());
    }

    @DisplayName("Testing method insert() on negative result")
    @Test
    public void testInsertThrowsException() {
        Tag tag = new Tag();
        String name = "travel";
        tag.setName(name);

        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            tagService.insert(tag);
        });
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    public void testUpdateSuccess() throws ResourceNotFoundException, ResourceAlreadyExistException {
        String newName = "sport for kids";

        String updatedTagName = "travel";
        Tag updatedTag = tagService.findByName(updatedTagName);
        updatedTag.setName(newName);

        tagService.update(updatedTag);
        Assertions.assertEquals(newName, tagService.findByName(newName).getName());
    }

    @DisplayName("Testing method update() on negative result with ResourceNotFoundException")
    @Test
    public void testUpdateThrowsResourceNotFoundException() {
        int id = 20;
        String newName = "sport";

        Tag updatedTag = new Tag();
        updatedTag.setId(id);
        updatedTag.setName(newName);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagService.update(updatedTag);
        });
    }

    @DisplayName("Testing method update() on negative result with ResourceAlreadyExistException")
    @Test
    public void testUpdateThrowsResourceAlreadyExistException() {
        int id = 3;
        String newName = "rest";

        Tag updatedTag = new Tag();
        updatedTag.setId(id);
        updatedTag.setName(newName);

        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            tagService.update(updatedTag);
        });
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    public void testFindAllSuccess() throws ResourceNotFoundException {
        List<Tag> expectedTags = tagService.findAll();

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        Tag tag4 = new Tag();
        tag1.setName("extreme");
        tag2.setName("beauty");
        tag3.setName("rest");
        tag4.setName("sport for kids");

        List<Tag> actualTags = new ArrayList<Tag>() {
            {
//                add(tag1);
                add(tag2);
                add(tag3);
                add(tag4);
            }
        };
        Assertions.assertEquals(actualTags, expectedTags);
    }

    @DisplayName("Testing method findAll() on negative result")
    @Test
    public void testFindAllThrowsException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagService.findAll();
        });
    }
}
