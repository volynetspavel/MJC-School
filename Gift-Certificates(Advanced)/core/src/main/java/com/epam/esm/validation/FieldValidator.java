package com.epam.esm.validation;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

/**
 * Contains method for validating data of entities by chain of responsibility.
 */
@Component
public abstract class FieldValidator<T extends AbstractDto<? extends Number>> {

    private FieldValidator nextFieldValidator;

    public void setNextFieldValidator(FieldValidator nextFieldValidator) {
        this.nextFieldValidator = nextFieldValidator;
    }

    public FieldValidator getNextFieldValidator() {
        return nextFieldValidator;
    }

    public abstract void isCountFieldsEqualNullLessOne(T dto, int countFieldsNotNull) throws ServiceException;

    public void checkCountOfFieldsWhichNotNull(int countFieldsNotNull) throws ServiceException {
        if (countFieldsNotNull > 1) {
            throw new ServiceException("Request contains more than one field.");
        }
    }
}
