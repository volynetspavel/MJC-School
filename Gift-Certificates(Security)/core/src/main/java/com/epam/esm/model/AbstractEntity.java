package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Entity is a base class of all other entities.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractEntity<T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected T id;
}
