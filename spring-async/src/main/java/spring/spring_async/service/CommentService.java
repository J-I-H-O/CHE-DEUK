package spring.spring_async.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.spring_async.domain.Comment;
import spring.spring_async.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void addComment() {
        Comment comment = new Comment(1L, "멋있네요~");
        commentRepository.save(comment);
    }
}
