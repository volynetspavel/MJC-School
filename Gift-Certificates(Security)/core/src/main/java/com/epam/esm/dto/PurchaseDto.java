package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Class-wrapper for purchase.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "purchase", collectionRelation = "purchases")
public class PurchaseDto extends AbstractDto<BigInteger> {

    @NotBlank
    @Email(regexp = "[A-Za-z0-9+_.-]+@(.+)")
    private String userEmail;

    @Null
    private BigDecimal cost;

    @Null
    private String purchaseDate;

    @NotEmpty
    private List<
            @NotBlank
            @Pattern(regexp = "[0-9A-Za-z \\p{Punct}]+") String> certificateNames;
}
