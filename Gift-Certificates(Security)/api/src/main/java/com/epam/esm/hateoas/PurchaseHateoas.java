package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.PurchaseController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.PurchaseDtoAfterOrder;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for assemble links for object PurchaseDto.
 */
@Component
public class PurchaseHateoas {

    private static final String USER = "user";
    private static final String CERTIFICATE = "certificate";
    private static final String TAG = "tag";

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

    public void addLinksForPurchaseDtoWithUser(PurchaseDtoAfterOrder newPurchase) {
        Link selfLink = linkTo(PurchaseController.class)
                .slash(newPurchase.getId())
                .withSelfRel();

        Link userLink = linkTo(methodOn(UserController.class)
                .findById(newPurchase.getUser().getId()))
                .withRel(USER);

        List<CertificateDto> certificates = newPurchase.getCertificates();
        for (int i = 0; i < certificates.size(); i++) {
            Link certificateLink = linkTo(methodOn(CertificateController.class)
                    .findById(certificates.get(i).getId()))
                    .withRel(CERTIFICATE);
            newPurchase.getCertificates().get(i).add(certificateLink);

            Set<TagDto> tags = newPurchase.getCertificates().get(i).getTags();
            for (TagDto tagDto : tags) {
                Link tagLink = linkTo(methodOn(TagController.class)
                        .findById(tagDto.getId()))
                        .withRel(TAG);

                if (!tagDto.hasLink(TAG)) {
                    tagDto.add(tagLink);
                }
            }
        }

        newPurchase.add(selfLink);
        newPurchase.add(userLink);
    }
}
