package com.epam.esm.service.impl;

import com.epam.esm.constant.CodeException;
import com.epam.esm.constant.TableColumn;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.specification.CertificateSpecification;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.mapper.impl.CertificateMapper;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validation.CertificateFieldValidator;
import com.epam.esm.validation.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is an implementation of CertificateService.
 */
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl extends CertificateService {

    private static final String SORT_ORDER_DESC = "desc";
    private static final String SORT_ORDER_ASC = "asc";
    private static final String TAG_NAME = "tag_name";
    private static final String PART_NAME = "part_name";
    private static final String PART_DESCRIPTION = "part_desc";
    private static final String TYPE_SORT = "type_sort";
    private static final String DATE = "date";
    private static final String NAME_DATE = "name_date";
    private static final String ORDER = "order";
    private static final String NAME = "name";
    private static final String DEFAULT_VALUE = "";

    private CertificateDao certificateDao;
    private TagDao tagDao;
    private CertificateMapper certificateMapper;
    private TagMapper tagMapper;
    private PaginationValidator paginationValidator;
    private CertificateFieldValidator certificateFieldValidator;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao,
                                  CertificateMapper certificateMapper, TagMapper tagMapper,
                                  PaginationValidator paginationValidator,
                                  CertificateFieldValidator certificateFieldValidator) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
        this.paginationValidator = paginationValidator;
        this.certificateFieldValidator = certificateFieldValidator;
    }

    @Transactional
    @Override
    public CertificateDto insert(CertificateDto certificateDto)
            throws ResourceAlreadyExistException, ServiceException {

        certificateFieldValidator.checkIsCountFieldsEqualNullMoreOne(certificateDto);

        Optional<Certificate> existedCertificate = certificateDao.findFirstByName(certificateDto.getName());
        if (existedCertificate.isPresent()) {
            throw new ResourceAlreadyExistException(CodeException.RESOURCE_ALREADY_EXIST, certificateDto.getName());
        }

        Set<TagDto> tagsFromDto = certificateDto.getTags();
        Set<Tag> tags = migrateListTagsFromDtoToEntity(tagsFromDto);
        Set<Tag> preparedTags = prepareTags(tags);

        Certificate certificate = certificateMapper.toEntity(certificateDto);
        certificate.setTags(preparedTags);
        certificate.setCreateDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());
        certificate.setLastUpdateDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());

        return certificateMapper.toDto(certificateDao.save(certificate));
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto updatedCertificateDto)
            throws ResourceNotFoundException {

        Certificate updatedCertificate = prepareCertificate(updatedCertificateDto);
        return certificateMapper.toDto(updatedCertificate);
    }

    private Certificate prepareCertificate(CertificateDto updatedCertificateDto) throws ResourceNotFoundException {

        int idUpdateCertificate = updatedCertificateDto.getId();
        Certificate existedCertificateForUpdate = certificateDao.findById(idUpdateCertificate)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RESOURCE_NOT_FOUND, idUpdateCertificate));

        if (updatedCertificateDto.getName() != null) {
            existedCertificateForUpdate.setName(updatedCertificateDto.getName());
        }
        if (updatedCertificateDto.getDescription() != null) {
            existedCertificateForUpdate.setDescription(updatedCertificateDto.getDescription());
        }
        if (updatedCertificateDto.getPrice() != null) {
            existedCertificateForUpdate.setPrice(updatedCertificateDto.getPrice());
        }
        if (updatedCertificateDto.getDuration() != null) {
            existedCertificateForUpdate.setDuration(updatedCertificateDto.getDuration());
        }

        existedCertificateForUpdate.setLastUpdateDate(LocalDateTime
                .now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());

        Set<Tag> tagListForUpdate;
        Set<TagDto> updatedTagDtoList = updatedCertificateDto.getTags();
        Set<Tag> existedTagList = existedCertificateForUpdate.getTags();

        if (!updatedTagDtoList.isEmpty()) {
            tagListForUpdate = migrateListTagsFromDtoToEntity(updatedTagDtoList);
            tagListForUpdate = prepareTags(tagListForUpdate);
            if (!existedTagList.isEmpty()) {
                tagListForUpdate.addAll(existedTagList);
            }
        } else {
            tagListForUpdate = existedTagList;
        }
        existedCertificateForUpdate.setTags(tagListForUpdate);

        return existedCertificateForUpdate;
    }

    private Set<Tag> prepareTags(Set<Tag> tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(tag -> (!tagDao.findByName(tag.getName()).isPresent()
                        ? tagDao.save(tag)
                        : tagDao.findByName(tag.getName()).get()))
                .collect(Collectors.toSet());
    }

    /**
     * Method for update only single field.
     *
     * @param updatedCertificateDto - certificate with one field for update.
     * @return updated certificate.
     * @throws ResourceNotFoundException - if this certificate won't found.
     * @throws ServiceException          - if there is more than one field to update.
     */
    @Transactional
    @Override
    public CertificateDto updateSingleField(CertificateDto updatedCertificateDto)
            throws ResourceNotFoundException, ServiceException {

        certificateFieldValidator.checkIsCountFieldsEqualNullLessOne(updatedCertificateDto);
        return update(updatedCertificateDto);
    }

    @Transactional
    @Override
    public void delete(int id) throws ResourceNotFoundException {
        Optional<Certificate> certificate = certificateDao.findById(id);
        checkEntityOnNull(certificate, id);
        certificateDao.delete(certificate.get());
    }

    @Override
    public CertificateDto findById(int id) throws ResourceNotFoundException {
        Optional<Certificate> certificate = certificateDao.findById(id);
        checkEntityOnNull(certificate, id);
        return certificateMapper.toDto(certificate.get());
    }

    @Override
    public List<CertificateDto> findCertificatesByParams(Map<String, String> params) throws ValidationParametersException {
        int limit = (int) certificateDao.count();
        int offset = 0;

        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }

        String tagName = params.getOrDefault(TAG_NAME, DEFAULT_VALUE);
        String partOfCertificateName = params.getOrDefault(PART_NAME, DEFAULT_VALUE);
        String partOfCertificateDescription = params.getOrDefault(PART_DESCRIPTION, DEFAULT_VALUE);

        Specification<Certificate> specification = CertificateSpecification.hasSeveralParams(tagName,
                partOfCertificateName, partOfCertificateDescription);

        Pageable page = PageRequest.of(offset, limit);
        List<Certificate> certificates = certificateDao.findAll(specification, page).getContent();
        List<CertificateDto> certificateList = migrateListCertificatesFromEntityToDto(certificates);

        String typeOfSort = params.getOrDefault(TYPE_SORT, NAME);
        String order = params.getOrDefault(ORDER, SORT_ORDER_ASC);
        return sortByTypeAndOrder(certificateList, typeOfSort, order);
    }

    @Override
    public List<CertificateDto> findAll(Map<String, String> params) throws ValidationParametersException {
        int limit = (int) certificateDao.count();
        int offset = 0;

        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }
        Sort sort = Sort.by(TableColumn.ID);
        Pageable page = PageRequest.of(offset, limit, sort);

        List<Certificate> certificates = certificateDao.findAll(page).getContent();
        return migrateListCertificatesFromEntityToDto(certificates);
    }

    @Override
    public List<CertificateDto> findCertificatesBySeveralTags(List<String> tagNames,
                                                              Map<String, String> params)
            throws ValidationParametersException {
        int limit = (int) certificateDao.count();
        int offset = 0;

        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }

        Specification<Certificate> specification = CertificateSpecification.hasSeveralTags(tagNames);

        List<Certificate> certificates = certificateDao.findAll(specification, PageRequest.of(offset, limit)).toList();
        return migrateListCertificatesFromEntityToDto(certificates);
    }


    private List<CertificateDto> migrateListCertificatesFromEntityToDto(List<Certificate> certificates) {
        return certificates.stream()
                .map(certificate -> certificateMapper.toDto(certificate))
                .collect(Collectors.toList());
    }

    private Set<Tag> migrateListTagsFromDtoToEntity(Set<TagDto> tags) {
        return tags.stream()
                .map(tagDto -> tagMapper.toEntity(tagDto))
                .collect(Collectors.toSet());
    }

    private List<CertificateDto> sortByTypeAndOrder(List<CertificateDto> certificates, String typeOfSort, String order) {
        Comparator<CertificateDto> comparator;
        switch (typeOfSort) {
            case DATE:
                comparator = Comparator.comparing(CertificateDto::getCreateDate);
                certificates = sortByComparatorAndOrder(certificates, comparator, order);
                break;
            case NAME_DATE:
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
            //sort order by default is asc
            certificateList = certificateList.stream()
                    .sorted(certificateDtoComparator)
                    .collect(Collectors.toList());
        }
        return certificateList;
    }
}
