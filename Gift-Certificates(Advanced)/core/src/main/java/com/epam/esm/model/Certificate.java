package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Certificate is entity of certificate.
 */
@Entity
@Table(name = "gift_certificate")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Audited
public class Certificate extends AbstractEntity<Integer> {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "gift_certificate_has_tag",
            joinColumns = {@JoinColumn(name = "gift_certificate_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags = new ArrayList<>();

}
