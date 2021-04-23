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

    @NotBlank(message = "Name must not be blank")
    @Pattern(regexp = "[A-Za-z \\-]+", message = "Name of tag must be according [A-Za-z \\-]+.")
    private String name;

    @NotBlank(message = "Description must not be blank")
    @Pattern(regexp = "[A-Za-z \\-]+", message = "Name of tag must be according [A-Za-z \\-]+.")
    private String description;

    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "0", message = "Enter certificate price over 0.")
    private BigDecimal price;

    @Min(value = 1, message = "Enter certificate duration more than 1 day")
    private Integer duration;

    @Null
    private String createDate;

    @Null
    private String lastUpdateDate;

    private List<TagDto> tags;

}
