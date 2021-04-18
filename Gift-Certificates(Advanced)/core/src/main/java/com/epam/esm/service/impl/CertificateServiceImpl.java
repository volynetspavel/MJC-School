package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.impl.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is an implementation of CertificateService.
 */
@Service
public class CertificateServiceImpl implements CertificateService {

    private static final String SORT_ORDER_DESC = "desc";
    private static final String PAGE = "page";
    private static final String SIZE = "size";
    private static final String TAG_NAME = "tag_name";
    private static final String PART_NAME = "part_name";
    private static final String PART_DESC = "part_desc";
    private static final String TYPE_SORT = "type_sort";
    private static final String ORDER = "order";

    private CertificateDao certificateDao;
    private TagDao tagDao;
    private CertificateMapper certificateMapper;
    private Validator validator;

    public CertificateServiceImpl() {
    }

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao,
                                  CertificateMapper certificateMapper, Validator validator) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.certificateMapper = certificateMapper;
        this.validator = validator;
    }

    @Transactional
    @Override
    public CertificateDto insert(CertificateDto certificateDto) throws ResourceAlreadyExistException {
        if (certificateDao.findByName(certificateDto.getName()) != null) {
            throw new ResourceAlreadyExistException("Requested resource (name = "
                    + certificateDto.getName() + ") has already existed.");
        }
        List<Tag> tags = certificateDto.getTags();
        List<Tag> preparedTags = prepareTags(tags);

        Certificate certificate = certificateMapper.toEntity(certificateDto);
        certificate.setId(null);
        certificate.setTags(preparedTags);
        certificate.setCreateDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());
        certificate.setLastUpdateDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());

        return certificateMapper.toDto(certificateDao.insert(certificate));
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto updatedCertificateDto) throws ResourceNotFoundException {

        Certificate updatedCertificate = prepareCertificate(updatedCertificateDto);
        certificateDao.update(updatedCertificate);
        return certificateMapper.toDto(updatedCertificate);
    }

    /**
     * Method for update only single field.
     *
     * @param updatedCertificateDto - certificate with one field for update.
     * @return updated certificate.
     * @throws ResourceNotFoundException - if this certificate won't found.
     * @throws ServiceException          - if there is more than one field to update.
     */
    @Override
    public CertificateDto updateSingleField(CertificateDto updatedCertificateDto)
            throws ResourceNotFoundException, ServiceException {

        if (!validator.isCertificateContainsOnlySingleField(updatedCertificateDto)) {
            throw new ServiceException("Request contains more than one field.");
        }
        return update(updatedCertificateDto);
    }

    @Transactional
    @Override
    public void delete(int id) throws ResourceNotFoundException {
        Certificate certificate = certificateDao.findById(id);
        if (certificate == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        certificateDao.delete(certificate);
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
    public List<CertificateDto> findCertificatesByParams(Map<String, String> params) throws ResourceNotFoundException {
        int pageSize = certificateDao.getCount();
        int pageNumber = 0;

        if (params.containsKey(SIZE) && params.containsKey(PAGE)) {
            pageSize = Integer.parseInt(params.get(SIZE));
            pageNumber = (Integer.parseInt(params.get(PAGE)) - 1) * pageSize;
        }
        System.out.println("service>> pageSize " + pageSize);
        System.out.println("service>> pageNumber " + pageNumber);

        String tagName = params.getOrDefault(TAG_NAME, "");
        String partOfCertificateName = params.getOrDefault(PART_NAME, "");
        String partOfCertificateDescription = params.getOrDefault(PART_DESC, "");

        List<Certificate> certificateList = certificateDao.findCertificatesByParams(tagName,
                partOfCertificateName, partOfCertificateDescription, pageNumber, pageSize);
        checkListOnEmptyOrNull(certificateList);

        List<CertificateDto> certificates = migrateListFromEntityToDto(certificateList);

        String typeOfSort = params.getOrDefault(TYPE_SORT, "name");
        String order = params.getOrDefault(ORDER, "asc");

        return sortByTypeAndOrder(certificates, typeOfSort, order);
    }

    private List<CertificateDto> sortByTypeAndOrder(List<CertificateDto> certificates, String typeOfSort, String order) {
        Comparator<CertificateDto> comparator;
        switch (typeOfSort) {
            case "date":
                comparator = Comparator.comparing(CertificateDto::getCreateDate);
                certificates = sortByComparatorAndOrder(certificates, comparator, order);
                break;
            case "name_date":
                Comparator<CertificateDto> certificateDtoComparatorByName = Comparator.comparing(CertificateDto::getName);
                Comparator<CertificateDto> certificateDtoComparatorByDate = Comparator.comparing(CertificateDto::getCreateDate);
                if (order.equalsIgnoreCase(SORT_ORDER_DESC)) {
                    certificates = certificates.stream()
                            .sorted(certificateDtoComparatorByName.thenComparing(certificateDtoComparatorByDate).reversed())
                            .collect(Collectors.toList());
                } else {
                    //sort order by default is asc
                    certificates = certificates.stream()
                            .sorted(certificateDtoComparatorByName.thenComparing(certificateDtoComparatorByDate))
                            .collect(Collectors.toList());
                }
                break;
            default:
                comparator = Comparator.comparing(CertificateDto::getName);
                certificates = sortByComparatorAndOrder(certificates, comparator, order);
        }
        return certificates;
    }

    private List<CertificateDto> sortByComparatorAndOrder(List<CertificateDto> certificateList,
                                                          Comparator<CertificateDto> certificateDtoComparator,
                                                          String order) {

        if (order != null && order.equalsIgnoreCase(SORT_ORDER_DESC)) {
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

    @Override
    public List<CertificateDto> findAll(Map<String, String> params) throws ResourceNotFoundException {
        int pageSize = certificateDao.getCount();
        int pageNumber = 0;

        if (params.containsKey(SIZE) && params.containsKey(PAGE)) {
            pageSize = Integer.parseInt(params.get(SIZE));
            pageNumber = (Integer.parseInt(params.get(PAGE)) - 1) * pageSize;
        }

        List<Certificate> certificates = certificateDao.findAll(pageNumber, pageNumber);
        checkListOnEmptyOrNull(certificates);

        return migrateListFromEntityToDto(certificates);
    }

    @Override
    public List<CertificateDto> findCertificatesBySeveralTags(List<String> tagNames) throws ResourceNotFoundException {
        List<Certificate> certificates = certificateDao.findCertificatesBySeveralTags(tagNames);
        checkListOnEmptyOrNull(certificates);

        return migrateListFromEntityToDto(certificates);
    }

    public CertificateDto findByName(String name) throws ResourceNotFoundException {
        Certificate certificate = certificateDao.findByName(name);
        if (certificate == null) {
            throw new ResourceNotFoundException("Requested resource not found (name = " + name + ")");
        }
        return certificateMapper.toDto(certificate);
    }

    private List<Tag> prepareTags(List<Tag> tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        return tags.stream()
                .map(tag -> (tagDao.findByName(tag.getName()) == null
                        ? tagDao.insert(tag)
                        : tagDao.findByName(tag.getName())))
                .collect(Collectors.toList());
    }

    private Certificate prepareCertificate(CertificateDto updatedCertificateDto) throws ResourceNotFoundException {

        int idUpdateCertificate = updatedCertificateDto.getId();
        Certificate existedCertificate = certificateDao.findById(idUpdateCertificate);
        if (existedCertificate == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + idUpdateCertificate + ")");
        }
        if (updatedCertificateDto.getName() == null) {
            updatedCertificateDto.setName(existedCertificate.getName());
        }
        if (updatedCertificateDto.getDescription() == null) {
            updatedCertificateDto.setDescription(existedCertificate.getDescription());
        }
        if (updatedCertificateDto.getPrice() == null) {
            updatedCertificateDto.setPrice(existedCertificate.getPrice());
        }
        if (updatedCertificateDto.getDuration() == null) {
            updatedCertificateDto.setDuration(existedCertificate.getDuration());
        }
        updatedCertificateDto.setCreateDate(existedCertificate.getCreateDate());
        updatedCertificateDto.setLastUpdateDate(LocalDateTime
                .now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());

        List<Tag> updatedTags;
        if (updatedCertificateDto.getTags() == null) {
            updatedTags = existedCertificate.getTags();
        } else {
            if (existedCertificate.getTags() != null) {
                updatedTags = prepareTags(updatedCertificateDto.getTags());
                updatedTags.addAll(existedCertificate.getTags());
            } else {
                updatedTags = prepareTags(updatedCertificateDto.getTags());
                updatedCertificateDto.setTags(updatedTags);
            }
        }
        updatedCertificateDto.setTags(updatedTags);

        return certificateMapper.toEntity(updatedCertificateDto);
    }

    private void checkListOnEmptyOrNull(List<Certificate> certificates) throws ResourceNotFoundException {
        if (certificates == null || certificates.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found");
        }
    }

    private List<CertificateDto> migrateListFromEntityToDto(List<Certificate> certificates) {
        return certificates.stream()
                .map(certificate -> certificateMapper.toDto(certificate))
                .collect(Collectors.toList());
    }
}
