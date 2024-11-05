package copy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShallowCopyTest {

    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String newName) {
            this.name = newName;
        }
    }

    @Test
    @DisplayName("shallow copy는 한 객체가 변경되면 다른 객체도 변경된다.")
    void shallowCopy() {
        Person original = new Person("original");
        Person shallowCopied = original;    // 얕은 복사

        shallowCopied.setName("copied");

        System.out.println(original.getName());
        System.out.println(shallowCopied.getName());
    }
}
