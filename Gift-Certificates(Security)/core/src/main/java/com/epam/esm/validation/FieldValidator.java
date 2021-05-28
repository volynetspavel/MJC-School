package com.epam.esm.validation;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ServiceException;

import static com.epam.esm.constant.CodeException.NULL_FIELDS_LESS_THEN_ONE;
import static com.epam.esm.constant.CodeException.NULL_FIELDS_MORE_THEN_ONE;

/**
 * Contains method for validating data of entities by chain of responsibility.
 */
public interface FieldValidator<T extends AbstractDto<? extends Number>> {

    void isCountFieldsEqualNullLessOne(T dto);

    void isCountFieldsEqualNullMoreOne(T dto) throws ServiceException;

    default void checkCountOfFieldsNotEqualsNull(int countFields) throws ServiceException {
        if (countFields > 1) {
            CertificateFieldValidator.setCounter(0);
            throw new ServiceException(NULL_FIELDS_LESS_THEN_ONE);
        }
    }

    default void checkCountOfFieldsEqualsNull(int countFields) throws ServiceException {
        if (countFields >= 1) {
            CertificateFieldValidator.setCounter(0);
            throw new ServiceException(NULL_FIELDS_MORE_THEN_ONE);
        }
    }
}
