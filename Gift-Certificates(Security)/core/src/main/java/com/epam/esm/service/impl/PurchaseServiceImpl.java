package com.epam.esm.service.impl;

import com.epam.esm.constant.CodeException;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.PurchaseDtoAfterOrder;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.mapper.impl.PurchaseAfterOrderMapper;
import com.epam.esm.mapper.impl.PurchaseMapper;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import com.epam.esm.security.SecurityUtil;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.validation.SecurityValidator;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is an implementation of PurchaseService.
 */
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl extends PurchaseService {

    private UserDao userDao;
    private CertificateDao certificateDao;
    private PurchaseDao purchaseDao;
    private PurchaseMapper purchaseMapper;
    private UserMapper userMapper;
    private PurchaseAfterOrderMapper purchaseAfterOrderMapper;

    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao, PurchaseMapper purchaseMapper,
                               UserDao userDao, CertificateDao certificateDao,
                               UserMapper userMapper, PurchaseAfterOrderMapper purchaseAfterOrderMapper) {
        this.purchaseDao = purchaseDao;
        this.purchaseMapper = purchaseMapper;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
        this.userMapper = userMapper;
        this.purchaseAfterOrderMapper = purchaseAfterOrderMapper;
    }

    @Transactional
    @Override
    public PurchaseDtoAfterOrder makePurchase(PurchaseDto purchaseDto) throws ResourceNotFoundException {
        String userEmail = SecurityUtil.getJwtUser().getEmail();
        Optional<User> user = userDao.findByEmail(userEmail);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException(CodeException.USER_EMAIL_NOT_FOUND);
        }

        List<String> certificateNames = purchaseDto.getCertificateNames();

        List<Certificate> certificates = certificateNames.stream()
                .map(name -> certificateDao.findByName(name).orElseThrow(() ->
                        new ResourceNotFoundException(CodeException.CERTIFICATE_NOT_FOUND)))
                .collect(Collectors.toList());

        BigDecimal totalCost = certificates.stream()
                .map(Certificate::getPrice)
                .reduce(BigDecimal::add)
                .get();

        Purchase purchase = purchaseMapper.toEntity(purchaseDto);
        purchase.setUser(user.get());
        purchase.setCost(totalCost);
        purchase.setPurchaseDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());
        purchase.setCertificates(certificates);

        PurchaseDtoAfterOrder newPurchaseDto = purchaseAfterOrderMapper.toDto(purchaseDao.save(purchase));
        newPurchaseDto.setUserDto(userMapper.toDto(user.get()));
        return newPurchaseDto;
    }

    @Override
    public PurchaseDto findById(BigInteger id) throws ResourceNotFoundException {
        Optional<Purchase> purchase = purchaseDao.findById(id);
        if (!purchase.isPresent()) {
            throw new ResourceNotFoundException(CodeException.RESOURCE_NOT_FOUND_WITHOUT_ID);
        }
        PurchaseDto purchaseDto = purchaseMapper.toDto(purchase.get());
        List<String> certificateNames = getListCertificateNamesFromListCertificates(purchase.get().getCertificates());
        purchaseDto.setCertificateNames(certificateNames);
        return purchaseDto;
    }

    @Override
    public List<PurchaseDto> findPurchasesByUserId(int userId)
            throws ValidationParametersException, ResourceNotFoundException {
        if (SecurityValidator.isCurrentUserHasRoleUser()) {
            userId = Objects.requireNonNull(SecurityUtil.getJwtUserId());
        }

        Optional<User> user = userDao.findById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException(CodeException.RESOURCE_NOT_FOUND_BY_USER_ID, userId);
        }

        List<Purchase> purchases = purchaseDao.findAllByUserId(userId);

        List<PurchaseDto> purchaseList = migrateListFromEntityToDto(purchases);
        return setCertificateNamesFromCertificatesEntityToDto(purchaseList, purchases);
    }

    private List<PurchaseDto> setCertificateNamesFromCertificatesEntityToDto(List<PurchaseDto> purchaseDtoList,
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
