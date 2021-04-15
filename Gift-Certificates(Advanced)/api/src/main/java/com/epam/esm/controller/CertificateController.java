package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
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
    public List<CertificateDto> findCertificatesByTagPartOfNamePartOfDescriptionAndOrderedByName(
            @RequestParam(value = "tag_name", required = false) String tagName,
            @RequestParam(value = "part_name", required = false) String partOfCertificateName,
            @RequestParam(value = "part_desc", required = false) String partOfCertificateDescription,
            @RequestParam(value = "order", required = false) String order) throws ResourceNotFoundException {
        return certificateService.findByTagPartOfNamePartOfDescriptionAndOrderedByName(tagName,
                partOfCertificateName, partOfCertificateDescription, order);
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
}