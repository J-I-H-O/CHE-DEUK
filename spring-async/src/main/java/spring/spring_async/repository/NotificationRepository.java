package spring.spring_async.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.spring_async.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
