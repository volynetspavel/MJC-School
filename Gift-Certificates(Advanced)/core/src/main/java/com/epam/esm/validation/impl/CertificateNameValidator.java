package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.FieldValidator;
import org.springframework.stereotype.Component;

/**
 * Class checks 'name' field on null.
 */
@Component
public class CertificateNameValidator extends FieldValidator<CertificateDto> {

    {
        this.setNextFieldValidator(new CertificateDescriptionValidator());
    }

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto certificateDto,
                                              int countFieldsNotNull) throws ServiceException {
        countFieldsNotNull = 0;
        if (certificateDto.getName() != null) {
            countFieldsNotNull++;
        }
        getNextFieldValidator().isCountFieldsEqualNullLessOne(certificateDto, countFieldsNotNull);
    }

    @Override
    public void isCountFieldsEqualNullMoreOne(CertificateDto certificateDto, int countNullFields) throws ServiceException {
        if (certificateDto.getName() == null) {
            countNullFields++;
        }
        getNextFieldValidator().isCountFieldsEqualNullMoreOne(certificateDto, countNullFields);
    }
}
