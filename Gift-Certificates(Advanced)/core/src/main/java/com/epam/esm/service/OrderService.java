package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * This class is a layer for interacting with OrderDao.
 */
public interface OrderService extends Service<OrderDto> {

    OrderDto findById(BigInteger id) throws ResourceNotFoundException;

    default OrderDto findById(int id) {
        return null;
    }
}
