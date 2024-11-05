package copy;

public class DeepCopyTest {

    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public Person(Person original) {
            this.name = original.getName();
        }

        public static Person from(Person original) {
            return new Person(original);
        }

        public String getName() {
            return name;
        }
    }
}
