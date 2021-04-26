package com.epam.esm.service;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.mapper.impl.PurchaseMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import com.epam.esm.service.impl.PurchaseServiceImpl;
import com.epam.esm.validation.PaginationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Mock
    private PaginationValidator paginationValidator;

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
    void makePurchaseSuccessTest() throws ResourceNotFoundException {
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

    @DisplayName("Testing method makePurchase() on exception, when user doesn't exist")
    @Test
    void makePurchaseThrowsExceptionWhenUserDoesNotExistTest() {
        String userEmail = "jonhy-non-exist@mail.com";
        PurchaseDto newPurchaseDto = createPurchaseDto(null, userEmail, null, null, null);
        when(userDao.findByEmail(userEmail)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseService.makePurchase(newPurchaseDto));
    }

    @DisplayName("Testing method makePurchase() on exception, when certificate doesn't exist")
    @Test
    void makePurchaseThrowsExceptionWhenCertificateDoesNotExistTest() {
        BigInteger id = new BigInteger("1");
        String userEmail = "jonhy@mail.com";
        BigDecimal cost = new BigDecimal("150");
        String purchaseDate = "2021-04-19T06:12:23.523";

        String certificateName = "Certificate Non-exist";
        List<String> certificateNames = Arrays.asList(certificateName);

        PurchaseDto newPurchaseDto = createPurchaseDto(id, userEmail, cost, purchaseDate, certificateNames);

        User user = new User();
        user.setEmail(userEmail);
        when(userDao.findByEmail(userEmail)).thenReturn(user);
        when(certificateDao.findByName(certificateName)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseService.makePurchase(newPurchaseDto));
    }

    @DisplayName("Testing method findPurchasesByUser() on positive result")
    @Test
    void findPurchasesByUserSuccessTest() throws ResourceNotFoundException, ValidationException {
        int userId = 1;
        String userEmail = "jonhy@mail.com";

        int offset = 0;
        int limit = 3;
        when(purchaseDao.getCount()).thenReturn(new BigInteger("3"));
        when(paginationValidator.validatePaginationParameters(new HashMap<>())).thenReturn(false);

        User user = new User();
        user.setId(userId);
        when(userDao.findById(userId)).thenReturn(user);

        BigInteger id1 = new BigInteger("1");
        BigDecimal cost1 = new BigDecimal("150");
        String purchaseDate1 = "2021-04-19T06:12:23.523";
        String certificateName1 = "Certificate1";
        List<String> certificateNames1 = Arrays.asList(certificateName1);
        Certificate certificate1 = new Certificate();
        certificate1.setName(certificateName1);
        List<Certificate> certificateList1 = Arrays.asList(certificate1);

        PurchaseDto purchaseDto1 = createPurchaseDto(id1, userEmail, cost1, purchaseDate1, certificateNames1);
        Purchase purchase1 = createPurchase(id1, user, cost1, purchaseDate1, certificateList1);

        BigInteger id2 = new BigInteger("2");
        BigDecimal cost2 = new BigDecimal("200");
        String purchaseDate2 = "2021-04-01T13:08:43.157";
        String certificateName2 = "Certificate2";
        List<String> certificateNames2 = Arrays.asList(certificateName1);
        Certificate certificate2 = new Certificate();
        certificate2.setName(certificateName2);
        List<Certificate> certificateList2 = Arrays.asList(certificate2);

        PurchaseDto purchaseDto2 = createPurchaseDto(id2, userEmail, cost2, purchaseDate2, certificateNames2);
        Purchase purchase2 = createPurchase(id2, user, cost2, purchaseDate2, certificateList2);

        List<PurchaseDto> purchaseDtoList = Arrays.asList(purchaseDto1, purchaseDto2);
        List<Purchase> purchaseList = Arrays.asList(purchase1, purchase2);

        when(purchaseDao.findPurchasesByUser(user, offset, limit)).thenReturn(purchaseList);
        when(purchaseMapper.toDto(purchase1)).thenReturn(purchaseDto1);
        when(purchaseMapper.toDto(purchase2)).thenReturn(purchaseDto2);

        List<PurchaseDto> purchaseDtoListByUserId = purchaseService.findPurchasesByUserId(userId, new HashMap<>());

        assertEquals(purchaseDtoListByUserId, purchaseDtoList);
    }

    @DisplayName("Testing method findPurchasesByUser() on exception, when user doesn't exist.")
    @Test
    void findPurchasesByUserThrowsExceptionTest() throws ValidationException {
        int userId = 1;

        when(purchaseDao.getCount()).thenReturn(new BigInteger("3"));
        when(paginationValidator.validatePaginationParameters(new HashMap<>())).thenReturn(false);

        when(userDao.findById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseService.findPurchasesByUserId(userId, new HashMap<>()));
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void findAllSuccessTest() throws ValidationException {
        int offset = 0;
        int limit = 3;
        when(purchaseDao.getCount()).thenReturn(new BigInteger("3"));
        when(paginationValidator.validatePaginationParameters(new HashMap<>())).thenReturn(false);

        int userId1 = 1;
        String userEmail1 = "jonhy2@mail.com";
        User user1 = new User();
        user1.setId(userId1);
        user1.setEmail(userEmail1);

        BigInteger id1 = new BigInteger("1");
        BigDecimal cost1 = new BigDecimal("150");
        String purchaseDate1 = "2021-04-19T06:12:23.523";
        String certificateName1 = "Certificate1";
        List<String> certificateNames1 = Arrays.asList(certificateName1);
        Certificate certificate1 = new Certificate();
        certificate1.setName(certificateName1);
        List<Certificate> certificateList1 = Arrays.asList(certificate1);

        PurchaseDto purchaseDto1 = createPurchaseDto(id1, userEmail1, cost1, purchaseDate1, certificateNames1);
        Purchase purchase1 = createPurchase(id1, user1, cost1, purchaseDate1, certificateList1);

        int userId2 = 2;
        String userEmail2 = "jonhy2@mail.com";
        User user2 = new User();
        user2.setId(userId2);
        user2.setEmail(userEmail2);

        BigInteger id2 = new BigInteger("2");
        BigDecimal cost2 = new BigDecimal("200");
        String purchaseDate2 = "2021-04-01T13:08:43.157";
        String certificateName2 = "Certificate2";
        List<String> certificateNames2 = Arrays.asList(certificateName1);
        Certificate certificate2 = new Certificate();
        certificate2.setName(certificateName2);
        List<Certificate> certificateList2 = Arrays.asList(certificate2);

        PurchaseDto purchaseDto2 = createPurchaseDto(id2, userEmail2, cost2, purchaseDate2, certificateNames2);
        Purchase purchase2 = createPurchase(id2, user2, cost2, purchaseDate2, certificateList2);

        List<PurchaseDto> purchaseDtoList = Arrays.asList(purchaseDto1, purchaseDto2);
        List<Purchase> purchaseList = Arrays.asList(purchase1, purchase2);


        when(purchaseDao.findAll(offset, limit)).thenReturn(purchaseList);
        when(purchaseMapper.toDto(purchase1)).thenReturn(purchaseDto1);
        when(purchaseMapper.toDto(purchase2)).thenReturn(purchaseDto2);

        List<PurchaseDto> purchaseDtoListFromDataBase = purchaseService.findAll(new HashMap<>());

        assertEquals(purchaseDtoList, purchaseDtoListFromDataBase);
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

