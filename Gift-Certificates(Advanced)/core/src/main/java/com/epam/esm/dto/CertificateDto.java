package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class-wrapper for certificate.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class CertificateDto extends AbstractDto<Integer> {

    @Pattern(regexp = "[0-9A-Za-z !?\\.]+")
    private String name;

    @Pattern(regexp = "[0-9A-Za-z !?\\.]+")
    private String description;

    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "1")
    private BigDecimal price;

    @Min(value = 1)
    private Integer duration;

    @Null
    private String createDate;

    @Null
    private String lastUpdateDate;

    private List<TagDto> tags = new ArrayList<>();
}
