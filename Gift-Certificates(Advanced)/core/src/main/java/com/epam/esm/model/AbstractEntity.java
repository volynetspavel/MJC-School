package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

/**
 * Entity is a base class of all other entities.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractEntity<T extends Number> extends RepresentationModel<AbstractEntity<Number>> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected T id;
}
