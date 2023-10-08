package ch02;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {
        // null, 빈 문자열 검증
        if (s == null || s.isEmpty()) {
            return PasswordStrength.INVALID;
        }

        int metCounts = getMetCriteriaCounts(s);

        if(metCounts <= 1) return PasswordStrength.WEAK;        // 조건을 1개 만족한 경우 암호는 약함 (WEAK)
        if(metCounts == 2) return PasswordStrength.NORMAL;      // 조건을 1개 만족한 경우 암호는 보통 (NORMAL)

        return PasswordStrength.STRONG;
    }

    private int getMetCriteriaCounts(String s) {
        int metCounts = 0;

        if (s.length() >= 8) metCounts++;                       // 길이 조건 만족 여부
        if (meetsContainingNumberCriteria(s)) metCounts++;      // 숫자 포함 조건 만족 여부
        if (meetsContaingUppercaseCriteria(s)) metCounts++;     // 대문자 포함 조건 만족 여부

        return metCounts;
    }

    private static boolean meetsContaingUppercaseCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if ('0' <= ch && ch <= '9') {
                return true;
            }
        }
        return false;
    }
}