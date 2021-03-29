package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Tag is entity of tag.
 */
@Data
@NoArgsConstructor
public class Tag extends AbstractEntity {
    private String name;
}
