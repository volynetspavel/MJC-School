package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.model.Purchase;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * This class is a layer for interacting with PurchaseDao.
 */
public abstract class PurchaseService {

    public abstract Purchase makePurchase(PurchaseDto purchaseDto) throws ResourceNotFoundException;

    public abstract PurchaseDto findById(BigInteger id) throws ResourceNotFoundException;

    public abstract List<PurchaseDto> findPurchasesByUserId(int userId, Map<String, String> params)
            throws ResourceNotFoundException, ValidationParametersException;
}
