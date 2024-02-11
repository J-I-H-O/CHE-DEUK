package functionalInterface;

@FunctionalInterface
public interface MyInterface {
    // 추상 메서드는 단 하나만 가질 수 있다.
    public abstract void hello(String name);

    // default 메서드와 static 메서드는 몇개든 가질 수 있다.
    default void myDefault() {
        System.out.println("default method");
    }

    default void myDefault2() {
        System.out.println("default method2");
    }

    static void myStatic() {
        System.out.println("static method");
    }
}
