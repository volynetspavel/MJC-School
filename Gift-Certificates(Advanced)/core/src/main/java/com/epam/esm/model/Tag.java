package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Tag is entity of tag.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Tag extends AbstractEntity<Integer> {

    private String name;
}
