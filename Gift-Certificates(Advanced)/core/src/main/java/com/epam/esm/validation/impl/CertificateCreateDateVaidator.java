package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;

/**
 * Class checks 'create date' field on null.
 */
public class CertificateCreateDateVaidator extends FieldValidator<CertificateDto> {

    {
        this.setNextFieldValidator(new CertificateLastUpdateDateVaidator());
    }

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        if (certificateDto.getCreateDate() != null) {
            countFieldsNotNull++;
        }
        checkCountOfFieldsWhichNotNull(countFieldsNotNull);
        getNextFieldValidator().isCountFieldsEqualNullLessOne(certificateDto, countFieldsNotNull);
    }
}
