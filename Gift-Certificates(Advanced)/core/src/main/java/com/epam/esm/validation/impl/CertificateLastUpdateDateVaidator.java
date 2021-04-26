package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;

/**
 * Class checks 'last update field' field on null.
 */
public class CertificateLastUpdateDateVaidator extends FieldValidator<CertificateDto> {

    {
        this.setNextFieldValidator(new CertificateTagsValidator());
    }

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        if (certificateDto.getLastUpdateDate() != null) {
            countFieldsNotNull++;
        }
        checkCountOfFieldsWhichNotNull(countFieldsNotNull);
        getNextFieldValidator().isCountFieldsEqualNullLessOne(certificateDto, countFieldsNotNull);
    }
}
