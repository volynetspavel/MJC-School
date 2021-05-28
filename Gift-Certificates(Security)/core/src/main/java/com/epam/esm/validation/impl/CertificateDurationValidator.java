package com.epam.esm.validation.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.CertificateFieldValidator;
import com.epam.esm.validation.FieldValidator;
import org.springframework.stereotype.Component;

/**
 * Class checks 'duration' field on null.
 */
@Component
public class CertificateDurationValidator implements FieldValidator<CertificateDto> {

    @Override
    public void isCountFieldsEqualNullLessOne(CertificateDto dto) {
        if (dto.getDuration() != null) {
            CertificateFieldValidator.setCounter(CertificateFieldValidator.getCounter() + 1);
        }
        checkCountOfFieldsNotEqualsNull(CertificateFieldValidator.getCounter());
    }

    @Override
    public void isCountFieldsEqualNullMoreOne(CertificateDto dto) throws ServiceException {
        if (dto.getDuration() == null) {
            CertificateFieldValidator.setCounter(CertificateFieldValidator.getCounter() + 1);
        }
        checkCountOfFieldsEqualsNull(CertificateFieldValidator.getCounter());
    }
}