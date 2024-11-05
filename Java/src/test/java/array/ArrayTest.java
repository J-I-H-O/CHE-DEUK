package array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayTest {

    @Test
    @DisplayName("정수 배열을 가리키는 참조변수 출력 시 '타입@주소'의 형식 출력")
    void printIntArray() {
        int[] iArr = {100, 95, 80, 70, 60};
        System.out.println(iArr);
    }

    @Test
    @DisplayName("문자 배열을 가리키는 참조변수 출력 시 문자열 출력")
    void printCharArray() {
        char[] chArr = {'a', 'b', 'c', 'd'};
        System.out.println(chArr);  // println이 문자 배열에 대해서만 이렇게 동작하도록 작성됨
    }

    @Test
    void strTest() {
        String str = "java";
        str = str + "8";
        System.out.println(str);
    }

    @Test
    void strCompareTest() {
        String str1 = "abc";
        String str2 = new String("abc");

        assertFalse(str1 == str2);
        assertTrue(str1.equals(str2));
    }
}
