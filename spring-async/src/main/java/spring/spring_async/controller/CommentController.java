package spring.spring_async.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import spring.spring_async.service.CommentFacade;

@Controller
public class CommentController {

    private final CommentFacade commentFacade;

    public CommentController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    @PostMapping("/comments")
    public ResponseEntity<Void> addComment() {
        commentFacade.addComment();
        return ResponseEntity.noContent().build();
    }
}
