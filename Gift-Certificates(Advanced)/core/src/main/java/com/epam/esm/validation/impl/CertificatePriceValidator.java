package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;

/**
 * Class checks 'price' field on null.
 */
public class CertificatePriceValidator extends FieldValidator<CertificateDto> {

    {
        this.setNextFieldValidator(new CertificateDurationValidator());
    }

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        if (certificateDto.getPrice() != null) {
            countFieldsNotNull++;
        }
        checkCountOfFieldsWhichNotNull(countFieldsNotNull);
        getNextFieldValidator().isCountFieldsEqualNullLessOne(certificateDto, countFieldsNotNull);
    }
}
