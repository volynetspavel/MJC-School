package com.epam.esm.mapper.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.mapper.AbstractMapper;
import com.epam.esm.model.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for certificate entity.
 */
@Component
public class CertificateMapper extends AbstractMapper<Certificate, CertificateDto> {

    @Autowired
    public CertificateMapper() {
        super(Certificate.class, CertificateDto.class);
    }
}
