package com.epam.esm.dto;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.User;
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

    private User user;
    private BigDecimal cost;
    private String purchaseDate;
    private List<Certificate> certificates;
}
