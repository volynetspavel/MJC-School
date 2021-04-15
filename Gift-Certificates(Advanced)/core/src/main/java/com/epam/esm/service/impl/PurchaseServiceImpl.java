package com.epam.esm.service.impl;

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
import java.util.stream.Collectors;

/**
 * This class is an implementation of PurchaseService.
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseDao purchaseDao;
    private PurchaseMapper purchaseMapper;

    private UserDao userDao;
    private CertificateDao certificateDao;

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
    public void delete(int id) throws ResourceNotFoundException {

    }

    @Override
    public PurchaseDto update(PurchaseDto entityDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
        return null;
    }

    @Override
    public List<PurchaseDto> findAll() throws ResourceNotFoundException {
        List<Purchase> purchases = purchaseDao.findAll();
        if (purchases == null || purchases.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found");
        }
        return purchases.stream()
                .map(purchase -> purchaseMapper.toDto(purchase))
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseDto findById(BigInteger id) throws ResourceNotFoundException {
        Purchase purchase = purchaseDao.findById(id);
        if (purchase == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        return purchaseMapper.toDto(purchase);
    }
}
