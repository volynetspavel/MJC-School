package com.epam.esm.dao;

import com.epam.esm.config.TestDBConfig;
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
public class CertificateDaoTest {
    @Autowired
    private CertificateDao certificateDao;

    @DisplayName("Testing method findById() on positive result")
    @Test
    void testFindByIdSuccess() {
        int id = 1;
        String name = "Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal("350.00");
        Integer duration = 15;
        String createDate = "2021-03-25T06:17:15.656Z";
        String lastUpdateDate = "2021-04-01T13:08:39.149Z";

        Certificate actualCertificate = createCertificate(name, description, price,
                duration, createDate, lastUpdateDate);
        actualCertificate.setId(id);

        Certificate exeptedCertificate = (certificateDao.findById(id));
        Assertions.assertEquals(exeptedCertificate, actualCertificate);
    }

    @DisplayName("Testing method findById() on negative result")
    @Test
    void testFindByIdReturnNull() {
        int id = 25;
        Assertions.assertNull(certificateDao.findById(id));
    }

    @DisplayName("Testing method delete() by id of certificate on positive result")
    @Test
    void testDeleteByIdSuccess() {
        int id = 2;
        certificateDao.delete(id);

        Assertions.assertNull(certificateDao.findById(id));
    }

    @DisplayName("Testing method insert() on positive result")
    @Test
    void testInsertSuccess() {
        String name = "New Off road jeep tour";
        String description = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price = new BigDecimal(350);
        Integer duration = 15;
        String createDate = "2021-03-27T06:12:15.156";
        String lastUpdateDate = "2021-03-29T06:12:15.156";

        Certificate certificate = createCertificate(name, description, price,
                duration, createDate, lastUpdateDate);

        certificateDao.insert(certificate);
        Assertions.assertEquals(name, certificateDao.findByName(name).getName());
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    void testUpdateSuccess() {
        //parameters which is null are not updated
        int id = 2;
        String newName = "New dermaplaning";
        BigDecimal newPrice = new BigDecimal(100);
        String newLastUpdateDate = "2021-04-03T13:12:15.256";

        Certificate updatedCertificate = createCertificate(newName, null, newPrice,
                null, null, newLastUpdateDate);
        updatedCertificate.setId(id);

        certificateDao.update(updatedCertificate);
        Assertions.assertEquals(newName, certificateDao.findByName(newName).getName());
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void testFindAllSuccess() {
        List expectedCertificates = certificateDao.findAll();

        String name1 = "Off road jeep tour";
        String description1 = "We offer the active and courageous an extreme off-road trip.";
        BigDecimal price1 = new BigDecimal("350.00");
        Integer duration1 = 15;
        String createDate1 = "2021-03-25T06:17:15.656Z";
        String lastUpdateDate1 = "2021-04-01T13:08:39.149Z";

        String name2 = "Dermaplaning";
        String description2 = "Dermaplaning is an exfoliating procedure using a straight-edge blade. " +
                "The treatment will allow products and laser therapies to penetrate the skin more easily";
        BigDecimal price2 = new BigDecimal("75.00");
        Integer duration2 = 53;
        String createDate2 = "2021-03-14T06:14:15.116Z";
        String lastUpdateDate2 = "2021-04-01T13:08:41.859Z";

        String name3 = "GO-KARTING";
        String description3 = "Take the gamble out of gift-giving. Give your champion an Ace Karts gift card.";
        BigDecimal price3 = new BigDecimal("45.00");
        Integer duration3 = 70;
        String createDate3 = "2021-03-23T06:09:15.147Z";
        String lastUpdateDate3 = "2021-04-01T13:08:43.157Z";

        Certificate certificate1 = createCertificate(name1,
                description1, price1, duration1, createDate1, lastUpdateDate1);
        Certificate certificate2 = createCertificate(name2,
                description2, price2, duration2, createDate2, lastUpdateDate2);
        Certificate certificate3 = createCertificate(name3,
                description3, price3, duration3, createDate3, lastUpdateDate3);

        List actualCertificates = new ArrayList<Certificate>() {
            {
                add(certificate1);
                add(certificate2);
                add(certificate3);
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
