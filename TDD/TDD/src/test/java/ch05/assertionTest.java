package ch05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class assertionTest {

    @Test
    @DisplayName("이전 검증이 실패하면 이후의 코드는 실행되지 않음")
    @Disabled
    void assertFails_Then_NextAssertionsNotDone() {
        assertEquals(3, 5 / 2);
        assertEquals(4, 2 * 2);
        assertEquals(6, 11 / 2);
    }

    @Test
    @DisplayName("assertAll() 메서드를 사용하는 경우, 이전 검증이 실패해도 일단 모든 검증 수행")
    @Disabled
    void assertAll_Then_EveryAssertionsDone() {
        assertAll(
                () -> assertEquals(3, 5 / 2),
                () -> assertEquals(4, 2 * 2),
                () -> assertEquals(6, 11 / 2)
        );
    }
}
