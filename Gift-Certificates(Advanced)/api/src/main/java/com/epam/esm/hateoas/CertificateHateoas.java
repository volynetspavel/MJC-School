package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Class for assemble links for object CertificateDto.
 */
@Component
public class CertificateHateoas {

    public void addLinksForCertificateDto(CertificateDto certificateDto) {

        Link selfLink = linkTo(CertificateController.class)
                .slash(certificateDto.getId())
                .withSelfRel();

        certificateDto.add(selfLink);
    }

    public CollectionModel<CertificateDto> addLinksForListOfCertificateDto(List<CertificateDto> certificates) {

        for (CertificateDto certificate : certificates) {
            addLinksForCertificateDto(certificate);
        }

        Link selfLink = linkTo(CertificateController.class)
                .withSelfRel();
        return CollectionModel.of(certificates, selfLink);
    }
}
