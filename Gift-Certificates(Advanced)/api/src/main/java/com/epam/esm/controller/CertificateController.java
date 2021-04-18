package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Class is used to send requests from the client to the service layer for certificate entity.
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CertificateDto insert(@RequestBody CertificateDto certificateDto)
            throws ResourceAlreadyExistException {
        return certificateService.insert(certificateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CertificateDto update(@PathVariable("id") int id, @RequestBody CertificateDto certificateDto)
            throws ResourceNotFoundException, ResourceAlreadyExistException {
        certificateDto.setId(id);
        return certificateService.update(certificateDto);
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
    public CertificateDto updateSingleField(@PathVariable("id") int id, @RequestBody CertificateDto certificateDto)
            throws ResourceNotFoundException, ServiceException {
        certificateDto.setId(id);
        return certificateService.updateSingleField(certificateDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        certificateService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable("id") int id) throws ResourceNotFoundException {
        return certificateService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CertificateDto> findCertificatesByParams(@RequestParam Map<String, String> params)
            throws ResourceNotFoundException {
        return certificateService.findCertificatesByParams(params);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<CertificateDto> findCertificatesBySeveralTags(@RequestBody List<String> tagNames,
                                                              @RequestParam Map<String, String> params)
            throws ResourceNotFoundException {
        return certificateService.findCertificatesBySeveralTags(tagNames, params);
    }
}