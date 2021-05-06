package com.epam.esm.controller;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.hateoas.PurchaseHateoas;
import com.epam.esm.model.Purchase;
import com.epam.esm.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Class is used to send requests from the client to the service layer for purchase entity.
 */
@RestController
@RequestMapping("/purchases")
@Validated
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
    public Purchase makePurchase(@Valid @RequestBody PurchaseDto purchase)
            throws ResourceNotFoundException, ValidationParametersException {
        Purchase newPurchase = purchaseService.makePurchase(purchase);
        purchaseHateoas.addLinksForPurchaseDtoWithUser(newPurchase);
        return newPurchase;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PurchaseDto findById(@PathVariable("id") @Min(value = 1, message = "Enter id more than one.")
                                        BigInteger id) throws ResourceNotFoundException {
        PurchaseDto purchaseDto = purchaseService.findById(id);
        purchaseHateoas.addLinksForPurchaseDto(purchaseDto);
        return purchaseDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    public List<PurchaseDto> findPurchasesByUserId(@PathVariable("id")
                                                   @Min(value = 1, message = "Enter id more than one.")
                                                           int userId,
                                                   @RequestParam Map<String, String> params)
            throws ResourceNotFoundException, ValidationParametersException {
        List<PurchaseDto> purchases = purchaseService.findPurchasesByUserId(userId, params);
        purchaseHateoas.addLinksForListOfPurchaseDto(purchases);
        return purchases;
    }
}
