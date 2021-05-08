package com.epam.esm.constant;

/**
 * Contains list of codes for exceptions.
 */
public class CodeException {

    public static final String RESOURCE_NOT_FOUND = "7701";
    public static final String RESOURCE_NOT_FOUND_WITHOUT_ID = "7702";
    public static final String RESOURCE_NOT_FOUND_BY_USER_ID = "7703";
    public static final String USER_EMAIL_NOT_FOUND = "7704";
    public static final String CERTIFICATE_NOT_FOUND = "7705";

    public static final String RESOURCE_ALREADY_EXIST = "9901";

    public static final String NOT_VALID_PAGINATION = "3301";
    public static final String NOT_VALID_PAGINATION_PAGE = "3302";
    public static final String NOT_VALID_PAGINATION_SIZE = "3303";
    public static final String NOT_VALID_PARAMETER_OF_REQUEST = "3304";

    public static final String NULL_FIELDS_MORE_THEN_ONE = "2201";
    public static final String NULL_FIELDS_LESS_THEN_ONE = "2202";

    public static final String INVALID_DATA = "5501";
    public static final String INVALID_URI = "5502";
    public static final String INVALID_FIELD = "5503";

    public static final String AUDIT_LOG_ERROR = "6601";

    public static final String UNEXPECTED_ERROR = "1101";

    public static final String UNAUTHORIZED = "4401";
    public static final String TOKEN_EXPIRED = "4402";
    public static final String INVALID_TOKEN = "4403";
    public static final String FORBIDDEN = "4404";

}
