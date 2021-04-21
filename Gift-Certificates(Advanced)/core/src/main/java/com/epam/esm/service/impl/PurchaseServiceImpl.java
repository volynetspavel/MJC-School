package com.epam.esm.service.impl;

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
import com.epam.esm.service.PurchaseService;
import com.epam.esm.validation.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is an implementation of PurchaseService.
 */
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private UserDao userDao;
    private CertificateDao certificateDao;
    private PurchaseDao purchaseDao;
    private PurchaseMapper purchaseMapper;
    private PaginationValidator paginationValidator;

    private int limit;
    private int offset = 0;

    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao, PurchaseMapper purchaseMapper,
                               UserDao userDao, CertificateDao certificateDao,
                               PaginationValidator paginationValidator) {
        this.purchaseDao = purchaseDao;
        this.purchaseMapper = purchaseMapper;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
        this.paginationValidator = paginationValidator;
    }

    @Transactional
    @Override
    public Purchase makePurchase(PurchaseDto purchaseDto) throws ResourceNotFoundException {
        String userEmail = purchaseDto.getUserEmail();
        User user = userDao.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User with email: " + userEmail + " don't found.");
        }

        List<String> certificateNames = purchaseDto.getCertificateNames();

        List<Certificate> certificates = certificateNames.stream()
                .map(name -> certificateDao.findByName(name))
                .collect(Collectors.toList());

        for (Certificate certificate : certificates) {
            if (certificate == null) {
                throw new ResourceNotFoundException("Non-existent Certificate in list.");
            }
        }

        BigDecimal totalCost = certificates.stream()
                .map(Certificate::getPrice)
                .reduce(BigDecimal::add)
                .get();

        Purchase purchase = purchaseMapper.toEntity(purchaseDto);
        purchase.setUser(user);
        purchase.setCost(totalCost);
        purchase.setPurchaseDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());
        purchase.setCertificates(certificates);

        return purchaseDao.insert(purchase);
    }

    @Override
    public PurchaseDto findById(BigInteger id) throws ResourceNotFoundException {
        Purchase purchase = purchaseDao.findById(id);
        if (purchase == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        PurchaseDto purchaseDto = purchaseMapper.toDto(purchase);
        List<String> certificateNames = getListCertificateNamesFromListCertificates(purchase.getCertificates());
        purchaseDto.setCertificateNames(certificateNames);
        return purchaseDto;
    }

    @Override
    public List<PurchaseDto> findAll(Map<String, String> params) throws ValidationException {
        limit = purchaseDao.getCount().intValue();
        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }

        List<Purchase> purchases = purchaseDao.findAll(offset, limit);

        List<PurchaseDto> purchaseList = migrateListFromEntityToDto(purchases);
        return setCertificateNamesFromCertificatesOfEntityToDto(purchaseList, purchases);
    }

    @Override
    public List<PurchaseDto> findPurchasesByUserId(int userId, Map<String, String> params)
            throws ValidationException, ResourceNotFoundException {
        limit = purchaseDao.getCount().intValue();
        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }

        User user = userDao.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + userId + ")");
        }

        List<Purchase> purchases = purchaseDao.findPurchasesByUser(user, offset, limit);

        List<PurchaseDto> purchaseList = migrateListFromEntityToDto(purchases);
        return setCertificateNamesFromCertificatesOfEntityToDto(purchaseList, purchases);
    }

    private List<PurchaseDto> setCertificateNamesFromCertificatesOfEntityToDto(List<PurchaseDto> purchaseDtoList,
                                                                               List<Purchase> purchaseList) {
        int i = 0;
        for (PurchaseDto purchaseDto : purchaseDtoList) {
            Purchase purchase = purchaseList.get(i);
            List<String> certificateNames = getListCertificateNamesFromListCertificates(purchase.getCertificates());
            purchaseDto.setCertificateNames(certificateNames);
            i++;
        }
        return purchaseDtoList;
    }

    private List<String> getListCertificateNamesFromListCertificates(List<Certificate> certificateList) {
        return certificateList.stream()
                .map(Certificate::getName)
                .collect(Collectors.toList());
    }

    private List<PurchaseDto> migrateListFromEntityToDto(List<Purchase> purchases) {
        return purchases.stream()
                .map(purchase -> purchaseMapper.toDto(purchase))
                .collect(Collectors.toList());
    }
}
