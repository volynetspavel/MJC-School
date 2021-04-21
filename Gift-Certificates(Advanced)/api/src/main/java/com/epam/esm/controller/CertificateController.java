package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.hateoas.CertificateHateoas;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Class is used to send requests from the client to the service layer for certificate entity.
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private CertificateService certificateService;
    private CertificateHateoas certificateHateoas;

    @Autowired
    public CertificateController(CertificateService certificateService, CertificateHateoas certificateHateoas) {
        this.certificateService = certificateService;
        this.certificateHateoas = certificateHateoas;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CertificateDto insert(@Valid @RequestBody CertificateDto certificateDto)
            throws ResourceAlreadyExistException, ResourceNotFoundException, ValidationException {
        CertificateDto newCertificateDto = certificateService.insert(certificateDto);
        certificateHateoas.addLinksForCertificateDto(newCertificateDto);
        return newCertificateDto;

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CertificateDto update(@PathVariable("id") int id, @Valid @RequestBody CertificateDto certificateDto)
            throws ResourceNotFoundException, ResourceAlreadyExistException, ValidationException {
        certificateDto.setId(id);
        CertificateDto updatedCertificateDto = certificateService.update(certificateDto);
        certificateHateoas.addLinksForCertificateDto(updatedCertificateDto);
        return updatedCertificateDto;
    }

    /**
     * Method for update only single field.
     *
     * @param id             - id of certificate.
     * @param certificateDto - certificate with one field for update.
     * @return updated certificate.
     * @throws ResourceNotFoundException - if this certificate won't found.
     * @throws ServiceException          - if there is more than one field to update.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/single/{id}")
    public CertificateDto updateSingleField(@PathVariable("id") int id,
                                            @Valid @RequestBody CertificateDto certificateDto)
            throws ResourceNotFoundException, ServiceException, ValidationException {
        certificateDto.setId(id);
        CertificateDto updatedCertificateDto = certificateService.updateSingleField(certificateDto);
        certificateHateoas.addLinksForCertificateDto(updatedCertificateDto);
        return updatedCertificateDto;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        certificateService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable("id") int id) throws ResourceNotFoundException, ValidationException {
        CertificateDto certificateDto = certificateService.findById(id);
        certificateHateoas.addLinksForCertificateDto(certificateDto);
        return certificateDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CertificateDto> findCertificatesByParams(@RequestParam Map<String, String> params)
            throws ValidationException, ResourceNotFoundException {
        List<CertificateDto> certificateDtoList = certificateService.findCertificatesByParams(params);
        certificateHateoas.addLinksForListOfCertificateDto(certificateDtoList);
        return certificateDtoList;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<CertificateDto> findCertificatesBySeveralTags(@RequestParam(name = "tags") List<String> tagNames,
                                                              @RequestParam Map<String, String> params)
            throws ValidationException, ResourceNotFoundException {
        List<CertificateDto> certificateDtoList = certificateService.findCertificatesBySeveralTags(tagNames, params);
        certificateHateoas.addLinksForListOfCertificateDto(certificateDtoList);
        return certificateDtoList;
    }
}