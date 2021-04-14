package com.epam.esm.service.impl;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.PurchaseMapper;
import com.epam.esm.model.Purchase;
import com.epam.esm.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an implementation of PurchaseService.
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseDao purchaseDao;
    private PurchaseMapper purchaseMapper;

    public PurchaseServiceImpl() {
    }

    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao, PurchaseMapper purchaseMapper) {
        this.purchaseDao = purchaseDao;
        this.purchaseMapper = purchaseMapper;
    }

    @Transactional
    @Override
    public PurchaseDto insert(PurchaseDto purchaseDto) throws ResourceAlreadyExistException {
/*        if (purchaseDao.findByName(purchaseDto.getName()) != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + purchaseDto.getName() + ") has already existed.");
        }
        Purchase purchase = purchaseMapper.toEntity(purchaseDto);
        int idNewPurchase = purchaseDao.insert(purchase);

        return purchaseMapper.toDto(purchaseDao.findById(idNewPurchase));*/
        return null;
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
