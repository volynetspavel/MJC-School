package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
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
@Audited
public class User extends AbstractEntity<Integer> {

    private String name;
    private String surname;

    @Column(unique = true)
    private String email;
}
