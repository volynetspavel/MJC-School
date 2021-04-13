package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CertificateDto insert(@RequestBody CertificateDto certificateDto) throws ResourceAlreadyExistException {
        return certificateService.insert(certificateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable("id") int id) throws ResourceNotFoundException {
        return certificateService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tag/{id}")
    public List<CertificateDto> findAllByTagId(@PathVariable("id") int id) throws ResourceNotFoundException {
        return certificateService.findAllByTagId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/name")
    public List<CertificateDto> findAllOrderByName(@RequestParam("order") String order)
            throws ResourceNotFoundException {
        return certificateService.findAllOrderByName(order);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/date")
    public List<CertificateDto> findAllOrderByDate(@RequestParam("order") String order)
            throws ResourceNotFoundException {
        return certificateService.findAllOrderByDate(order);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/name_date")
    public List<CertificateDto> findAllOrderByNameAndDate(@RequestParam("order") String order)
            throws ResourceNotFoundException {
        return certificateService.findAllOrderByNameAndDate(order);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CertificateDto> findCertificatesByTagPartOfNamePartOfDescriptionAndOrderedByName(
            @RequestParam(value = "tag_name", required = false) String tagName,
            @RequestParam(value = "part_name", required = false) String partOfCertificateName,
            @RequestParam(value = "part_desc", required = false) String partOfCertificateDescription,
            @RequestParam(value = "order", required = false) String order) throws ResourceNotFoundException {
        return certificateService.findByTagPartOfNamePartOfDescriptionAndOrderedByName(tagName,
                partOfCertificateName, partOfCertificateDescription, order);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/name")
    public List<CertificateDto> searchByPartOfName(@RequestParam("part") String partOfName)
            throws ResourceNotFoundException {
        return certificateService.searchByPartOfName(partOfName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/description")
    public List<CertificateDto> searchByPartOfDescription(@RequestParam("part") String partOfDescription)
            throws ResourceNotFoundException {
        return certificateService.searchByPartOfDescription(partOfDescription);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        certificateService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CertificateDto update(@PathVariable("id") int id, @RequestBody CertificateDto certificateDto)
            throws ResourceNotFoundException, ResourceAlreadyExistException {
        certificateDto.setId(id);
        return certificateService.update(certificateDto);
    }
}