package log.slf4j_mdc.controller;

import log.slf4j_mdc.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/doSomething")
    public void doSomething() {
        log.info("traceId - " + MDC.get("traceId") + ": 요청 처리 시작");
        memberService.process();
        log.info("traceId - " + MDC.get("traceId") + ": 요청 처리 완료");
    }
}
