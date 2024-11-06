package store.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void Map_변환_검증() {
        // given
        String input = "[물-10],[콜라-10]";
        Map<String, Integer> expected =new HashMap<>();
        expected.put("물", 10);
        expected.put("콜라", 10);

        // when
        Map<String, Integer> result = Parser.parseToMap(input);

        // then
        assertEquals(result, expected);
    }
}