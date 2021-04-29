package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Class-wrapper for tag.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagDto extends AbstractDto<Integer> {

    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9 \\-]+")
    private String name;
}
