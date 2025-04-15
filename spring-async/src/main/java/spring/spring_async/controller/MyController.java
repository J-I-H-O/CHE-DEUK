package spring.spring_async.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import spring.spring_async.service.Facade;

@Controller
public class MyController {

    private final Facade notificationFacade;

    public MyController(Facade facade) {
        this.notificationFacade = facade;
    }

    @PostMapping("/notifications")
    public ResponseEntity<Void> sendNotification() {
        notificationFacade.process();
        return ResponseEntity.noContent().build();
    }
}
