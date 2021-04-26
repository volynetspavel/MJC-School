package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;

/**
 * Class checks 'description' field on null.
 */
public class CertificateDescriptionValidator extends FieldValidator<CertificateDto> {

    {
        this.setNextFieldValidator(new CertificatePriceValidator());
    }

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        if (certificateDto.getDescription() != null) {
            countFieldsNotNull++;
        }
        checkCountOfFieldsWhichNotNull(countFieldsNotNull);
        getNextFieldValidator().isCountFieldsEqualNullLessOne(certificateDto, countFieldsNotNull);
    }
}
