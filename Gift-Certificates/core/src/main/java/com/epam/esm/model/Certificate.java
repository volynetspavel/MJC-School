package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Certificate is entity of certificate.
 */
@Data
@NoArgsConstructor
public class Certificate extends AbstractEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createTime;
    private String lastUpdateTime;
}
