package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Class-wrapper for purchase.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseDto extends AbstractDto<BigInteger> {

    private String userEmail;
    private BigDecimal cost;
    private String purchaseDate;
    private List<String> certificateNames;
}
