package functionalInterface;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalInterfaceTest {
    List<Integer> numbers = new ArrayList<>(List.of(10, 5, 22, 18));

    @Test
    @DisplayName("람다식과 함수형 인터페이스를 사용하지 않은 경우")
    public void noLambdaTest() {
        numbers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        numbers.forEach(System.out::println);
    }

    @Test
    @DisplayName("람다식, 메서드 참조, 익명 클래스는 함수형 인터페이스의 구현을 반환한다")
    public void implementTest() {
        List<String> names = List.of("name1, name2, name3");

        // stream API의 forEach는 함수형 인터페이스 Consumer<T>의 구현을 인자로 받는다.

        // 1. 람다식 사용
        System.out.println("=== 람다식 ====");
        names.stream()
                .forEach(name -> System.out.println(name));

        // 2. 메서드 참조 사용
        System.out.println("=== 메서드 참조 ====");
        names.stream().
                forEach(System.out::println);

        // 3. 익명 클래스 사용
        System.out.println("=== 익명 클래스 ====");
        names.stream()
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                });
    }

    @Test
    @DisplayName("람다식과 함수형 인터페이스를 사용한 경우")
    public void lambdaTest() {
        numbers.sort((o1, o2) -> o1 - o2);
        numbers.stream()
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("람다식을 활용하여 함수형 인터페이스를 사용할 수 있다.")
    public void interfaceTest() {
        MyInterface myInterface = (name) -> System.out.println("hello, " + name);
        myInterface.hello("jiho");
    }

    @Test
    @DisplayName("람다식을 메서드 참조로 더 간결하게 표현할 수 있다.")
    public void methodRefTest() {
        // 1. 람다식 사용
        MyInterface lambda = name -> System.out.println(name);
        lambda.hello("hello");
        
        // 2. 메서드 참조 사용
        MyInterface methodRef = System.out::println;
        methodRef.hello("hello");
    }
}
