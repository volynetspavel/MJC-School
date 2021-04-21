package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 * Abstract class for data transfer object classes.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AbstractDto<T extends Number> extends RepresentationModel<AbstractDto<Number>> {

    private T id;
}
