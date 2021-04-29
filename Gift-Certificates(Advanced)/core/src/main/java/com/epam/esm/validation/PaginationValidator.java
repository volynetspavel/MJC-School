package com.epam.esm.validation;

import com.epam.esm.constant.CodeException;
import com.epam.esm.exception.ValidationParametersException;
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

    public boolean validatePaginationParameters(Map<String, String> params) throws ValidationParametersException {
        if (params.containsKey(SIZE) && params.containsKey(PAGE)) {
            String sizeValue = params.get(SIZE);
            String pageValue = params.get(PAGE);

            if (isPositiveNumber(sizeValue) && isPositiveNumber(pageValue)) {
                limit = Integer.parseInt(sizeValue);
                offset = (Integer.parseInt(pageValue) - 1) * limit;
            } else {
                throw new ValidationParametersException(CodeException.NOT_VALID_PAGINATION);
            }
            if (offset < 0) {
                throw new ValidationParametersException(CodeException.NOT_VALID_PAGINATION_PAGE);
            }
            if (limit < 1) {
                throw new ValidationParametersException(CodeException.NOT_VALID_PAGINATION_SIZE);
            }
            return true;
        }
        return false;
    }

    private boolean isPositiveNumber(String value) {
        return StringUtils.isNumeric(value);
    }
}
