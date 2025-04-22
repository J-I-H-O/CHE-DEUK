package spring.spring_async;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.spring_async.domain.Notification;
import spring.spring_async.repository.NotificationRepository;
import spring.spring_async.service.Facade;

@SpringBootTest
public class FacadeTest {

    @Autowired
    private Facade facade;

    @Autowired
    private NotificationRepository notificationRepository;

    @DisplayName("비동기 요청은 별도의 스레드에서 실행된다.")
    @Test
    public void asyncRunsWithAnotherThread() throws InterruptedException {
        facade.process();

        assertThat(facade.getFacadeThreadName()).isEqualTo(facade.getSyncServiceThreadName());
        assertThat(facade.getFacadeThreadName()).isNotEqualTo(facade.getAsyncServiceThreadName());
    }

    @DisplayName("비동기 요청은 별도의 트랜잭션에서 실행된다.")
    @Test
    public void asyncRunsWithAnotherTransaction() throws InterruptedException {
        facade.process();

        assertThat(facade.getFacadeTransactionName()).isEqualTo(facade.getSyncServiceTransactionName());
        assertThat(facade.getFacadeTransactionName()).isNotEqualTo(facade.getAsyncServiceTransactionName());
    }

    /**
     * 비동기 스레드는 원본 스레드와 완전히 별도의 스레드에서 동작하고, 트랜잭션도 마찬가지 이므로 두 트랜잭션은 부모 자식 관계가 아닌 완전히 남남
     * 따라서 비동기 트랜잭션이 롤백되어도 원본 트랜잭션은 전혀 영향을 받지 않음
     */
    @DisplayName("비동기 처리 중 예외가 발생한 경우, outer 트랜잭션은 롤백되지 않는다.")
    @Test
    public void noRollbackOnOuterTransaction() {
        /**
         * SyncService: save 1회
         * AsyncService: save 1회 이후 예외 발생 (롤백)
         */
        Notification syncNotification = new Notification("sync");
        Notification asyncNotification = new Notification("async");

        facade.processButAsyncException(syncNotification, asyncNotification);

        List<Notification> found = notificationRepository.findAll();

        assertThat(found).hasSize(1);
        assertThat(found).extracting(notification -> notification.getBody())
                .containsExactly("sync");
    }
}
