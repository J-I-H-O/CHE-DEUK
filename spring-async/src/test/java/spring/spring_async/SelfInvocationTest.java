package spring.spring_async;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.spring_async.service.AsyncService;

@SpringBootTest
public class SelfInvocationTest {

    @Autowired
    private AsyncService asyncService;

    /**
     * self-invocation(메서드 내부에서 같은 클래스 내의 다른 메서드를 호출)하는 경우, outer 메서드가 실행중이던 스레드에서 그대로 inner 메서드를 실행함.
     * this 키워드를 사용한 것이기 때문에 프록시가 아닌 실제 객체의 메서드를 호출한 것과 같고, 따라서 AOP가 동작하지 않음
     *
     * 1. outer가 async, inner 도 async인 경우 -> outer와 inner가 같은 비동기 작업으로 묶여버림. 즉, 비동기 스레드에서 동기처럼 동작. (outer 메서드가 실행중이던 비동기 스레드에서 inner 메서드 이어서 실행)
     * 2. outer가 sync, inner가 async인 경우 -> inner 메서드가 비동기로 동작하지 않음 (outer 메서드가 실행중이던 동기 스레드에서 inner 메서드 이어서 실행)
     */
    @DisplayName("Async outer에서 self-invocation을 통해 Async inner를 실행하는 경우, 같은 비동기 스레드에서 실행된다.")
    @Test
    public void asyncToAsyncSelfInvocation() {
        // 비동기로 동작하긴 하지만, 같은 비동기 스레드에서 동기적으로 처리되는 하나의 작업으로 묶여버린 꼴
        // outer async method thread : task-1
        // inner async method thread : task-1
        asyncService.asyncToAsync();
    }

    @DisplayName("Sync outer에서 self-invocation을 통해 Async inner를 실행하는 경우, inner는 비동기 처리되지 않는다.")
    @Test
    public void syncToAsyncSelfInvocation() {
        // 아예 비동기로 처리되지 않아버림
        // outer sync method thread : Test worker
        // inner async method thread : Test worker
        asyncService.syncToAsync();
    }

    @DisplayName("Async outer에서 self-invocation을 통해 Sync inner를 실행하는 경우, 같은 비동기 스레드에서 실행된다.")
    @Test
    public void asyncToSyncSelfInvocation() {
        // 비동기로 동작하며, 같은 비동기 스레드에서 동기적으로 처리되는 하나의 작업으로 묶임
        asyncService.asyncToSync();
    }
}
