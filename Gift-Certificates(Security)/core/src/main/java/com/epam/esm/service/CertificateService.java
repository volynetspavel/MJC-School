package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.model.Certificate;
import com.epam.esm.service.crud.DeletableService;
import com.epam.esm.service.crud.InsertableService;
import com.epam.esm.service.crud.ReadableService;
import com.epam.esm.service.crud.UpdatableService;

import java.util.List;
import java.util.Map;

/**
 * This class is a layer for interacting with CertificateDao.
 */
public abstract class CertificateService implements ReadableService<CertificateDto, Certificate>, InsertableService<CertificateDto>,
        UpdatableService<CertificateDto>, DeletableService<CertificateDto> {

    public abstract CertificateDto updateSingleField(CertificateDto certificateDto) throws ResourceNotFoundException, ServiceException;

    public abstract List<CertificateDto> findCertificatesBySeveralTags(List<String> tagNames,
                                                                       Map<String, String> params)
            throws ValidationParametersException;

    public abstract List<CertificateDto> findCertificatesByParams(Map<String, String> params)
            throws ValidationParametersException;
}
