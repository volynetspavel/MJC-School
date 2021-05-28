package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Defines methods for counting fields which equals null.
 */
@Component
public class CertificateFieldValidator {

    private static int counter = 0;

    @Autowired
    public List<FieldValidator<CertificateDto>> fieldValidators;

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        CertificateFieldValidator.counter = counter;
    }

    public void checkIsCountFieldsEqualNullLessOne(CertificateDto certificateDto) {
        fieldValidators.forEach(fieldValidator ->
                fieldValidator.isCountFieldsEqualNullLessOne(certificateDto));
        setCounter(0);
    }

    public void checkIsCountFieldsEqualNullMoreOne(CertificateDto certificateDto) {
        fieldValidators.forEach(fieldValidator ->
                fieldValidator.isCountFieldsEqualNullMoreOne(certificateDto));
        setCounter(0);
    }
}
