package com.epam.esm.validation;

import com.epam.esm.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Contains methods for validating parameters of pagination.
 */
@Component
public class PaginationValidator {

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    private int limit;
    private int offset;

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public boolean validatePaginationParameters(Map<String, String> params) throws ValidationException {
        if (params.containsKey(SIZE) && params.containsKey(PAGE)) {
            String sizeValue = params.get(SIZE);
            String pageValue = params.get(PAGE);

            if (isPositiveNumber(sizeValue) && isPositiveNumber(pageValue)) {
                limit = Integer.parseInt(sizeValue);
                offset = (Integer.parseInt(pageValue) - 1) * limit;
            } else {
                throw new ValidationException("Parameters of pagination are incorrect. Please, check parameters.");
            }
            if (offset < 0) {
                throw new ValidationException("Parameter of page must be greater than 0.");
            }
            if (limit < 1) {
                throw new ValidationException("Parameter of size must be greater than 0.");
            }
            return true;
        }
        return false;
    }

    private boolean isPositiveNumber(String value) {
        return StringUtils.isNumeric(value);
    }
}
