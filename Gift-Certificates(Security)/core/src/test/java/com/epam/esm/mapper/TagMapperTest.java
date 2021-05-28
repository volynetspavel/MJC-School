package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.when;
/**
 * Class for testing tag mapper.
 */
@ExtendWith(MockitoExtension.class)
class TagMapperTest {

    @InjectMocks
    private TagMapper tagMapper;
    @Mock
    private ModelMapper mapper;

    @DisplayName("Testing method toEntity() on positive result")
    @Test
    void toEntitySuccessTest() {
        Tag expectedTag = getTag();
        TagDto tagDto = getTagDto();

        when(mapper.map(tagDto, Tag.class)).thenReturn(expectedTag);
        when(tagMapper.toEntity(tagDto)).thenReturn(expectedTag);

        Tag actualTag = tagMapper.toEntity(tagDto);
        Assertions.assertEquals(expectedTag, actualTag);
    }

    @DisplayName("Testing method toEntity() on negative result, when tag is null")
    @Test
    void toEntityNegativeTest() {
        TagDto tagDto = null;

        Tag actualTag = tagMapper.toEntity(tagDto);
        Assertions.assertNull(actualTag);
    }

    @DisplayName("Testing method toDto() on positive result")
    @Test
    void toDtoSuccessTest() {
        Tag tag = getTag();
        TagDto expectedTagDto = getTagDto();

        when(mapper.map(tag, TagDto.class)).thenReturn(expectedTagDto);
        when(tagMapper.toDto(tag)).thenReturn(expectedTagDto);

        TagDto actualTagDto = tagMapper.toDto(tag);
        Assertions.assertEquals(expectedTagDto, actualTagDto);
    }


    @DisplayName("Testing method toDto() on negative result, when TagDto is null")
    @Test
    void toDtoNegativeTest() {
        Tag tag = null;

        TagDto actualTagDto = tagMapper.toDto(tag);
        Assertions.assertNull(actualTagDto);
    }

    private Tag getTag() {
        int id = 1;
        String name = "extreme";
        return createTag(id, name);
    }

    private TagDto getTagDto() {
        int id = 1;
        String name = "extreme";
        return createTagDto(id, name);
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
