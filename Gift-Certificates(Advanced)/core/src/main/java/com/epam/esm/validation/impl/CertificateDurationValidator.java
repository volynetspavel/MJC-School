package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;

/**
 * Class checks 'duration' field on null.
 */
public class CertificateDurationValidator extends FieldValidator<CertificateDto> {

    {
        this.setNextFieldValidator(new CertificateCreateDateVaidator());
    }

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        if (certificateDto.getDuration() != null) {
            countFieldsNotNull++;
        }
        checkCountOfFieldsWhichNotNull(countFieldsNotNull);
        getNextFieldValidator().isCountFieldsEqualNullLessOne(certificateDto, countFieldsNotNull);
    }
}