package spring.spring_async.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentFacade {

    private final CommentService commentService;
    private final NotificationService notificationService;

    public CommentFacade(CommentService commentService, NotificationService notificationService) {
        this.commentService = commentService;
        this.notificationService = notificationService;
    }

    @Transactional
    public void addComment() {
        commentService.addComment();
        notificationService.sendNotification();
    }
}
