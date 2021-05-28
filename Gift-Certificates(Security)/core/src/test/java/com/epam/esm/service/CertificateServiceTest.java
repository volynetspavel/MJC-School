package com.epam.esm.service;

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
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.validation.PaginationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class for testing methods from service layer for certificate.
 */
@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;
    @Mock
    private CertificateDao certificateDao;
    @Mock
    private CertificateMapper certificateMapper;
    @Mock
    private PaginationValidator paginationValidator;
    @Mock
    private TagDao tagDao;
    @Mock
    private TagMapper tagMapper;

    @DisplayName("Testing method insert() on positive result")
    @Test
    void insertSuccessTest() throws ResourceAlreadyExistException, ServiceException {
        int tagId = 1;
        String tagName = "tag";

        TagDto tagDto = createTagDto(tagId, tagName);
        Optional<Tag> tag = createTag(tagId, tagName);

        CertificateDto newCertificateDto = getCertificateDto(0);
        Optional<Certificate> newCertificate = getCertificate(0);

        when(tagDao.findByName(tagName)).thenReturn(tag);
        when(tagMapper.toDto(tag.get())).thenReturn(tagDto);

        when(tagMapper.toEntity(tagDto)).thenReturn(tag.get());

        when(certificateMapper.toEntity(newCertificateDto)).thenReturn(newCertificate.get());
        newCertificate.get().setCreateDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());
        newCertificate.get().setLastUpdateDate(LocalDateTime.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());

        when(certificateDao.save(newCertificate.get())).thenReturn(newCertificate.get());
        when(certificateMapper.toDto(newCertificate.get())).thenReturn(newCertificateDto);

        CertificateDto certificateDtoAfterInsert = certificateService.insert(newCertificateDto);
        assertEquals(newCertificateDto, certificateDtoAfterInsert);
    }

    @DisplayName("Testing method insert() on negative result")
    @Test
    void insertThrowsExceptionTest() {
        String name = "New Off road jeep tour";
        Optional<Certificate> newCertificate = Optional.of(new Certificate());
        newCertificate.get().setName(name);
        CertificateDto newCertificateDto = new CertificateDto();
        newCertificateDto.setName(name);

        when(certificateDao.findFirstByName(name)).thenReturn(newCertificate);

        assertThrows(ResourceAlreadyExistException.class,
                () -> certificateService.insert(newCertificateDto));
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    void updateSuccessTest() throws ResourceNotFoundException, ServiceException {
        int id = 2;
        String newName = "New dermaplaning";
        String newDescription = null;
        BigDecimal newPrice = new BigDecimal("100");
        Integer newDuration = null;
        String newCreateDate = null;
        String newLastUpdateDate = null;
        Set<TagDto> newTagDtoList = new HashSet<>(Arrays.asList(new TagDto()));
        CertificateDto updatedCertificateDto = createCertificateDto(id, newName, newDescription,
                newPrice, newDuration, newCreateDate, newLastUpdateDate, newTagDtoList);

        int tagId = 1;
        String tagName = "tag1";
        Optional<Tag> tag = getTag(tagId);

        TagDto tagDto = getTagDto(tagId);
        Set<TagDto> tagDtoList = new HashSet<>();
        tagDtoList.add(tagDto);

        Optional<Certificate> certificateFromDataBase = getCertificate(id);


        when(certificateDao.findById(id)).thenReturn(certificateFromDataBase);
        updatedCertificateDto.setLastUpdateDate(LocalDateTime
                .now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS).toString());
        updatedCertificateDto.setTags(tagDtoList);

        when(tagDao.findByName(tagName)).thenReturn(tag);
        when(tagMapper.toDto(tag.get())).thenReturn(tagDto);
        when(certificateMapper.toEntity(updatedCertificateDto)).thenReturn(certificateFromDataBase.get());
        when(certificateMapper.toDto(certificateFromDataBase.get())).thenReturn(updatedCertificateDto);

        CertificateDto certificateDtoAfterUpdate = certificateService.update(updatedCertificateDto);
        assertEquals(certificateDtoAfterUpdate, updatedCertificateDto);
    }

    @DisplayName("Testing method update() on negative result with ResourceNotFoundException")
    @Test
    void updateThrowsResourceNotFoundExceptionTest() {
        int id = 20;
        CertificateDto newCertificateDto = getCertificateDto(id);

        when(certificateDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> certificateService.update(newCertificateDto));
    }

    @DisplayName("Testing method update() on positive result")
    @Test
    void updateSingleFieldThrowsExceptionTest() {
        int id = 2;
        String newName = "New dermaplaning";
        String newDescription = null;
        BigDecimal newPrice = new BigDecimal("100");
        Integer newDuration = null;
        String newCreateDate = null;
        String newLastUpdateDate = null;
        Set<TagDto> newTagDtoList = new HashSet<>(Arrays.asList(new TagDto()));

        CertificateDto updatedCertificateDto = createCertificateDto(id, newName, newDescription,
                newPrice, newDuration, newCreateDate, newLastUpdateDate, newTagDtoList);

        assertThrows(ServiceException.class,
                () -> certificateService.updateSingleField(updatedCertificateDto));
    }

    @DisplayName("Testing method delete() by id of certificate on positive result")
    @Test
    void deleteByIdSuccessTest() throws ResourceNotFoundException {
        int id = 3;
        Optional<Certificate> certificate = getCertificate(id);

        when(certificateDao.findById(id)).thenReturn(certificate);
        certificateService.delete(id);
        verify(certificateDao, times(1)).delete(certificate.get());
    }

    @DisplayName("Testing method delete() by id of certificate on negative result")
    @Test
    void deleteByIdThrowsExceptionTest() {
        int id = 5;
        when(certificateDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> certificateService.findById(id));
    }

    @DisplayName("Testing method findById() on positive result")
    @Test
    void findByIdSuccessTest() throws ResourceNotFoundException {
        int id = 1;

        Optional<Certificate> actual = getCertificate(1);
        CertificateDto actualDto = getCertificateDto(1);

        when(certificateDao.findById(id)).thenReturn(actual);
        when(certificateMapper.toDto(actual.get())).thenReturn(actualDto);
        CertificateDto expectedDto = certificateService.findById(id);

        assertEquals(expectedDto, actualDto);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    void findByIdThrowsExceptionTest() {
        int id = 10;
        when(certificateDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> certificateService.findById(id));
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void findAllSuccessTest() throws ValidationParametersException {

        int offset = 0;
        int limit = 3;
        when(certificateDao.count()).thenReturn((long) 3);
        when(paginationValidator.validatePaginationParameters(new HashMap<>())).thenReturn(false);

        Optional<Certificate> certificate1 = getCertificate(1);
        Optional<Certificate> certificate2 = getCertificate(2);
        Optional<Certificate> certificate3 = getCertificate(3);

        List<Certificate> expectedCertificateList = Arrays.asList(certificate1.get(),
                certificate2.get(), certificate3.get());
        Page<Certificate> expectedCertificatePage = new PageImpl<>(expectedCertificateList);

        CertificateDto certificateDto1 = getCertificateDto(1);
        CertificateDto certificateDto2 = getCertificateDto(2);
        CertificateDto certificateDto3 = getCertificateDto(3);

        List<CertificateDto> expectedCertificateDtoList = Arrays.asList(certificateDto1,
                certificateDto2, certificateDto3);

        when(certificateDao.findAll(PageRequest.of(offset, limit))).thenReturn(expectedCertificatePage);
        when(certificateMapper.toDto(certificate1.get())).thenReturn(certificateDto1);
        when(certificateMapper.toDto(certificate2.get())).thenReturn(certificateDto2);
        when(certificateMapper.toDto(certificate3.get())).thenReturn(certificateDto3);

        List<CertificateDto> actualCertificateDtoList = certificateService.findAll(new HashMap<>());

        assertEquals(expectedCertificateDtoList, actualCertificateDtoList);
    }

    @DisplayName("Testing method findCertificatesBySeveralTags() on positive result")
    @Test
    void findCertificatesBySeveralTagsSuccessTest() throws ValidationParametersException {
        List<String> tagNames = Arrays.asList("tag1", "tag2");
        Optional<Tag> tag1 = getTag(1);
        Optional<Tag> tag2 = getTag(2);
        Set<Tag> tagList = new HashSet<>(Arrays.asList(tag1.get(), tag2.get()));

        TagDto tagDto1 = getTagDto(1);
        TagDto tagDto2 = getTagDto(2);
        Set<TagDto> tagDtoList = new HashSet<>(Arrays.asList(tagDto1, tagDto2));

        int offset = 0;
        int limit = 3;
        when(certificateDao.count()).thenReturn((long) 3);
        when(paginationValidator.validatePaginationParameters(new HashMap<>())).thenReturn(false);

        Optional<Certificate> certificate1 = getCertificate(1);
        certificate1.get().setTags(tagList);
        Optional<Certificate> certificate2 = getCertificate(2);
        certificate1.get().setTags(tagList);
        Optional<Certificate> certificate3 = getCertificate(3);
        certificate1.get().setTags(tagList);

        List<Certificate> expectedCertificateList = Arrays.asList(certificate1.get(),
                certificate2.get(), certificate3.get());
        Page<Certificate> expectedCertificatePage = new PageImpl<>(expectedCertificateList);

        CertificateDto certificateDto1 = getCertificateDto(1);
        certificateDto1.setTags(tagDtoList);
        CertificateDto certificateDto2 = getCertificateDto(2);
        certificateDto2.setTags(tagDtoList);
        CertificateDto certificateDto3 = getCertificateDto(3);
        certificateDto3.setTags(tagDtoList);

        List<CertificateDto> expectedCertificateDtoList = Arrays.asList(certificateDto1,
                certificateDto2, certificateDto3);

        Specification<Certificate> specification = null;
        when(CertificateSpecification.hasSeveralTags(tagNames)).thenReturn(specification);
        when(certificateDao.findAll(specification, PageRequest.of(offset, limit))).thenReturn(expectedCertificatePage);
        when(certificateMapper.toDto(certificate1.get())).thenReturn(certificateDto1);
        when(certificateMapper.toDto(certificate2.get())).thenReturn(certificateDto2);
        when(certificateMapper.toDto(certificate3.get())).thenReturn(certificateDto3);

        List<CertificateDto> actualCertificateDtoList = certificateService.findCertificatesBySeveralTags(tagNames, new HashMap<>());

        assertEquals(expectedCertificateDtoList, actualCertificateDtoList);
    }

    private Optional<Tag> getTag(int num) {
        int tagId = 1 + num;
        String tagName = "tag" + num;
        return createTag(tagId, tagName);
    }

    private TagDto getTagDto(int num) {
        int tagId = 1 + num;
        String tagName = "tag" + num;
        return createTagDto(tagId, tagName);
    }

    private Optional<Certificate> getCertificate(int num) {
        int id = 1 + num;
        String name = "New Off road jeep tour" + num;
        String description = "We offer the active and courageous an extreme off-road trip." + num;
        BigDecimal price = new BigDecimal(350).add(new BigDecimal(num));
        Integer duration = 15 + num;
        String createDate = "2021-03-25T06:17:15.656";
        String lastUpdateDate = "2021-04-01T13:08:39.149";

        int tagId = 1 + num;
        String tagName = "tag1" + num;
        Optional<Tag> tag = createTag(tagId, tagName);
        Set<Tag> tagList = new HashSet<>(Arrays.asList(tag.get()));

        return createCertificate(id, name, description, price, duration, createDate, lastUpdateDate, tagList);
    }

    private CertificateDto getCertificateDto(int num) {
        int id = num;
        String name = "New Off road jeep tour" + num;
        String description = "We offer the active and courageous an extreme off-road trip." + num;
        BigDecimal price = new BigDecimal(350).add(new BigDecimal(num));
        Integer duration = 15 + num;
        String createDate = "2021-03-25T06:17:15.656";
        String lastUpdateDate = "2021-04-01T13:08:39.149";

        int tagId = 1 + num;
        String tagName = "tag1" + num;
        TagDto tagDto = createTagDto(tagId, tagName);
        Set<TagDto> tagDtoList = new HashSet<>(Arrays.asList(tagDto));

        return createCertificateDto(id, name, description, price, duration, createDate, lastUpdateDate, tagDtoList);
    }

    private Optional<Certificate> createCertificate(int id, String name, String description, BigDecimal price,
                                          Integer duration, String createDate, String lastUpdateDate,
                                          Set<Tag> tagList) {

        Certificate certificate = new Certificate();
        certificate.setId(id);
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        certificate.setCreateDate(createDate);
        certificate.setLastUpdateDate(lastUpdateDate);
        certificate.setTags(tagList);

        return Optional.of(certificate);
    }

    private CertificateDto createCertificateDto(int id, String name, String description, BigDecimal price,
                                                Integer duration, String createDate, String lastUpdateDate,
                                                Set<TagDto> tagDtoList) {

        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(id);
        certificateDto.setName(name);
        certificateDto.setDescription(description);
        certificateDto.setPrice(price);
        certificateDto.setDuration(duration);
        certificateDto.setCreateDate(createDate);
        certificateDto.setLastUpdateDate(lastUpdateDate);
        certificateDto.setTags(tagDtoList);

        return certificateDto;
    }

    private Optional<Tag> createTag(int id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        return Optional.of(tag);
    }

    private TagDto createTagDto(int id, String name) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagDto.setName(name);

        return tagDto;
    }
}