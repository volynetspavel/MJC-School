package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Certificate;

import java.util.List;
import java.util.Map;

/**
 * This class is a layer for interacting with CertificateDao.
 */
public interface CertificateService extends Service<CertificateDto, Certificate> {

    CertificateDto updateSingleField(CertificateDto certificateDto) throws ResourceNotFoundException, ServiceException;

    List<CertificateDto> findCertificatesBySeveralTags(List<String> tagNames,
                                                       Map<String, String> params) throws ResourceNotFoundException;

    List<CertificateDto> findCertificatesByParams(Map<String, String> params) throws ResourceNotFoundException;
}
