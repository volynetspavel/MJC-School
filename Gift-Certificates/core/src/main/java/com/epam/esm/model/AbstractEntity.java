package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity is a base class of all other entities.
 */
@Data
@NoArgsConstructor
public abstract class AbstractEntity {

    private int id;
}
