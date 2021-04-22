package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Tag is entity of tag.
 */
@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Audited
public class Tag extends AbstractEntity<Integer> {

    @Column(unique = true)
    private String name;

}
