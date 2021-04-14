package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.model.Order;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an implementation of OrderService.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private OrderMapper orderMapper;

    public OrderServiceImpl() {
    }

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderMapper orderMapper) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public OrderDto insert(OrderDto orderDto) throws ResourceAlreadyExistException {
/*        if (orderDao.findByName(orderDto.getName()) != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + orderDto.getName() + ") has already existed.");
        }
        Order order = orderMapper.toEntity(orderDto);
        int idNewOrder = orderDao.insert(order);

        return orderMapper.toDto(orderDao.findById(idNewOrder));*/
        return null;
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {

    }

    @Override
    public OrderDto update(OrderDto entityDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return null;
    }

    @Override
    public List<OrderDto> findAll() throws ResourceNotFoundException {
        List<Order> orders = orderDao.findAll();
        if (orders == null || orders.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found");
        }
        return orders.stream()
                .map(order -> orderMapper.toDto(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(BigInteger id) throws ResourceNotFoundException {
        Order order = orderDao.findById(id);
        if (order == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        return orderMapper.toDto(order);
    }
}
