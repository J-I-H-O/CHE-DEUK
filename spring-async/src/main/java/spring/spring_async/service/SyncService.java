package spring.spring_async.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import spring.spring_async.domain.Notification;
import spring.spring_async.repository.NotificationRepository;

@Getter
@Slf4j
@Service
public class SyncService {

    private String threadName;
    private String transactionName;
    private final NotificationRepository notificationRepository;

    public SyncService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void process() {
        this.threadName = Thread.currentThread().getName();
        this.transactionName = TransactionSynchronizationManager.getCurrentTransactionName();

        log.info("inner sync method thread : {}", this.threadName);
        log.info("inner sync method transaction : {}", this.transactionName);

        Notification notification = new Notification("작성한 글에 댓글이 달렸어요~");
        notificationRepository.save(notification);
    }
}
