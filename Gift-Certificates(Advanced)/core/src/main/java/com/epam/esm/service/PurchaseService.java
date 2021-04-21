package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Purchase;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * This class is a layer for interacting with PurchaseDao.
 */
public interface PurchaseService extends Service<PurchaseDto, Purchase> {

    PurchaseDto findById(BigInteger id) throws ResourceNotFoundException;

    default PurchaseDto findById(int id) {
        return null;
    }

    Purchase makePurchase(PurchaseDto purchaseDto);

    @Override
    default PurchaseDto insert(PurchaseDto entityDto) {
        return null;
    }

    @Override
    default void delete(int id) {
    }

    @Override
    default PurchaseDto update(PurchaseDto entityDto) {
        return null;
    }

    List<PurchaseDto> findPurchasesByUser(int userId, Map<String, String> params)
            throws ResourceNotFoundException, ValidationException;
}
