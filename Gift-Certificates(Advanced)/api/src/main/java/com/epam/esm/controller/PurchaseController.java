package com.epam.esm.controller;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.hateoas.PurchaseHateoas;
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
    private PurchaseHateoas purchaseHateoas;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, PurchaseHateoas purchaseHateoas) {
        this.purchaseService = purchaseService;
        this.purchaseHateoas = purchaseHateoas;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Purchase makePurchase(@RequestBody PurchaseDto purchase)
            throws ResourceNotFoundException, ValidationException {
        Purchase newPurchase = purchaseService.makePurchase(purchase);
        purchaseHateoas.addLinksForPurchaseDtoWithUser(newPurchase);
        return newPurchase;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PurchaseDto> findPurchases(@RequestParam Map<String, String> params)
            throws ValidationException {
        List<PurchaseDto> purchases = purchaseService.findAll(params);
        purchaseHateoas.addLinksForListOfPurchaseDto(purchases);
        return purchases;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PurchaseDto findById(@PathVariable("id") BigInteger id) throws ResourceNotFoundException {
        PurchaseDto purchaseDto = purchaseService.findById(id);
        purchaseHateoas.addLinksForPurchaseDto(purchaseDto);
        return purchaseDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    public List<PurchaseDto> findPurchasesByUserId(@PathVariable("id") int userId,
                                                   @RequestParam Map<String, String> params)
            throws ResourceNotFoundException, ValidationException {
        List<PurchaseDto> purchases = purchaseService.findPurchasesByUser(userId, params);
        purchaseHateoas.addLinksForListOfPurchaseDto(purchases);
        return purchases;
    }
}
