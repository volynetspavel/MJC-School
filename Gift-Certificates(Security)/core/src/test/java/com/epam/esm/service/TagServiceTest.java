package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validation.PaginationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Class for testing methods from service layer for tag.
 */
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDao tagDao;
    @Mock
    private TagMapper tagMapper;
    @Mock
    private PaginationValidator paginationValidator;

    @DisplayName("Testing method findById() on positive result")
    @Test
    void findByIdSuccessTest() throws ResourceNotFoundException {
        int id = 3;
        String name = "rest";
        Tag expected = createTag(id, name);
        TagDto expectedDto = createTagDto(id, name);

        when(tagDao.findById(id)).thenReturn(expected);
        when(tagMapper.toDto(expected)).thenReturn(expectedDto);
        TagDto actualDto = tagService.findById(id);

        assertEquals(expectedDto, actualDto);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    void findByIdThrowsExceptionTest() {
        int id = 10;
        when(tagDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.findById(id));
    }

    @DisplayName("Testing method delete() by id of tag on positive result")
    @Test
    void deleteByIdSuccessTest() throws ResourceNotFoundException {
        int id = 3;
        String name = "rest";
        Tag tag = createTag(id, name);

        when(tagDao.findById(id)).thenReturn(tag);
        tagService.delete(id);
        verify(tagDao, times(1)).delete(tag);
    }

    @DisplayName("Testing method delete() by id of tag on negative result")
    @Test
    void deleteByIdThrowsExceptionTest() {
        int id = 5;
        when(tagDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.findById(id));
    }

    @DisplayName("Testing method insert() on positive result")
    @Test
    void insertSuccessTest() throws ResourceAlreadyExistException {
        int id = 0;
        String name = "rest";
        TagDto newTagDto = createTagDto(id, name);
        when(tagDao.findByName(name)).thenReturn(null);
        Tag newTag = createTag(id, name);

        when(tagMapper.toEntity(newTagDto)).thenReturn(newTag);
        when(tagDao.insert(newTag)).thenReturn(newTag);
        when(tagMapper.toDto(newTag)).thenReturn(newTagDto);

        TagDto tagDtoAfterInsert = tagService.insert(newTagDto);
        assertEquals(newTagDto, tagDtoAfterInsert);
    }

    @DisplayName("Testing method insert() on negative result")
    @Test
    void insertThrowsExceptionTest() {
        int id = 3;
        String name = "rest";
        Tag newTag = createTag(id, name);
        TagDto newTagDto = createTagDto(id, name);

        when(tagDao.findByName(name)).thenReturn(newTag);

        assertThrows(ResourceAlreadyExistException.class,
                () -> tagService.insert(newTagDto));
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    void updateSuccessTest() throws ResourceNotFoundException, ResourceAlreadyExistException {
        int id = 3;
        String newName = "rest for adult";
        TagDto newTagDto = createTagDto(id, newName);

        String oldName = "rest for child";
        Tag tagFromDataBase = createTag(id, oldName);

        when(tagDao.findById(id)).thenReturn(tagFromDataBase);
        when(tagDao.findByName(newName)).thenReturn(null);

        Tag newTagFromDataBase = createTag(id, newName);
        when(tagMapper.toEntity(newTagDto)).thenReturn(newTagFromDataBase);

        Tag updatesTagFromDataBase = createTag(id, newName);
        when(tagDao.findById(id)).thenReturn(updatesTagFromDataBase);
        when(tagMapper.toDto(updatesTagFromDataBase)).thenReturn(newTagDto);

        TagDto tagDtoAfterUpdate = tagService.update(newTagDto);
        assertEquals(tagDtoAfterUpdate, newTagDto);
    }

    @DisplayName("Testing method update() on negative result with ResourceNotFoundException")
    @Test
    void updateThrowsResourceNotFoundExceptionTest() {
        int id = 20;
        String newName = "sport";
        TagDto newTagDto = createTagDto(id, newName);

        when(tagDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> tagService.update(newTagDto));
    }

    @DisplayName("Testing method update() on negative result with ResourceAlreadyExistException")
    @Test
    void updateThrowsResourceAlreadyExistExceptionTest() {
        int id = 20;
        String newName = "sport";
        TagDto newTagDto = createTagDto(id, newName);

        String oldName = "rest for child";
        Tag tagFromDb = createTag(id, oldName);
        when(tagDao.findById(id)).thenReturn(tagFromDb);

        Tag tagWithSameName = createTag(id, newName);
        when(tagDao.findByName(newName)).thenReturn(tagWithSameName);

        assertThrows(ResourceAlreadyExistException.class,
                () -> tagService.update(newTagDto));
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void findAllSuccessTest() throws ValidationParametersException {

        int id1 = 1;
        String name1 = "extreme";
        Tag tag1 = createTag(id1, name1);

        int id2 = 2;
        String name2 = "beauty";
        Tag tag2 = createTag(id2, name2);

        int id3 = 3;
        String name3 = "rest";
        Tag tag3 = createTag(id3, name3);

        List<Tag> expectedTagList = Arrays.asList(tag1, tag2, tag3);

        TagDto tagDto1 = createTagDto(id1, name1);
        TagDto tagDto2 = createTagDto(id2, name2);
        TagDto tagDto3 = createTagDto(id3, name3);

        List<TagDto> expectedTagDtoList = Arrays.asList(tagDto1, tagDto2, tagDto3);

        int offset = 0;
        int limit = 3;
        when(tagDao.getCount()).thenReturn(3);
        when(paginationValidator.validatePaginationParameters(new HashMap<>())).thenReturn(false);

        when(tagDao.findAll(offset, limit)).thenReturn(expectedTagList);
        when(tagMapper.toDto(tag1)).thenReturn(tagDto1);
        when(tagMapper.toDto(tag2)).thenReturn(tagDto2);
        when(tagMapper.toDto(tag3)).thenReturn(tagDto3);

        List<TagDto> actualTagDtoList = tagService.findAll(new HashMap<>());

        assertEquals(expectedTagDtoList, actualTagDtoList);
    }

    private Tag createTag(int id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        return tag;
    }

    private TagDto createTagDto(int id, String name) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagDto.setName(name);

        return tagDto;
    }
}
