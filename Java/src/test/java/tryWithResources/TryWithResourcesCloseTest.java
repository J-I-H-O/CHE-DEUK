package tryWithResources;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TryWithResourcesCloseTest {

    class MyResource implements AutoCloseable {
        @Override
        public void close() throws RuntimeException {
            System.out.println("closed");
            throw new RuntimeException();
        }
    }

    @Test
    @DisplayName("try-catch-finally를 사용하는 경우, 자원이 close되지 않을 수 있다.")
    void tryCatchFinallyCloseFail() {
        MyResource myResource1 = null;
        MyResource myResource2 = null;

        try {
            myResource1 = new MyResource();
            myResource2 = new MyResource();
        } finally {
            myResource1.close();    // closed 출력, RuntimeException 발생
            myResource2.close();    // 실행되지 않음
        }
    }

    @Test
    @DisplayName("try-catch-finally를 사용하는 경우, 각 close() 호출에 대해 예외 처리")
    void tryCatchFinallyCloseSuccess() {
        MyResource myResource1 = null;
        MyResource myResource2 = null;

        try {
            myResource1 = new MyResource();
            myResource2 = new MyResource();
        } finally {
            try {
                if (myResource1 != null) {
                    myResource1.close();
                }
            } catch (Exception e) {
                // 예외 처리
            }

            try {
                if (myResource2 != null) {
                    myResource2.close();
                }
            } catch (Exception e) {
                // 예외 처리
            }
        }
    }

    @Test
    @DisplayName("try-with-resources를 사용하는 경우, 모든 자원이 close된다.")
    void tryWithResourcesClose() {
        try (MyResource myResource1 = new MyResource();
             MyResource myResource2 = new MyResource()) {
        }
    }
}
