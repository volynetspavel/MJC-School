package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an implementation of CertificateService.
 */
@Service
public class CertificateServiceImpl implements CertificateService {

    private static final String SORT_ORDER_DESC = "desc";

    private CertificateDao certificateDao;
    private TagDao tagDao;
    private CertificateMapper certificateMapper;

    public CertificateServiceImpl() {
    }

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao,
                                  CertificateMapper certificateMapper) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public CertificateDto insert(CertificateDto certificateDto) throws ResourceAlreadyExistException {
        if (certificateDao.findByName(certificateDto.getName()) != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + certificateDto.getName() + ") has already existed.");
        }

        List<Tag> tags = certificateDto.getTags();
        insertTags(tags);

        Certificate certificate = certificateMapper.toEntity(certificateDto);
        certificate.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        certificate.setLastUpdateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());

        int idNewCertificate = certificateDao.insert(certificate);
        createLinkBetweenCertificateAndTag(idNewCertificate, tags);

        CertificateDto newCertificateDto = certificateMapper.toDto(certificateDao.findById(idNewCertificate));
        newCertificateDto.setTags(tags);
        return newCertificateDto;
    }

    @Override
    public CertificateDto update(CertificateDto updatedCertificateDto)
            throws ResourceNotFoundException {

        int idUpdateCertificate = updatedCertificateDto.getId();
        if (certificateDao.findById(idUpdateCertificate) == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + idUpdateCertificate + ")");
        }

        Certificate updatedCertificate = certificateMapper.toEntity(updatedCertificateDto);
        updatedCertificate.setLastUpdateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        certificateDao.update(updatedCertificate);

        List<Tag> tags = updatedCertificateDto.getTags();
        if (tags != null) {
            insertTags(tags);
            createLinkBetweenCertificateAndTag(idUpdateCertificate, tags);
        }
        CertificateDto certificateWithUpdatedFields = certificateMapper
                .toDto(certificateDao.findById(idUpdateCertificate));
        certificateWithUpdatedFields.setTags(tagDao.findTagsByCertificateId(idUpdateCertificate));
        return certificateWithUpdatedFields;
    }

    @Override
    public List<CertificateDto> findAll() throws ResourceNotFoundException {
        List<Certificate> certificates = certificateDao.findAll();
        checkListOnEmptyOrNull(certificates);

        return fromEntityToDtoAndAddListTags(certificates);
    }

    @Override
    public List<CertificateDto> findAllByTagId(int id) throws ResourceNotFoundException {
        List<Certificate> certificates = certificateDao.findAllByTagId(id);
        checkListOnEmptyOrNull(certificates);

        return fromEntityToDtoAndAddListTags(certificates);
    }

    @Override
    public List<CertificateDto> searchByPartOfName(String partOfName) throws ResourceNotFoundException {
        List<Certificate> certificates = certificateDao.searchByPartOfName(partOfName);
        checkListOnEmptyOrNull(certificates);

        return fromEntityToDtoAndAddListTags(certificates);
    }

    @Override
    public List<CertificateDto> searchByPartOfDescription(String partOfDescription)
            throws ResourceNotFoundException {

        List<Certificate> certificates = certificateDao.searchByPartOfDescription(partOfDescription);
        checkListOnEmptyOrNull(certificates);

        return fromEntityToDtoAndAddListTags(certificates);
    }

    @Override
    public List<CertificateDto> findAllOrderByName(String order) throws ResourceNotFoundException {
        List<CertificateDto> certificateList = findAll();
        Comparator<CertificateDto> certificateDtoComparatorByName = Comparator.comparing(CertificateDto::getName);
        return sortByComparatorAndOrder(certificateList, certificateDtoComparatorByName, order);
    }

    @Override
    public List<CertificateDto> findAllOrderByDate(String order) throws ResourceNotFoundException {
        List<CertificateDto> certificateList = findAll();
        Comparator<CertificateDto> certificateDtoComparatorByDate = Comparator.comparing(CertificateDto::getCreateDate);
        return sortByComparatorAndOrder(certificateList, certificateDtoComparatorByDate, order);
    }

    @Override
    public List<CertificateDto> findAllOrderByNameAndDate(String order) throws ResourceNotFoundException {
        List<CertificateDto> certificateList = findAll();

        Comparator<CertificateDto> certificateDtoComparatorByName = Comparator.comparing(CertificateDto::getName);
        Comparator<CertificateDto> certificateDtoComparatorByDate = Comparator.comparing(CertificateDto::getCreateDate);

        if (order.equalsIgnoreCase(SORT_ORDER_DESC)) {
            certificateList = certificateList.stream()
                    .sorted(certificateDtoComparatorByName.thenComparing(certificateDtoComparatorByDate).reversed())
                    .collect(Collectors.toList());
        } else {
            //sort order by default is asc
            certificateList = certificateList.stream()
                    .sorted(certificateDtoComparatorByName.thenComparing(certificateDtoComparatorByDate))
                    .collect(Collectors.toList());
        }
        return certificateList;
    }

    @Override
    public CertificateDto findById(int id) throws ResourceNotFoundException {
        Certificate certificate = certificateDao.findById(id);
        if (certificate == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        return certificateMapper.toDto(certificate);
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {
        Certificate certificate = certificateDao.findById(id);
        if (certificate == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        certificateDao.delete(id);
    }

    @Override
    public CertificateDto findByName(String name) throws ResourceNotFoundException {
        Certificate certificate = certificateDao.findByName(name);
        if (certificate == null) {
            throw new ResourceNotFoundException("Requested resource not found (name = " + name + ")");
        }
        return certificateMapper.toDto(certificate);
    }

    @Override
    public void insertTags(List<Tag> tags) {
        for (Tag tag : tags) {
            if (tagDao.findByName(tag.getName()) == null) {
                tagDao.insert(tag);
            }
        }
    }

    @Override
    public void createLinkBetweenCertificateAndTag(int idNewCertificate, List<Tag> tags) {
        for (Tag tag : tags) {
            int idTag = tagDao.findByName(tag.getName()).getId();
            certificateDao.insertLinkBetweenCertificateAndTag(idNewCertificate, idTag);
        }
    }

    private void checkListOnEmptyOrNull(List<Certificate> certificates) throws ResourceNotFoundException {
        if (certificates == null || certificates.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found");
        }
    }

    private List<CertificateDto> fromEntityToDtoAndAddListTags(List<Certificate> certificates) {
        return certificates.stream()
                .map(certificate -> certificateMapper.toDto(certificate))
                .peek(certificateDto -> certificateDto.setTags(tagDao.findTagsByCertificateId(certificateDto.getId())))
                .collect(Collectors.toList());
    }

    private List<CertificateDto> sortByComparatorAndOrder(List<CertificateDto> certificateList,
                                                          Comparator<CertificateDto> certificateDtoComparator,
                                                          String order) {
        if (order.equalsIgnoreCase(SORT_ORDER_DESC)) {
            certificateList = certificateList.stream()
                    .sorted(certificateDtoComparator.reversed())
                    .collect(Collectors.toList());
        } else {
            //sort order by default is ASC
            certificateList = certificateList.stream()
                    .sorted(certificateDtoComparator)
                    .collect(Collectors.toList());
        }
        return certificateList;
    }
}
