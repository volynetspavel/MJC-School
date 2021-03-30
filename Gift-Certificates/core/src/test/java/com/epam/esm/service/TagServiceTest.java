package com.epam.esm.service;

import com.epam.esm.config.TestDBConfig;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void testFindById() {
        Tag actual = createTag();
        Tag expected = tagService.findById(1);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDelete(){
        String name = "sport";
        tagService.delete(name);
        Tag tag = tagService.findByName(name);
        Assertions.assertNull(tag);
    }

    @Test
    public void testInsert(){
        Tag tag = new Tag();
        String name = "sport";
        tag.setName(name);

        tagService.insert(tag);
        Assertions.assertEquals(name, tagService.findByName(name).getName());
    }

    @Test
    public void testUpdate(){
        String newName = "sport for kids";

        String updatedTagName = "sport";
        Tag updatedtag = tagService.findByName(updatedTagName);
        updatedtag.setName(newName);

        tagService.update(updatedtag);
        Assertions.assertEquals(newName, tagService.findByName(newName).getName());
    }

    @Test
    public void testFindAll(){
        List<Tag> expectedTags = tagService.findAll();

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        Tag tag4 = new Tag();
        tag1.setName("extreme");
        tag2.setName("beauty");
        tag3.setName("rest");
        tag4.setName("sport for kids");

        List<Tag> actualTags = new ArrayList<Tag>(){
            {
                add(tag1);
                add(tag2);
                add(tag3);
                add(tag4);
            }
        };
        Assertions.assertEquals(actualTags, expectedTags);
    }

    private Tag createTag() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("extreme");
        return tag;
    }
}
