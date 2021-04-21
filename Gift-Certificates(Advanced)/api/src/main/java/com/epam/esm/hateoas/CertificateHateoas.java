package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for assemble links for object CertificateDto.
 */
@Component
public class CertificateHateoas {

    private static final String TAG = "tag";

    public void addLinksForCertificateDto(CertificateDto certificateDto)
            throws ResourceNotFoundException, ValidationException {

        Link selfLink = linkTo(CertificateController.class)
                .slash(certificateDto.getId())
                .withSelfRel();

        List<TagDto> tagDtoList = certificateDto.getTags();
        for (TagDto tagDto : tagDtoList) {
            Link tagLink = linkTo(methodOn(TagController.class)
                    .findById(tagDto.getId()))
                    .withRel(TAG);
            tagDto.add(tagLink);
        }
        certificateDto.setTags(tagDtoList);
        certificateDto.add(selfLink);
    }

    public CollectionModel<CertificateDto> addLinksForListOfCertificateDto(List<CertificateDto> certificates)
            throws ResourceNotFoundException, ValidationException {

        for (CertificateDto certificate : certificates) {
            addLinksForCertificateDto(certificate);
        }

        Link selfLink = linkTo(CertificateController.class)
                .withSelfRel();
        return CollectionModel.of(certificates, selfLink);
    }
}
