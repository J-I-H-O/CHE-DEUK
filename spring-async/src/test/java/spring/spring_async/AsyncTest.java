package spring.spring_async;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.spring_async.service.Facade;

@SpringBootTest
public class AsyncTest {

    @Autowired
    private Facade facade;

    @DisplayName("비동기 요청은 별도의 스레드에서 실행된다.")
    @Test
    public void asyncRunsWithAnothreThread() throws InterruptedException {
        facade.process();

        assertThat(facade.getSyncServiceThreadName()).isEqualTo(facade.getSyncServiceThreadName());
        assertThat(facade.getSyncServiceThreadName()).isNotEqualTo(facade.getAsyncServiceThreadName());
    }
}
