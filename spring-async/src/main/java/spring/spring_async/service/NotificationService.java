package spring.spring_async.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.spring_async.domain.Notification;
import spring.spring_async.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void sendNotification() {
        Notification notification = new Notification("작성한 글에 댓글이 달렸어요~");
        notificationRepository.save(notification);
    }
}
