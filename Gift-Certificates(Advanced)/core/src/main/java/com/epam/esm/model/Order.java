package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Order is an entity of order.
 */

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends AbstractEntity<BigInteger> {

    @ManyToOne
    @JoinColumn
    private User user;

    private BigDecimal cost;

    @Column(name = "purchase_date")
    private String purchaseDate;

/*    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_gift_certificate",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "gift_certificate_id")}
    )
    private List<Certificate> certificates;*/
}
