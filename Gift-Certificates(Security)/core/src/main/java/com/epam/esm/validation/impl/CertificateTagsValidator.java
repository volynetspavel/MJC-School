package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;

import static com.epam.esm.constant.CodeException.NULL_FIELDS_LESS_THEN_ONE;
import static com.epam.esm.constant.CodeException.NULL_FIELDS_MORE_THEN_ONE;

/**
 * Class checks 'tags' field on null.
 */
public class CertificateTagsValidator extends FieldValidator<CertificateDto> {

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        if (certificateDto.getTags() != null && !certificateDto.getTags().isEmpty()) {
            countFieldsNotNull++;
        }
        checkCountOfFieldsOnNull(countFieldsNotNull, NULL_FIELDS_LESS_THEN_ONE);
    }

    @Override
    public void isCountFieldsEqualNullMoreOne(CertificateDto certificateDto, int countNullFields) throws ServiceException {
        if (certificateDto.getTags().isEmpty()) {
            countNullFields++;
        }
        checkCountOfFieldsOnNull(countNullFields, NULL_FIELDS_MORE_THEN_ONE);
    }
}
