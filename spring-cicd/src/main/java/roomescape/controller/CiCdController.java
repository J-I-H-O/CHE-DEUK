package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cicd")
public class CiCdController {

    @GetMapping
    public ResponseEntity<String> returnString() {
        return ResponseEntity.ok("자동 배포 성공~");
    }
}
