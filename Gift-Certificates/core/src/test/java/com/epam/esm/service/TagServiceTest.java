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

import java.util.ArrayList;
import java.util.List;

/**
 * Class for testing methods from service layer for tag.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagMapper tagMapper;


    @DisplayName("Testing method findById() on positive result")
    @Test
    public void testFindByIdSuccess() throws ResourceNotFoundException {
        int id = 2;
        String name = "beauty";

        Tag actual = new Tag();
        actual.setId(id);
        actual.setName(name);

        Tag expected = tagMapper.toEntity(tagService.findById(id));
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

        tagService.insert(tagMapper.toDto(tag));
        Assertions.assertEquals(name, tagService.findByName(name).getName());
    }

    @DisplayName("Testing method insert() on negative result")
    @Test
    public void testInsertThrowsException() {
        Tag tag = new Tag();
        String name = "travel";
        tag.setName(name);

        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            tagService.insert(tagMapper.toDto(tag));
        });
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    public void testUpdateSuccess() throws ResourceNotFoundException, ResourceAlreadyExistException {
        String newName = "sport for kids";

        String updatedTagName = "travel";
        Tag updatedTag = tagMapper.toEntity(tagService.findByName(updatedTagName));
        updatedTag.setName(newName);

        tagService.update(tagMapper.toDto(updatedTag));
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
            tagService.update(tagMapper.toDto(updatedTag));
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
            tagService.update(tagMapper.toDto(updatedTag));
        });
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    public void testFindAllSuccess() throws ResourceNotFoundException {
        List<TagDto> expectedTagDtos = tagService.findAll();

        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();
        TagDto tagDto3 = new TagDto();
        TagDto tagDto4 = new TagDto();
        tagDto1.setName("extreme");
        tagDto2.setName("beauty");
        tagDto3.setName("rest");
        tagDto4.setName("sport for kids");

        List<TagDto> actualTagDtos = new ArrayList<TagDto>() {
            {
//                add(tagDto1);
                add(tagDto2);
                add(tagDto3);
                add(tagDto4);
            }
        };
        Assertions.assertEquals(actualTagDtos, expectedTagDtos);
    }

    @DisplayName("Testing method findAll() on negative result")
    @Test
    public void testFindAllThrowsException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagService.findAll();
        });
    }
}
