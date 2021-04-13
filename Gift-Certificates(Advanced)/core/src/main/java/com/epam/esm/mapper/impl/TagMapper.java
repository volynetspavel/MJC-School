package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.AbstractMapper;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for tag entity.
 */
@Component
public class TagMapper extends AbstractMapper<Tag, TagDto> {

    @Autowired
    public TagMapper() {
        super(Tag.class, TagDto.class);
    }
}
