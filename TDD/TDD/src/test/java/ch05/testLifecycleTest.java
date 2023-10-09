package ch05;

import org.junit.jupiter.api.*;

public class testLifecycleTest {
    public testLifecycleTest() {
        System.out.println("Constructor");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("@AfterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("@BeforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("@AfterEach");
    }

    @Test
    void test1() {
        System.out.println("@Test 1");
    }

    @Test
    void test2() {
        System.out.println("@Test 2");
    }
}
