package com.epam.esm.controller;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Purchase;
import com.epam.esm.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
    public Purchase makePurchase(@RequestBody PurchaseDto purchase) {
        return purchaseService.makePurchase(purchase);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PurchaseDto> findUserPurchases(@RequestParam Map<String, String> params) throws ResourceNotFoundException {
        return purchaseService.findAll(params);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PurchaseDto findById(@PathVariable("id") BigInteger id) throws ResourceNotFoundException {
        return purchaseService.findById(id);
    }
}
