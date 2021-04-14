package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * This class is a layer for interacting with PurchaseDao.
 */
public interface PurchaseService extends Service<PurchaseDto> {

    PurchaseDto findById(BigInteger id) throws ResourceNotFoundException;

    default PurchaseDto findById(int id) {
        return null;
    }
}
