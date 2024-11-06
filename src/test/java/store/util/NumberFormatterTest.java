package store.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NumberFormatterTest {

    @Test
    void 금액_포맷팅_테스트() {
        //given
        int price = 4000000;

        //when
        String formattedNumber = NumberFormatter.formatNumber(price);

        //then
        assertEquals(formattedNumber, "4,000,000");
    }

}