package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import org.springframework.stereotype.Component;

/**
 * Contains methods for validating data of entities.
 */
@Component
public class Validator {

    public boolean isCertificateContainsOnlySingleField(CertificateDto certificateDto){
        int countFieldsNotNull = 0;

        if (certificateDto.getName() != null){
            countFieldsNotNull++;
        }
        if (certificateDto.getDescription() != null){
            countFieldsNotNull++;
        }
        if (certificateDto.getPrice() != null){
            countFieldsNotNull++;
        }
        if (certificateDto.getDuration() != null){
            countFieldsNotNull++;
        }
        if (certificateDto.getCreateDate() != null){
            countFieldsNotNull++;
        }
        if (certificateDto.getLastUpdateDate() != null){
            countFieldsNotNull++;
        }
        if (certificateDto.getTags() != null){
            countFieldsNotNull++;
        }

        return countFieldsNotNull < 2;
    }
}
