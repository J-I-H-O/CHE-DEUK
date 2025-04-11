package spring.spring_async.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.spring_async.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
