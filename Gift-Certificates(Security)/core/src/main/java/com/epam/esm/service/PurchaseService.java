package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.PurchaseDtoAfterOrder;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;

import java.math.BigInteger;
import java.util.List;

/**
 * This class is a layer for interacting with PurchaseDao.
 */
public abstract class PurchaseService {

    public abstract PurchaseDtoAfterOrder makePurchase(PurchaseDto purchaseDto) throws ResourceNotFoundException;

    public abstract PurchaseDto findById(BigInteger id) throws ResourceNotFoundException;

    public abstract List<PurchaseDto> findPurchasesByUserId(int userId)
            throws ResourceNotFoundException, ValidationParametersException;
}
