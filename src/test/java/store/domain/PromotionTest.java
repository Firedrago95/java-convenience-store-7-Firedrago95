package store.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    void 프로모션_기간_확인_테스트() {
        // given
        LocalDateTime now = DateTimes.now();
        Promotion promotion1
            = new Promotion("MD추천", 1, 1, now.minusDays(3), now.plusDays(1));
        Promotion promotion2
            = new Promotion("MD추천", 1, 1, now.minusDays(3), now.minusDays(1));

        // when
        boolean result1 = promotion1.isPromotionTime(now);
        boolean result2 = promotion2.isPromotionTime(now);

        // then
        assertTrue(result1);
        assertFalse(result2);
    }

}