package com.epam.esm.service;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.PurchaseMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import com.epam.esm.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Class for testing methods from service layer for purchase.
 */
@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    @Mock
    private PurchaseDao purchaseDao;
    @Mock
    private PurchaseMapper purchaseMapper;
    @Mock
    private UserDao userDao;
    @Mock
    private CertificateDao certificateDao;


    @DisplayName("Testing method findById() on positive result")
    @Test
    void findByIdSuccessTest() throws ResourceNotFoundException {
        BigInteger id = new BigInteger("1");
        User user = new User();
        String userEmail = "jonhy@mail.com";
        user.setEmail(userEmail);
        BigDecimal cost = new BigDecimal("150");
        String purchaseDate = "2021-04-19T06:12:23.523";

        String certificateName = "Certificate";
        Certificate certificate = new Certificate();
        certificate.setName(certificateName);

        List<Certificate> certificates = Arrays.asList(certificate);
        List<String> certificateNames = Arrays.asList(certificateName);

        Purchase expected = createPurchase(id, user, cost, purchaseDate, certificates);
        PurchaseDto expectedDto = createPurchaseDto(id, userEmail, cost, purchaseDate, certificateNames);

        when(purchaseDao.findById(id)).thenReturn(expected);
        when(purchaseMapper.toDto(expected)).thenReturn(expectedDto);
        PurchaseDto actualDto = purchaseService.findById(id);

        assertEquals(expectedDto, actualDto);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    void findByIdThrowsExceptionTest() {
        BigInteger id = new BigInteger("1");
        when(purchaseDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseService.findById(id));
    }


    @DisplayName("Testing method makePurchase() on positive result")
    @Test
    void makePurchaseSuccessTest() {
        BigInteger id = new BigInteger("1");
        String userEmail = "jonhy@mail.com";
        BigDecimal cost = new BigDecimal("150");
        String purchaseDate = "2021-04-19T06:12:23.523";

        String certificateName = "Certificate";
        List<String> certificateNames = Arrays.asList(certificateName);

        PurchaseDto newPurchaseDto = createPurchaseDto(id, userEmail, cost, purchaseDate, certificateNames);

        User user = new User();
        user.setEmail(userEmail);
        when(userDao.findByEmail(userEmail)).thenReturn(user);

        Certificate certificate = new Certificate();
        certificate.setName(certificateName);
        certificate.setPrice(cost);
        when(certificateDao.findByName(certificateName)).thenReturn(certificate);

        List<Certificate> certificates = Arrays.asList(certificate);

        Purchase newPurchase = createPurchase(id, user, cost, purchaseDate, certificates);
        when(purchaseMapper.toEntity(newPurchaseDto)).thenReturn(newPurchase);

        newPurchase.setPurchaseDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());
        when(purchaseDao.insert(newPurchase)).thenReturn(newPurchase);

        Purchase purchaseAfterMakePurchase = purchaseService.makePurchase(newPurchaseDto);
        assertEquals(newPurchase, purchaseAfterMakePurchase);
    }

/*
    @DisplayName("Testing method findAll() on positive result")
    @Test
    void findAllSuccessTest() throws ResourceNotFoundException {

        int id1 = 1;
        String name1 = "extreme";
        Purchase purchase1 = createPurchase(id1, name1);

        int id2 = 2;
        String name2 = "beauty";
        Purchase purchase2 = createPurchase(id2, name2);

        int id3 = 3;
        String name3 = "rest";
        Purchase purchase3 = createPurchase(id3, name3);

        List<Purchase> expectedPurchaseList = Arrays.asList(purchase1, purchase2, purchase3);

        PurchaseDto purchaseDto1 = createPurchaseDto(id1, name1);
        PurchaseDto purchaseDto2 = createPurchaseDto(id2, name2);
        PurchaseDto purchaseDto3 = createPurchaseDto(id3, name3);

        List<PurchaseDto> expectedPurchaseDtoList = Arrays.asList(purchaseDto1, purchaseDto2, purchaseDto3);

        int offset = 0;
        int limit = 3;
        when(purchaseDao.getCount()).thenReturn(3);
        when(purchaseDao.findAll(offset, limit)).thenReturn(expectedPurchaseList);
        when(purchaseMapper.toDto(purchase1)).thenReturn(purchaseDto1);
        when(purchaseMapper.toDto(purchase2)).thenReturn(purchaseDto2);
        when(purchaseMapper.toDto(purchase3)).thenReturn(purchaseDto3);

        List<PurchaseDto> actualPurchaseDtoList = purchaseService.findAll(new HashMap<>());

        assertEquals(expectedPurchaseDtoList, actualPurchaseDtoList);
    }

    @DisplayName("Testing method findAll() on negative result")
    @Test
    void findAllThrowsExceptionTest() {
        int offset = 0;
        int limit = 3;
        when(purchaseDao.getCount()).thenReturn(3);
        when(purchaseDao.findAll(offset, limit)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseService.findAll(new HashMap<>()));
    }*/

    private Purchase createPurchase(BigInteger id, User user, BigDecimal cost,
                                    String purchaseDate, List<Certificate> certificates) {
        Purchase purchase = new Purchase();
        purchase.setId(id);
        purchase.setUser(user);
        purchase.setCost(cost);
        purchase.setPurchaseDate(purchaseDate);
        purchase.setCertificates(certificates);

        return purchase;
    }

    private PurchaseDto createPurchaseDto(BigInteger id, String userEmail, BigDecimal cost,
                                          String purchaseDate, List<String> certificateNames) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setId(id);
        purchaseDto.setUserEmail(userEmail);
        purchaseDto.setCost(cost);
        purchaseDto.setPurchaseDate(purchaseDate);
        purchaseDto.setCertificateNames(certificateNames);

        return purchaseDto;
    }
}

