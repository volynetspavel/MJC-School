package com.epam.esm.controller;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

/**
 * Class is used to send requests from the client to the service layer for purchase entity.
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PurchaseDto insert(@RequestBody PurchaseDto purchase) throws ResourceAlreadyExistException {
        return purchaseService.insert(purchase);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PurchaseDto> findAll() throws ResourceNotFoundException {
        return purchaseService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PurchaseDto findById(@PathVariable("id") BigInteger id) throws ResourceNotFoundException {
        return purchaseService.findById(id);
    }
}
