package ch02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <조건>
 * - 길이가 8글자 이상
 * - 0부터 9 사이의 숫자를 포함
 * - 대문자 포함
 *
 * <암호의 강도>
 * - 3가지 규칙을 만족하면 암호는 강함
 * - 2가지 규칙을 만족하면 암호는 보통
 * - 1가지 이하의 규칙을 만족하면 암호는 약함
 */
public class PasswordStrengthMeterTest {
    private final PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr) {
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr, result);
    }

    @Test
    @DisplayName("모든 조건을 만족 한다면 암호는 강함(STRONG)")
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abc1!@Add", PasswordStrength.STRONG);
    }

    @Test
    @DisplayName("길이 조건 외의 모든 조건을 만족 한다면 암호는 보통(NORMAL)")
    void meetsOtherCriteria_except_for_length_Then_Normal() {
        assertStrength("ab12!@A", PasswordStrength.NORMAL);

        assertStrength("Ab12!c", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("숫자 포함 조건 외의 모든 조건을 만족 한다면 암호는 보통(NORMAL)")
    void meetsOtherCriteria_except_for_number_Then_Normal() {
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("대문자 포함 조건 외의 모든 조건을 만족 한다면 암호는 보통(NORMAL)")
    void meetsOtherCriteria_except_for_uppercase_Then_Normal() {
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("길이가 8글자 이상인 조건만 충족하는 경우 암호는 약함(WEAK)")
    void meetsOnlyLengthCriteria_Then_Weak() {
        assertStrength("abdefghi", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("숫자 포함 조건만 충족하는 경우 암호는 약함(WEAK)")
    void meetsOnlyNumCriteria_Then_Weak() {
        assertStrength("12345", PasswordStrength.WEAK);
    }
    
    @Test
    @DisplayName("대문자 포함 조건만 충족하는 경우 암호는 약함(WEAK)")
    void meetsOnlyUpperCriteria_Then_Weak() {
        assertStrength("ABZEF", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("아무 조건도 충족하지 않는 경우 암호는 약함(WEAK)")
    void meetsNoCriteria_Then_Weak() {
        assertStrength("abc", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("null을 입력할 경우 암호는 INVALID")
    void nullInput_Then_Invalid() {
        assertStrength(null, PasswordStrength.INVALID);
    }

    @Test
    @DisplayName("빈 문자열을 입력할 경우 암호는 INVALID")
    void emptyInput_Then_Invalid() {
        assertStrength("", PasswordStrength.INVALID);
    }
}
