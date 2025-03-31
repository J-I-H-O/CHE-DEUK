package log.slf4j_mdc.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {

    public void process() {
        try {
            processA();
            processB();
        } catch (InterruptedException e) {
        }
    }

    public void processA() throws InterruptedException {
        log.info("traceId - " + MDC.get("traceId") + ": processA 처리 시작");
        Thread.sleep(1000);
        log.info("traceId - " + MDC.get("traceId") + ": processA 처리 완료");
    }

    public void processB() throws InterruptedException {
        log.info("traceId - " + MDC.get("traceId") + ": processB 처리 시작");
        Thread.sleep(1000);
        log.info("traceId - " + MDC.get("traceId") + ": processB 처리 완료");
    }
}
