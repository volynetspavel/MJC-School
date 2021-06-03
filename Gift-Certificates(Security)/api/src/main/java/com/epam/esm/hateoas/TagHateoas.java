package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for assemble links for object TagDto.
 */
@Component
public class TagHateoas {

    private static final String TAG_NAME = "tag_name";
    private static final String CERTIFICATES = "certificates";
    private static final String USER = "user";

    public void addLinksForTagDto(TagDto tagDto) {

        Link selfLink = linkTo(TagController.class)
                .slash(tagDto.getId())
                .withSelfRel();

        Map<String, String> params = new HashMap<>();
        params.put(TAG_NAME, tagDto.getName());

        Link certificates = linkTo(methodOn(CertificateController.class)
                .findCertificatesByParams(params))
                .withRel(CERTIFICATES);

        tagDto.add(selfLink);
        tagDto.add(certificates);
    }

    public CollectionModel<TagDto> addLinksForListOfTagDto(List<TagDto> tags) {

        for (TagDto tag : tags) {
            addLinksForTagDto(tag);
        }

        Link selfLink = linkTo(TagController.class)
                .withSelfRel();
        return CollectionModel.of(tags, selfLink);
    }

    public void addLinksForUser(TagDto tagDto, int userId) {

        Link userLink = linkTo(methodOn(UserController.class)
                .findById(userId))
                .withRel(USER);
        tagDto.add(userLink);
    }
}
