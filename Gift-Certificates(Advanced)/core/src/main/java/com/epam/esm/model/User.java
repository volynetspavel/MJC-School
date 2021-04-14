package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User is an entity of user.
 */
@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity<Integer> {

    private String name;
    private String surname;
    private String email;
}
