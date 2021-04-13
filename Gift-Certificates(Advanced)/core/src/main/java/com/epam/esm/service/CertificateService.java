package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Tag;

import java.util.List;

/**
 * This class is a layer for interacting with CertificateDao.
 */
public interface CertificateService extends Service<CertificateDto> {

    CertificateDto findByName(String name) throws ResourceNotFoundException;

    void insertTags(List<Tag> tags);

    void createLinkBetweenCertificateAndTag(int idNewCertificate, List<Tag> tags);

    List<CertificateDto> findAllByTagId(int id) throws ResourceNotFoundException;

    List<CertificateDto> searchByPartOfName(String partOfName) throws ResourceNotFoundException;

    List<CertificateDto> searchByPartOfDescription(String partOfDescription) throws ResourceNotFoundException;

    List<CertificateDto> findAllOrderByName(String order) throws ResourceNotFoundException;

    List<CertificateDto> findAllOrderByNameAndDate(String order) throws ResourceNotFoundException;

    List<CertificateDto> findAllOrderByDate(String order) throws ResourceNotFoundException;

    List<CertificateDto> findByTagPartOfNamePartOfDescriptionAndOrderedByName(String tagName,
                                                                              String partOfCertificateName,
                                                                              String partOfCertificateDescription,
                                                                              String order) throws ResourceNotFoundException;

    CertificateDto updateSingleField(CertificateDto certificateDto) throws ResourceNotFoundException, ServiceException;
}
