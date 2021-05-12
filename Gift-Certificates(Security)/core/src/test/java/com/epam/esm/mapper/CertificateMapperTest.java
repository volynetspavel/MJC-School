package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.impl.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

/**
 * Class for testing certificate mapper.
 */
@ExtendWith(MockitoExtension.class)
class CertificateMapperTest {

    @InjectMocks
    private CertificateMapper certificateMapper;
    @Mock
    private ModelMapper mapper;

    @DisplayName("Testing method toEntity() on positive result")
    @Test
    void toEntitySuccessTest() {
        Certificate expectedCertificate = getCertificate();
        CertificateDto certificateDto = getCertificateDto();

        when(mapper.map(certificateDto, Certificate.class)).thenReturn(expectedCertificate);
        when(certificateMapper.toEntity(certificateDto)).thenReturn(expectedCertificate);

        Certificate actualCertificate = certificateMapper.toEntity(certificateDto);
        Assertions.assertEquals(expectedCertificate, actualCertificate);
    }

    @DisplayName("Testing method toEntity() on negative result, when certificate is null")
    @Test
    void toEntityNegativeTest() {
        CertificateDto certificateDto = null;

        Certificate actualCertificate = certificateMapper.toEntity(certificateDto);
        Assertions.assertNull(actualCertificate);
    }

    @DisplayName("Testing method toDto() on positive result")
    @Test
    void toDtoSuccessTest() {
        Certificate certificate = getCertificate();
        CertificateDto expectedCertificateDto = getCertificateDto();

        when(mapper.map(certificate, CertificateDto.class)).thenReturn(expectedCertificateDto);
        when(certificateMapper.toDto(certificate)).thenReturn(expectedCertificateDto);

        CertificateDto actualCertificateDto = certificateMapper.toDto(certificate);
        Assertions.assertEquals(expectedCertificateDto, actualCertificateDto);
    }

    @DisplayName("Testing method toDto() on negative result, when CertificateDto is null")
    @Test
    void toDtoNegativeTest() {
        Certificate certificate = null;

        CertificateDto actualCertificateDto = certificateMapper.toDto(certificate);
        Assertions.assertNull(actualCertificateDto);
    }

    private Certificate getCertificate() {
        int id = 1;
        String name = "New Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;
        String createDate = "2021-03-25T06:17:15.656";
        String lastUpdateDate = "2021-04-01T13:08:39.149";

        int tagId = 1;
        String tagName = "tag1";
        Tag tag = createTag(tagId, tagName);
        Set<Tag> tagList = new HashSet<>(Arrays.asList(tag));

        return createCertificate(id, name, description, price, duration, createDate, lastUpdateDate, tagList);
    }

    private CertificateDto getCertificateDto() {
        int id = 1;
        String name = "New Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;
        String createDate = "2021-03-25T06:17:15.656";
        String lastUpdateDate = "2021-04-01T13:08:39.149";

        int tagId = 1;
        String tagName = "tag1";
        TagDto tagDto = createTagDto(tagId, tagName);
        Set<TagDto> tagDtoList = new HashSet<>(Arrays.asList(tagDto));

        return createCertificateDto(id, name, description, price, duration, createDate, lastUpdateDate, tagDtoList);
    }

    private Certificate createCertificate(int id, String name, String description, BigDecimal price,
                                          Integer duration, String createDate, String lastUpdateDate,
                                          Set<Tag> tagList) {

        Certificate certificate = new Certificate();
        certificate.setId(id);
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        certificate.setCreateDate(createDate);
        certificate.setLastUpdateDate(lastUpdateDate);
        certificate.setTags(tagList);

        return certificate;
    }

    private CertificateDto createCertificateDto(int id, String name, String description, BigDecimal price,
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

    private Tag createTag(int id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        return tag;
    }

    private TagDto createTagDto(int id, String name) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagDto.setName(name);

        return tagDto;
    }
}
