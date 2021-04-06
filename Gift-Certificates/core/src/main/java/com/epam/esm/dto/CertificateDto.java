package com.epam.esm.dto;

import com.epam.esm.model.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class-wrapper for certificate.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateDto extends AbstractDto {
    
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<Tag> tags;

    public CertificateDto() {
    }
}
