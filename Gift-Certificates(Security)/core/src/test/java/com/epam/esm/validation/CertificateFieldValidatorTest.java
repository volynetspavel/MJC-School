package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validation.impl.CertificateNameValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Class for testing certificates fields validation.
 */
@ExtendWith(MockitoExtension.class)
class CertificateFieldValidatorTest {

    @InjectMocks
    private CertificateNameValidator certificateNameValidator;

    @DisplayName("Testing method isCountFieldsEqualNullLessOne() on positive result")
    @Test
    void isCountFieldsEqualNullLessOneSuccessTest() {
        CertificateDto updatedCertificateDto = getCertificateDtoWithOneField();

        int count = 0;
        Assertions.assertDoesNotThrow(() ->
                certificateNameValidator.isCountFieldsEqualNullLessOne(updatedCertificateDto, count));
    }

    @DisplayName("Testing method isCountFieldsEqualNullLessOne() on negative result")
    @Test
    void isCountFieldsEqualNullLessOneThrowExceptionTest() {
        CertificateDto updatedCertificateDto = getCertificateDtoWithMoreOneField();

        int count = 0;
        Assertions.assertThrows(ServiceException.class,
                () -> certificateNameValidator.isCountFieldsEqualNullLessOne(updatedCertificateDto, count));
    }

    private static CertificateDto createCertificateDto(int id, String name, String description, BigDecimal price,
                                                       Integer duration, String createDate, String lastUpdateDate,
                                                       Set<TagDto> tagDtoList) {

        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(id);
        certificateDto.setName(name);
        certificateDto.setDescription(description);
        certificateDto.setPrice(price);
        certificateDto.setDuration(duration);
        certificateDto.setCreateDate(createDate);
        certificateDto.setLastUpdateDate(lastUpdateDate);
        certificateDto.setTags(tagDtoList);

        return certificateDto;
    }

    private CertificateDto getCertificateDtoWithOneField() {
        int id = 2;
        String newName = "New dermaplaning";
        String newDescription = null;
        BigDecimal newPrice = null;
        Integer newDuration = null;
        String newCreateDate = null;
        String newLastUpdateDate = null;
        Set<TagDto> newTagDtoList = null;

        return createCertificateDto(id, newName, newDescription,
                newPrice, newDuration, newCreateDate, newLastUpdateDate, newTagDtoList);
    }


    private CertificateDto getCertificateDtoWithMoreOneField() {
        int id = 2;
        String newName = "New dermaplaning";
        String newDescription = null;
        BigDecimal newPrice = new BigDecimal("15");
        Integer newDuration = null;
        String newCreateDate = null;
        String newLastUpdateDate = null;
        Set<TagDto> newTagDtoList = null;

        return createCertificateDto(id, newName, newDescription,
                newPrice, newDuration, newCreateDate, newLastUpdateDate, newTagDtoList);
    }
}
