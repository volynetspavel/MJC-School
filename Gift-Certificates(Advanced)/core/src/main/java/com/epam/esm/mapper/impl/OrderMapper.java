package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.mapper.AbstractMapper;
import com.epam.esm.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for order entity.
 */
@Component
public class OrderMapper extends AbstractMapper<Order, OrderDto> {

    @Autowired
    public OrderMapper() {
        super(Order.class, OrderDto.class);
    }
}
