package CHE_DEUK.spring_log.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class LogController {

    private Logger log = LoggerFactory.getLogger(LogController.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    record JsonFormatLog(Long traceId, Long userId, String api, String message) {}

    // JSON 형태로 로그를 정형화해서 저장하면 외부 도구와 연계해 로그 검색 시스템을 만들기 용이하다.
    @GetMapping("/method")
    public void log() throws JsonProcessingException {
        log.info("{}", objectMapper.writeValueAsString(new JsonFormatLog(1L, 1L, "/api/log/method", "요청 처리 시작")));
        log.info("{}", objectMapper.writeValueAsString(new JsonFormatLog(1L, 1L, "/api/log/method", "요청 처리 종료")));
    }

    // 예외 생성자에 Throwable을 넣어주면 Cause Exception으로 확인할 수 있다.
    @GetMapping("/chain")
    public void chain() {
        try {
            throwException();
        } catch (IllegalArgumentException e) {
            RuntimeException runtimeException = new RuntimeException("예외로 감싸기", e);
            runtimeException.printStackTrace();
        }
    }

    private void throwException() {
        throw new IllegalArgumentException("원인 예외");
    }
}
