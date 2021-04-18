package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.PurchaseMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import com.epam.esm.service.PurchaseService;
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
public class PurchaseServiceImpl implements PurchaseService {

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    private UserDao userDao;
    private CertificateDao certificateDao;
    private PurchaseDao purchaseDao;
    private PurchaseMapper purchaseMapper;

    private int limit;
    private int offset = 0;

    public PurchaseServiceImpl() {
    }

    @Autowired

    public PurchaseServiceImpl(PurchaseDao purchaseDao, PurchaseMapper purchaseMapper,
                               UserDao userDao, CertificateDao certificateDao) {
        this.purchaseDao = purchaseDao;
        this.purchaseMapper = purchaseMapper;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }

    @Transactional
    @Override
    public Purchase makePurchase(PurchaseDto purchaseDto) {
        String userEmail = purchaseDto.getUserEmail();
        User user = userDao.findByEmail(userEmail);

        List<String> certificateNames = purchaseDto.getCertificateNames();
        List<Certificate> certificates = certificateNames.stream()
                .map(name -> certificateDao.findByName(name))
                .collect(Collectors.toList());

        BigDecimal totalCost = certificates.stream()
                .map(Certificate::getPrice)
                .reduce(BigDecimal::add)
                .get();

        Purchase purchase = new Purchase();
        purchase.setId(null);
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
        return purchaseMapper.toDto(purchase);
    }

    @Override
    public List<PurchaseDto> findAll(Map<String, String> params) throws ResourceNotFoundException {
        limit = purchaseDao.getCount().intValue();

        if (params.containsKey(SIZE) && params.containsKey(PAGE)) {
            limit = Integer.parseInt(params.get(SIZE));
            offset = (Integer.parseInt(params.get(PAGE)) - 1) * limit;
        }
        List<Purchase> purchases = purchaseDao.findAll(offset, limit);
        List<PurchaseDto> purchaseList = migrateListFromEntityToDto(purchases);
        checkListOnEmptyOrNull(purchaseList);

        return setCertificateNamesFromCertificatesOfEntityToDto(purchaseList, purchases);
    }

    private List<PurchaseDto> setCertificateNamesFromCertificatesOfEntityToDto(List<PurchaseDto> purchaseDtoList,
                                                                               List<Purchase> purchaseList) {
        int i = 0;
        for (PurchaseDto purchaseDto : purchaseDtoList) {
            Purchase purchase = purchaseList.get(i);

            List<String> certificateNames = purchase.getCertificates().stream()
                    .map(Certificate::getName)
                    .collect(Collectors.toList());

            purchaseDto.setCertificateNames(certificateNames);
            i++;
        }
        return purchaseDtoList;
    }

    private List<PurchaseDto> migrateListFromEntityToDto(List<Purchase> purchases) {
        return purchases.stream()
                .map(purchase -> purchaseMapper.toDto(purchase))
                .collect(Collectors.toList());
    }
}
