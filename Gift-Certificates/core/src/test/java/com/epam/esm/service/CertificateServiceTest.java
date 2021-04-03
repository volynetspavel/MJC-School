package com.epam.esm.service;

import com.epam.esm.config.TestDBConfig;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for testing methods from service layer for certificate.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
public class CertificateServiceTest {
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CertificateMapper certificateMapper;

    @DisplayName("Testing method findById() on positive result")
    @Test
    void testFindByIdSuccess() throws ResourceNotFoundException {
        int id = 1;
        String name = "Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;
        String createDate = "2021-03-27T06:12:15.156";
        String lastUpdateDate = "2021-03-29T06:12:15.156";

        Certificate actualCertificate = createCertificate(name, description, price,
                duration, createDate, lastUpdateDate);
        actualCertificate.setId(id);

        Certificate exeptedCertificate = certificateMapper.toEntity(certificateService.findById(id));
        Assertions.assertEquals(actualCertificate, exeptedCertificate);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    void testFindByIdThrowsException() {
        int id = 25;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.findById(id);
        });
    }

    @DisplayName("Testing method delete() by id of certificate on positive result")
    @Test
    void testDeleteByIdSuccess() throws ResourceNotFoundException {
        int id = 2;
        certificateService.delete(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.findById(id);
        });
    }

    @DisplayName("Testing method delete() by id of certificate on negative result")
    @Test
    void testDeleteByIdThrowsException() {
        int id = 1;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.delete(id);
        });
    }

    @DisplayName("Testing method insert() on positive result")
    @Test
    void testInsertSuccess() throws ResourceAlreadyExistException, ResourceNotFoundException {
        String name = "New Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;

        //createdDaye and lastUpdateDate are set in services
        Certificate certificate = createCertificate(name, description, price,
                duration, null, null);

        certificateService.insert(certificateMapper.toDto(certificate));
        Assertions.assertEquals(name, certificateService.findByName(name).getName());
    }

    @DisplayName("Testing method insert() on negative result")
    @Test
    void testInsertThrowsException() {
        String name = "New Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;

        //createdDate and lastUpdateDate are set in services
        Certificate certificate = createCertificate(name, description, price,
                duration, null, null);

        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            certificateService.insert(certificateMapper.toDto(certificate));
        });
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    void testUpdateSuccess() throws ResourceNotFoundException, ResourceAlreadyExistException {
        //parameters which is null are not updated
        int id = 2;
        String newName = "New dermaplaning";
        String newDescription = null;
        BigDecimal newPrice = new BigDecimal(100);
        Integer newDuration = null;
        String newCreateDate = null;
        String newLastUpdateDate = null;

        Certificate updatedCertificate = createCertificate(newName, newDescription, newPrice,
                newDuration, newCreateDate, newLastUpdateDate);
        updatedCertificate.setId(id);

        certificateService.update(certificateMapper.toDto(updatedCertificate));
        Assertions.assertEquals(newName, certificateService.findByName(newName).getName());
    }

    @DisplayName("Testing method update() on negative result with ResourceNotFoundException")
    @Test
    void testUpdateThrowsResourceNotFoundException() {
        int id = 17;
        String newName = "New dermaplaning";
        String newDescription = null;
        BigDecimal newPrice = new BigDecimal(100);
        Integer newDuration = null;
        String newCreateDate = null;
        String newLastUpdateDate = null;

        Certificate updatedCertificate = createCertificate(newName, newDescription, newPrice,
                newDuration, newCreateDate, newLastUpdateDate);
        updatedCertificate.setId(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.update(certificateMapper.toDto(updatedCertificate));
        });
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void testFindAllSuccess() throws ResourceNotFoundException {
        List expectedCertificates = certificateService.findAll();

        String name1 = "Off road jeep tour";
        String description1 = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price1 = new BigDecimal("350.00");
        Integer duration1 = 15;
        String createDate1 = "2021-03-25T06:17:15.656Z";
        String lastUpdateDate1 = "2021-04-01T13:08:39.149Z";

        String[] tagNames = {"extreme", "beauty", "rest-new"};
        Tag[] tags1 = new Tag[]{new Tag(), new Tag(), new Tag()};
        for (int i = 0; i < tags1.length; i++) {
            tags1[i].setName(tagNames[i]);
        }

        String name2 = "Dermaplaning";
        String description2 = "Dermaplaning is an exfoliating procedure using a straight-edge blade. " +
                "The treatment will allow products and laser therapies to penetrate the skin more easily";
        BigDecimal price2 = new BigDecimal("75.00");
        Integer duration2 = 53;
        String createDate2 = "2021-03-14T06:14:15.116Z";
        String lastUpdateDate2 = "2021-04-01T13:08:41.859Z";

        Tag[] tags2 = {new Tag(), new Tag()};
        for (int i = 0; i < tags2.length; i++) {
            tags2[i].setName(tagNames[i]);
        }

        String name3 = "GO-KARTING";
        String description3 = "Take the gamble out of gift-giving. Give your champion an Ace Karts gift card.";
        BigDecimal price3 = new BigDecimal("45.00");
        Integer duration3 = 70;
        String createDate3 = "2021-03-23T06:09:15.147Z";
        String lastUpdateDate3 = "2021-04-01T13:08:43.157Z";

        CertificateDto certificateDto1 = certificateMapper.toDto(createCertificate(name1,
                description1, price1, duration1, createDate1, lastUpdateDate1));
        certificateDto1.setTags(List.of(tags1));
        CertificateDto certificateDto2 = certificateMapper.toDto(createCertificate(name2,
                description2, price2, duration2, createDate2, lastUpdateDate2));
        certificateDto2.setTags(List.of(tags2));
        CertificateDto certificateDto3 = certificateMapper.toDto(createCertificate(name3,
                description3, price3, duration3, createDate3, lastUpdateDate3));
        certificateDto3.setTags(List.of(new Tag[]{}));

        List actualCertificates = new ArrayList<CertificateDto>() {
            {
                add(certificateDto1);
                add(certificateDto2);
                add(certificateDto3);
            }
        };
        Assertions.assertEquals(expectedCertificates, actualCertificates);
    }

    private Certificate createCertificate(String name,
                                          String description,
                                          BigDecimal price,
                                          Integer duration,
                                          String createDate,
                                          String lastUpdateDate) {

        Certificate certificate = new Certificate();

        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        certificate.setCreateDate(createDate);
        certificate.setLastUpdateDate(lastUpdateDate);
        return certificate;
    }
}
