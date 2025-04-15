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

    @Transactional
    public void syncToAsync() {
        log.info("outer sync method thread : {}", Thread.currentThread().getName());
        processAsync();
    }

    @Async
    @Transactional
    public void asyncToSync() {
        log.info("outer async method thread : {}", Thread.currentThread().getName());
        processSync();
    }

    @Async
    @Transactional
    public void asyncToAsync() {
        log.info("outer async method thread : {}", Thread.currentThread().getName());
        processAsync();
    }

    @Transactional
    public void processSync() {
        log.info("inner sync method thread : {}", Thread.currentThread().getName());
    }

    @Async
    @Transactional
    public void processAsync() {
        this.threadName = Thread.currentThread().getName();
        log.info("inner async method thread : {}", threadName);

        Notification notification = new Notification("작성한 글에 댓글이 달렸어요~");
        notificationRepository.save(notification);
    }

    public String getThreadName() {
        return threadName;
    }
}
