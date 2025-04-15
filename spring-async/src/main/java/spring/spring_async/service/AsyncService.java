package spring.spring_async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.spring_async.domain.Notification;
import spring.spring_async.repository.NotificationRepository;

@Slf4j
@Service
public class AsyncService {

    private String threadName;
    private final NotificationRepository notificationRepository;

    public AsyncService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Async
    @Transactional
    public void process() {
        this.threadName = Thread.currentThread().getName();
        log.info("사용 중인 스레드: {}", threadName);

        Notification notification = new Notification("작성한 글에 댓글이 달렸어요~");
        notificationRepository.save(notification);
    }

    public String getThreadName() {
        return threadName;
    }
}
