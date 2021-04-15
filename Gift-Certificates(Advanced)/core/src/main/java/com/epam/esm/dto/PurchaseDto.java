package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class-wrapper for purchase.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseDto extends AbstractDto {

    private String userEmail;
    private BigDecimal cost;
    private String purchaseDate;
    private List<String> certificateNames;
}
