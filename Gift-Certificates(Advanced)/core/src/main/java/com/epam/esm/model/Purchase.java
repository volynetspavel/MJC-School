package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Purchase is an entity of purchase.
 */

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Purchase extends AbstractEntity<BigInteger> {

    @ManyToOne
    @JoinColumn
    private User user;
    private BigDecimal cost;

    @Column(name = "purchase_date")
    private String purchaseDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "purchase_gift_certificate",
            joinColumns = {@JoinColumn(name = "purchase_id")},
            inverseJoinColumns = {@JoinColumn(name = "gift_certificate_id")}
    )
    private List<Certificate> certificates = new ArrayList<>();
}
