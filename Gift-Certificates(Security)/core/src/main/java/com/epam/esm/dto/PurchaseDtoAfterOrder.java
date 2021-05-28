package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Class-wrapper for purchase after make purchase.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "purchase", collectionRelation = "purchases")
public class PurchaseDtoAfterOrder extends AbstractDto<BigInteger> {

    private UserDto user;
    private BigDecimal cost;
    private String purchaseDate;
    private List<CertificateDto> certificates = new ArrayList<>();
}

