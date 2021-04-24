package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class-wrapper for certificate.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class CertificateDto extends AbstractDto<Integer> {

    @Pattern(regexp = "[0-9A-Za-z \\p{Punct}]+", message = "Name must be according [0-9A-Za-z \\p{Punct}]+")
    private String name;

    @Pattern(regexp = "[0-9A-Za-z \\p{Punct}]+", message = "Description must be according [0-9A-Za-z \\p{Punct}]+")
    private String description;

    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "0", message = "Enter certificate price over 0.")
    private BigDecimal price;

    @Min(value = 1, message = "Enter certificate duration more than 1 day")
    private Integer duration;

    @Null(message = "You cannot change createDate field.")
    private String createDate;

    @Null(message = "You cannot set lastUpdateDate field.")
    private String lastUpdateDate;

    private List<TagDto> tags;

}
