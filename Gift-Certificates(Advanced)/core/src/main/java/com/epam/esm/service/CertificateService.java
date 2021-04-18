package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Map;

/**
 * This class is a layer for interacting with CertificateDao.
 */
public interface CertificateService extends Service<CertificateDto> {

    CertificateDto updateSingleField(CertificateDto certificateDto) throws ResourceNotFoundException, ServiceException;

/*    List<CertificateDto> findAllByTagId(int id) throws ResourceNotFoundException;

    List<CertificateDto> findByTagPartOfNamePartOfDescriptionAndOrderedByName(Map<String, String> params)
            throws ResourceNotFoundException;

    List<CertificateDto> findAllOrderByDate(String order) throws ResourceNotFoundException;

    List<CertificateDto> findAllOrderByName(String order) throws ResourceNotFoundException;

    List<CertificateDto> findAllOrderByNameAndDate(String order) throws ResourceNotFoundException;*/

    List<CertificateDto> findCertificatesBySeveralTags(List<String> tagNames) throws ResourceNotFoundException;

    List<CertificateDto> findCertificatesByParams(Map<String, String> params) throws ResourceNotFoundException;
}
