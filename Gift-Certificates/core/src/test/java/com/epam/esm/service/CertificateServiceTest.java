package com.epam.esm.service;

import com.epam.esm.config.TestDBConfig;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.CertificateMapper;
import com.epam.esm.model.Certificate;
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
    public void testFindByIdSuccess() throws ResourceNotFoundException {
        int id = 1;
        String name = "Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;
        String createDate = "2021-03-27T06:12:15.156";
        String lastUpdateDate = "2021-03-29T06:12:15.156";

        Certificate actualCertificate = new Certificate();
        actualCertificate.setId(id);
        actualCertificate.setName(name);
        actualCertificate.setDescription(description);
        actualCertificate.setPrice(price);
        actualCertificate.setDuration(duration);
        actualCertificate.setCreateDate(createDate);
        actualCertificate.setLastUpdateDate(lastUpdateDate);

        Certificate exeptedCertificate = certificateMapper.toEntity(certificateService.findById(id));
        Assertions.assertEquals(actualCertificate, exeptedCertificate);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    public void testFindByIdThrowsException() {
        int id = 25;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.findById(id);
        });
    }

    @DisplayName("Testing method delete() by id of certificate on positive result")
    @Test
    public void testDeleteByIdSuccess() throws ResourceNotFoundException {
        int id = 2;
        certificateService.delete(id);

        //after execute method delete, expected ResourceNotFoundException, because same certificate hasn't already exist
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.findById(id);
        });
    }

    @DisplayName("Testing method delete() by id of certificate on negative result")
    @Test
    public void testDeleteByIdThrowsException() {
        int id = 1;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.delete(id);
        });
    }

    @DisplayName("Testing method insert() on positive result")
    @Test
    public void testInsertSuccess() throws ResourceAlreadyExistException, ResourceNotFoundException {
        Certificate certificate = new Certificate();

        String name = "New Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;
        String createDate = "2021-03-27T06:12:15.156";
        String lastUpdateDate = "2021-03-29T06:12:15.156";

        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        certificate.setCreateDate(createDate);
        certificate.setLastUpdateDate(lastUpdateDate);

        certificateService.insert(certificateMapper.toDto(certificate));
        Assertions.assertEquals(name, certificateService.findByName(name).getName());
    }

    @DisplayName("Testing method insert() on negative result")
    @Test
    public void testInsertThrowsException() {
        Certificate certificate = new Certificate();

        String name = "New Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;
        String createDate = "2021-03-27T06:12:15.156";
        String lastUpdateDate = "2021-03-29T06:12:15.156";

        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        certificate.setCreateDate(createDate);
        certificate.setLastUpdateDate(lastUpdateDate);

        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            certificateService.insert(certificateMapper.toDto(certificate));
        });
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    public void testUpdateSuccess() throws ResourceNotFoundException, ResourceAlreadyExistException {
        int id = 2;
        String newName = "New dermaplaning";
        BigDecimal newPrice = new BigDecimal(100);

        Certificate updatedCertificate = new Certificate();
        updatedCertificate.setId(id);
        updatedCertificate.setName(newName);
        updatedCertificate.setPrice(newPrice);

        certificateService.update(certificateMapper.toDto(updatedCertificate));
        Assertions.assertEquals(newName, certificateService.findByName(newName).getName());
    }

    @DisplayName("Testing method update() on negative result with ResourceNotFoundException")
    @Test
    public void testUpdateThrowsResourceNotFoundException() {
        int id = 17;
        String newName = "New dermaplaning";
        BigDecimal newPrice = new BigDecimal(100);

        Certificate updatedCertificate = new Certificate();
        updatedCertificate.setId(id);
        updatedCertificate.setName(newName);
        updatedCertificate.setPrice(newPrice);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.update(certificateMapper.toDto(updatedCertificate));
        });
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    public void testFindAllSuccess() throws ResourceNotFoundException {
        List<CertificateDto> expectedCertificates = certificateService.findAll();

        CertificateDto certificateDto1 = new CertificateDto();
        CertificateDto certificateDto2 = new CertificateDto();
        CertificateDto certificateDto3 = new CertificateDto();

        certificateDto1.setName("Off road jeep tour");
        certificateDto1.setDescription("We offer the active and courageous an extreme off-road trip.");
        certificateDto1.setPrice(new BigDecimal(350));
        certificateDto1.setDuration(15);
        certificateDto1.setCreateDate("2021-03-27T06:12:15.156");
        certificateDto1.setLastUpdateDate("2021-03-29T06:12:15.156");

        certificateDto2.setName("Dermaplaning");
        certificateDto2.setDescription("Dermaplaning is an exfoliating procedure using a " +
                "straight-edge blade. The treatment will allow products and laser " +
                "therapies to penetrate the skin more easily");
        certificateDto2.setPrice(new BigDecimal(75));
        certificateDto2.setDuration(53);
        certificateDto2.setCreateDate("2021-03-27T06:12:15.156");
        certificateDto2.setLastUpdateDate("2021-03-29T06:12:15.156");

        certificateDto3.setName("GO-KARTING");
        certificateDto3.setDescription("Take the gamble out of gift-giving. " +
                "Give your champion an Ace Karts gift card.");
        certificateDto3.setPrice(new BigDecimal(45));
        certificateDto3.setDuration(70);
        certificateDto3.setCreateDate("2021-03-27T06:12:15.156");
        certificateDto3.setLastUpdateDate("2021-03-29T06:12:15.156");

        List<CertificateDto> actualCertificateDtos = new ArrayList<CertificateDto>() {
            {
                add(certificateDto1);
                add(certificateDto2);
                add(certificateDto3);
            }
        };
        Assertions.assertEquals(actualCertificateDtos, expectedCertificates);
    }
}
