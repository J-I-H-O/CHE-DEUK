package spring.spring_async;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.spring_async.service.Facade;

@SpringBootTest
public class FacadeTest {

    @Autowired
    private Facade facade;

    @DisplayName("비동기 요청은 별도의 스레드에서 실행된다.")
    @Test
    public void asyncRunsWithAnothreThread() throws InterruptedException {
        facade.process();

        assertThat(facade.getSyncServiceThreadName()).isEqualTo(facade.getSyncServiceThreadName());
        assertThat(facade.getSyncServiceThreadName()).isNotEqualTo(facade.getAsyncServiceThreadName());
    }

    // TODO: async에서 예외 발생 시 outer 트랜잭션 롤백 x
    // TODO: asyn와 outer가 다른 트랜잭션에서 실행되는 것도 확인해야 할듯
}
