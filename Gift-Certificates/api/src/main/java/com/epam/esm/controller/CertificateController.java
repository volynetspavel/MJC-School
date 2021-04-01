package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void insert(@RequestBody CertificateDto certificateDto) throws ResourceAlreadyExistException {
        certificateService.insert(certificateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<CertificateDto> findAll() throws ResourceNotFoundException {
        return certificateService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all-by-tag/{id}")
    public List<CertificateDto> findAllByTagId(@PathVariable int id) throws ResourceNotFoundException {
        return certificateService.findAllByTagId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable int id) throws ResourceNotFoundException {
        return certificateService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        certificateService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody CertificateDto certificateDto)
            throws ResourceNotFoundException, ResourceAlreadyExistException {
        certificateDto.setId(id);
        certificateService.update(certificateDto);
    }
}