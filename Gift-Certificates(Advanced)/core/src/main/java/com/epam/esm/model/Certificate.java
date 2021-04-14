package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Certificate is entity of certificate.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Certificate extends AbstractEntity<Integer> {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
}
