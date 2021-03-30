package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tag is entity of tag.
 */
@Data
@NoArgsConstructor
public class Tag extends AbstractEntity {
    private String name;
}
