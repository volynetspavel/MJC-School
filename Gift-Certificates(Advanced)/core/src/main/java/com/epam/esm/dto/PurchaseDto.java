package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.*;
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

    @NotBlank(message = "User email must not be blank.")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$]", message = "Please, enter correct email.")
    private String userEmail;
    @Null
    private BigDecimal cost;
    @Null
    private String purchaseDate;
    @NotEmpty(message = "Certificate names must not be blank.")
    private List<
            @NotBlank(message = "Certificate names must not be blank.")
            @Pattern(regexp = "[0-9A-Za-z ]+", message = "Name of certificate is incorrect.") String> certificateNames;
}
