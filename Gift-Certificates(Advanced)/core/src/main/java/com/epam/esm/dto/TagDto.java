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

    @NotBlank(message = "Name must not be blank")
    @Pattern(regexp = "[A-Za-z0-9 \\-]+", message = "Name of tag must be according [A-Za-z0-9 \\-]+.")
    private String name;
}
