package com.epam.esm.service;

import com.epam.esm.config.TestDBConfig;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

/**
 * Class for testing methods from service layer for tag.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagMapper tagMapper;


    @DisplayName("Testing method findById() on positive result")
    @Test
    void testFindByIdSuccess() throws ResourceNotFoundException {
        int id = 3;
        String name = "rest";

        Tag actual = createTag(name);
        actual.setId(id);

        Tag expected = tagMapper.toEntity(tagService.findById(id));
        Assertions.assertEquals(actual, expected);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    void testFindByIdThrowsException() {
        int id = 10;
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.findById(id));
    }

    @DisplayName("Testing method delete() by id of tag on positive result")
    @Test
    void testDeleteByIdSuccess() throws ResourceNotFoundException {
        int id = 2;
        tagService.delete(id);

        //after execute method delete, expected ResourceNotFoundException, because same tag hasn't already exist
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.findById(id));
    }

    @DisplayName("Testing method delete() by id of tag on negative result")
    @Test
    void testDeleteByIdThrowsException() {
        int id = 1;

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.delete(id));
    }

    @DisplayName("Testing method insert() on positive result")
    @Test
    void testInsertSuccess() throws ResourceAlreadyExistException, ResourceNotFoundException {
        String name = "travel";
        Tag tag = createTag(name);

        tagService.insert(tagMapper.toDto(tag));
        Assertions.assertEquals(name, tagService.findByName(name).getName());
    }

    @DisplayName("Testing method insert() on negative result")
    @Test
    void testInsertThrowsException() {
        String name = "travel";
        Tag tag = createTag(name);

        Assertions.assertThrows(ResourceAlreadyExistException.class,
                () -> tagService.insert(tagMapper.toDto(tag)));
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    void testUpdateSuccess() throws ResourceNotFoundException, ResourceAlreadyExistException {
        String newName = "sport for kids";

        String updatedTagName = "travel";
        Tag updatedTag = tagMapper.toEntity(tagService.findByName(updatedTagName));
        updatedTag.setName(newName);

        tagService.update(tagMapper.toDto(updatedTag));
        Assertions.assertEquals(newName, tagService.findByName(newName).getName());
    }

    @DisplayName("Testing method update() on negative result with ResourceNotFoundException")
    @Test
    void testUpdateThrowsResourceNotFoundException() {
        int id = 20;
        String newName = "sport";

        Tag updatedTag = createTag(newName);
        updatedTag.setId(id);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.update(tagMapper.toDto(updatedTag)));
    }

    @DisplayName("Testing method update() on negative result with ResourceAlreadyExistException")
    @Test
    void testUpdateThrowsResourceAlreadyExistException() {
        int id = 3;
        String newName = "rest";

        Tag updatedTag = createTag(newName);
        updatedTag.setId(id);

        Assertions.assertThrows(ResourceAlreadyExistException.class,
                () -> tagService.update(tagMapper.toDto(updatedTag)));
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void testFindAllSuccess() throws ResourceNotFoundException {
        List<TagDto> expectedTags = tagService.findAll(params);

        String name1 = "extreme";
        String name2 = "beauty";
        String name3 = "rest";
        String name4 = "sport";

        TagDto tagDto1 = tagMapper.toDto(createTag(name1));
        TagDto tagDto2 = tagMapper.toDto(createTag(name2));
        TagDto tagDto3 = tagMapper.toDto(createTag(name3));
        TagDto tagDto4 = tagMapper.toDto(createTag(name4));

        List<TagDto> actualTags = Arrays.asList(tagDto1, tagDto2, tagDto3, tagDto4);
        Assertions.assertEquals(actualTags, expectedTags);
    }

    @DisplayName("Testing method findAll() on negative result")
    @Test
    void testFindAllThrowsException() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.findAll(params));
    }

    private Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}
