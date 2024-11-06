package store.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "[콜라-1][사이다-3]", "[콜라-1,[사이다-3]",
        "[콜라-0]", "[콜라-3],[콜라-2]"})
    void 주문_검증_예외_테스트(String input) {
        // when & then
        assertThrows(IllegalArgumentException.class,
            () -> InputValidator.validateOrder(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-1]", "[콜라-1],[사이다-3]", "[콜라-10],[사이다-3],[아이스크림-8]"})
    void 주문_검증_테스트(String input) {
        // when & then
        assertDoesNotThrow(() -> InputValidator.validateOrder(input));
    }
}