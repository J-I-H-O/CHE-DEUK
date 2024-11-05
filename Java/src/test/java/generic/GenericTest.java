package generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {
    class MyClass<T> {
        List<T> myList = new ArrayList<>();

        public void add(T element) {
            myList.add(element);
        }

        public void print() {
            myList.forEach(System.out::println);
        }
    }

    class MyClassMultiple<T, U> {
        T value1;
        U value2;

        public MyClassMultiple(T value1, U value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        public void print() {
            System.out.println(value1);
            System.out.println(value2);
        }
    }

    @Test
    @DisplayName("제네릭 기본")
    public void genericTest() {
        // 제네릭 타입 파라미터로 Integer 타입 할당
        MyClass<Integer> myIntegerList = new MyClass<>();
        myIntegerList.add(1);
        myIntegerList.add(2);
        myIntegerList.print();

        // 제네릭 타입 파라미터로 String 타입 할당
        MyClass<String> myStringList = new MyClass<>();
        myStringList.add("hi");
        myStringList.add("hello");
        myStringList.print();
    }

    @Test
    @DisplayName("복수 타입을 제네릭으로 지정할 수 있다")
    public void multipleParamsTest() {
        MyClassMultiple<String, String> myClass1 = new MyClassMultiple<>("hi", "hello");
        myClass1.print();

        MyClassMultiple<Integer, String> myClass2 = new MyClassMultiple<>(5, "hi");
        myClass2.print();
    }

    @Test
    @DisplayName("new 키워드 부분에는 제네릭 타입을 생략할 수 있다.")
    public void typeOmissionTest() {
        MyClass<String> myClass1 = new MyClass<String>();   // OK
        MyClass<String> myClass2 = new MyClass<>();         // OK, 타입 생략 가능
    }

    @Test
    @DisplayName("잘못된 타입을 사용한 경우, 제네릭은 컴파일 타임에 에러를 체크할 수 있다.")
    public void typeCastingTest() {
        class ObjectArray {
            private Object[] array;

            public ObjectArray(Object[] array) {
                this.array = array;
            }

            public Object getElement(int index) {
                return array[index];
            }
        }

        class GenericArray<T> {
            private T[] array;

            public GenericArray(T[] array) {
                this.array = array;
            }

            public T getElement(int index) {
                return array[index];
            }
        }

        // given
        Integer[] arr = {1, 2, 3};

        ObjectArray objectArray = new ObjectArray(arr);
        Integer number1 = (Integer) objectArray.getElement(0);
//        String wrongType1 = (String) objectArray.getElement(1);   // 런타임 에러

        GenericArray<Integer> genericArray = new GenericArray<>(arr);
        Integer number2 = genericArray.getElement(0);
//        String wrongType2 = genericArray.getElement(1);           // 컴파일 에러
    }

    @Test
    @DisplayName("의도와 관계없는 클래스도 대입 가능하다")
    public void problemOfFreedomTest() {
        class Calculator<T> {
            public void add(T a, T b) {}
        }

        Calculator<String> calculator = new Calculator<>();
    }

    @Test
    @DisplayName("제네릭 타입 자체로 타입을 지정하여 객체를 생성할 수 없다.")
    public void instanceCreationErrorTest() {
        class TestClass<T> {
            public void func() {
//                T t = new T();
            }
        }
    }

    @Test
    @DisplayName("static 멤버변수 또는 static 메서드의 타입으로 제네릭 타입 파라미터를 지정할 수 없다.")
    public void staticTypeErrorTest() {
        class TestClass<T> {
//            private static T staticField;
//            public static T staticMethod() {}
        }
    }

    @Test
    @DisplayName("제네릭 인터페이스를 구현하는 클래스는 제네릭 타입 파라미터에 맞추어 구현해야 한다.")
    public void implementGenericInterfaceTest() {
        interface MyInterface<T> {
            public void myMethod(T value);
        }

        // 인터페이스를 구현하는 클래스는 T의 구체적인 타입을 결정할 수도 있다.
        class MyClass implements MyInterface<String> {
            @Override
            public void myMethod(String value) {}
        }

        // 제네릭 인터페이스를 구현하는 클래스도 제네릭 클래스일 수 있다.
        class MyClass2<T> implements MyInterface<T> {
            @Override
            public void myMethod(T value) {}
        }
    }

    @Test
    @DisplayName("함수형 인터페이스에서 제네릭의 사용")
    public void genericFunctionalInterface() {
        @FunctionalInterface
        interface MyInterface<T> {
            public abstract T add(T a, T b);
        }

        // 제네릭을 통해 리턴 타입 및 파라미터 a와 b의 타입 결정
        MyInterface<Integer> myInterface = (a, b) -> a + b;
        Integer result = myInterface.add(1, 2);
        System.out.println(result);
    }
}
