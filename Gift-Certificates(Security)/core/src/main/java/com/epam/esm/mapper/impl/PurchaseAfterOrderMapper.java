package com.epam.esm.mapper.impl;

import com.epam.esm.dto.PurchaseDtoAfterOrder;
import com.epam.esm.mapper.AbstractMapper;
import com.epam.esm.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for purchase entity after make order.
 */
@Component
public class PurchaseAfterOrderMapper extends AbstractMapper<Purchase, PurchaseDtoAfterOrder> {

    @Autowired
    public PurchaseAfterOrderMapper() {
        super(Purchase.class, PurchaseDtoAfterOrder.class);
    }
}
