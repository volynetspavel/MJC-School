package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;

import java.util.List;

/**
 * This class is a layer for interacting with CertificateDao.
 */
public abstract class CertificateService implements AbstractService<CertificateDto>{

    public abstract CertificateDto findByName(String name) throws ResourceNotFoundException;

    public abstract void checkAndCreateTags(List<Tag> tags);

    public abstract void createLinkBetweenCertificateAndTag(int idNewCertificate, List<Tag> tags);

    public abstract List<CertificateDto> findAllByTagId(int id) throws ResourceNotFoundException;

    public abstract List<CertificateDto> searchByPartOfName(String partOfName) throws ResourceNotFoundException;

    public abstract List<CertificateDto> searchByPartOfDescription(String partOfDescription) throws ResourceNotFoundException;

    public abstract List<CertificateDto> findAllOrderByName(String order) throws ResourceNotFoundException;

    public abstract List<CertificateDto> findAllOrderByNameAndDate(String order) throws ResourceNotFoundException;

    public abstract List<CertificateDto> findAllOrderByDate(String order) throws ResourceNotFoundException;
}
