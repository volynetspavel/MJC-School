package com.epam.esm.validation;

import com.epam.esm.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Class for testing methods PaginationValidator class.
 */
@ExtendWith(MockitoExtension.class)
class PaginationValidatorTest {

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    @InjectMocks
    private PaginationValidator paginationValidator;

    @DisplayName("Testing method validatePaginationParameters() on positive result")
    @Test
    void validatePaginationParametersSuccessTest() throws ValidationException {
        String page = "1";
        String size = "5";
        Map<String, String> params = getParams(page, size);

        Assertions.assertTrue(paginationValidator.validatePaginationParameters(params));
    }

    @DisplayName("Testing method validatePaginationParameters() on negative result " +
            "when map of params don't have params.")
    @Test
    void validatePaginationParametersWithoutParametersTest() throws ValidationException {
        Map<String, String> params = new HashMap<>();

        Assertions.assertFalse(paginationValidator.validatePaginationParameters(params));
    }

    static Stream<Arguments> badData() {
        return Stream.of(
                //when one of values page or size will be incorrect, then throw exception
                Arguments.of((Object) new String[]{"-1", "20"}),
                Arguments.of((Object) new String[]{"word", "20"}),
                Arguments.of((Object) new String[]{"", "20"}),
                Arguments.of((Object) new String[]{"12.3", "20"}),
                Arguments.of((Object) new String[]{"+123", "20"}),
                Arguments.of((Object) new String[]{" ", "20"}),
                //when page value = 0 also will be exception
                Arguments.of((Object) new String[]{"0", "5"})
        );
    }

    @DisplayName("Testing method validatePaginationParameters() on negative result " +
            "when method throws exception.")
    @ParameterizedTest
    @MethodSource("badData")
    void validatePaginationParametersThrowExceptionTest(String[] arr) {
        Map<String, String> params = getParams(arr[0], arr[1]);
        Assertions.assertThrows(ValidationException.class,
                () -> paginationValidator.validatePaginationParameters(params));
    }

    private Map<String, String> getParams(String page, String size) {
        Map<String, String> params = new HashMap<>();

        params.put(PAGE, page);
        params.put(SIZE, size);
        return params;
    }
}
