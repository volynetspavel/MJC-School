package com.epam.esm.mapper;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.mapper.impl.PurchaseMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Class for testing purchase mapper.
 */
@ExtendWith(MockitoExtension.class)
class PurchaseMapperTest {

    @InjectMocks
    private PurchaseMapper purchaseMapper;
    @Mock
    private ModelMapper mapper;

    @DisplayName("Testing method toEntity() on positive result")
    @Test
    void toEntitySuccessTest() {
        Purchase expectedPurchase = getPurchase();
        PurchaseDto purchaseDto = getPurchaseDto();

        when(mapper.map(purchaseDto, Purchase.class)).thenReturn(expectedPurchase);
        when(purchaseMapper.toEntity(purchaseDto)).thenReturn(expectedPurchase);

        Purchase actualPurchase = purchaseMapper.toEntity(purchaseDto);
        Assertions.assertEquals(expectedPurchase, actualPurchase);
    }

    @DisplayName("Testing method toEntity() on negative result, when purchase is null")
    @Test
    void toEntityNegativeTest() {
        PurchaseDto purchaseDto = null;

        Purchase actualPurchase = purchaseMapper.toEntity(purchaseDto);
        Assertions.assertNull(actualPurchase);
    }

    @DisplayName("Testing method toDto() on positive result")
    @Test
    void toDtoSuccessTest() {
        Purchase purchase = getPurchase();
        PurchaseDto expectedPurchaseDto = getPurchaseDto();

        when(mapper.map(purchase, PurchaseDto.class)).thenReturn(expectedPurchaseDto);
        when(purchaseMapper.toDto(purchase)).thenReturn(expectedPurchaseDto);

        PurchaseDto actualPurchaseDto = purchaseMapper.toDto(purchase);
        Assertions.assertEquals(expectedPurchaseDto, actualPurchaseDto);
    }


    @DisplayName("Testing method toDto() on negative result, when PurchaseDto is null")
    @Test
    void toDtoNegativeTest() {
        Purchase purchase = null;

        PurchaseDto actualPurchaseDto = purchaseMapper.toDto(purchase);
        Assertions.assertNull(actualPurchaseDto);
    }

    private Purchase getPurchase() {
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

        return createPurchase(id, user, cost, purchaseDate, certificates);
    }

    private PurchaseDto getPurchaseDto() {
        BigInteger id = new BigInteger("1");
        User user = new User();
        String userEmail = "jonhy@mail.com";
        user.setEmail(userEmail);
        BigDecimal cost = new BigDecimal("150");
        String purchaseDate = "2021-04-19T06:12:23.523";

        String certificateName = "Certificate";
        Certificate certificate = new Certificate();
        certificate.setName(certificateName);

        List<String> certificateNames = Arrays.asList(certificateName);

        return createPurchaseDto(id, userEmail, cost, purchaseDate, certificateNames);
    }

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
