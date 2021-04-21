package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.PurchaseController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Purchase;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for assemble links for object PurchaseDto.
 */
@Component
public class PurchaseHateoas {

    private static final String USER = "user";

    public void addLinksForPurchaseDto(PurchaseDto purchaseDto) {

        Link selfLink = linkTo(PurchaseController.class)
                .slash(purchaseDto.getId())
                .withSelfRel();
        purchaseDto.add(selfLink);
    }

    public CollectionModel<PurchaseDto> addLinksForListOfPurchaseDto(List<PurchaseDto> purchases) {

        for (PurchaseDto purchase : purchases) {
            addLinksForPurchaseDto(purchase);
        }

        Link selfLink = linkTo(PurchaseController.class)
                .withSelfRel();
        return CollectionModel.of(purchases, selfLink);
    }

    public void addLinksForPurchaseDtoWithUser(Purchase newPurchase) throws ResourceNotFoundException {
        Link selfLink = linkTo(PurchaseController.class)
                .slash(newPurchase.getId())
                .withSelfRel();

        Link userLink = linkTo(methodOn(UserController.class)
                .findById(newPurchase.getUser().getId()))
                .withRel(USER);

        newPurchase.add(selfLink);
        newPurchase.add(userLink);
    }
}
