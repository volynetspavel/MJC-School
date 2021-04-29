package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;

import static com.epam.esm.constant.CodeException.NULL_FIELDS_LESS_THEN_ONE;
import static com.epam.esm.constant.CodeException.NULL_FIELDS_MORE_THEN_ONE;

/**
 * Class checks 'duration' field on null.
 */
public class CertificateDurationValidator extends FieldValidator<CertificateDto> {

    {
        this.setNextFieldValidator(new CertificateTagsValidator());
    }

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        if (certificateDto.getDuration() != null) {
            countFieldsNotNull++;
        }
        checkCountOfFieldsOnNull(countFieldsNotNull, NULL_FIELDS_LESS_THEN_ONE);
        getNextFieldValidator().isCountFieldsEqualNullLessOne(certificateDto, countFieldsNotNull);
    }

    @Override
    public void isCountFieldsEqualNullMoreOne(CertificateDto certificateDto, int countNullFields) throws ServiceException {
        if (certificateDto.getDuration() == null) {
            countNullFields++;
        }
        checkCountOfFieldsOnNull(countNullFields, NULL_FIELDS_MORE_THEN_ONE);
        getNextFieldValidator().isCountFieldsEqualNullMoreOne(certificateDto, countNullFields);
    }
}