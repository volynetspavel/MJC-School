package com.epam.esm.mapper.impl;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.mapper.AbstractMapper;
import com.epam.esm.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for purchase entity.
 */
@Component
public class PurchaseMapper extends AbstractMapper<Purchase, PurchaseDto> {

    @Autowired
    public PurchaseMapper() {
        super(Purchase.class, PurchaseDto.class);
    }
}
