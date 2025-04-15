package spring.spring_async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @EnableAsync 를 붙여줘야만 @Async 정상적으로 비동기로 수행됨
 * @EnableAsync 가 존재하는 경우에만 @Async 이 붙은 메서드를 감싸는 프록시를 생성하기 때문
 * 만약 @EnableAsync 가 없는 경우, 그냥 일반 빈이 생성되기 때문에 @Async는 무시됨
 */
@EnableAsync
@SpringBootApplication
public class SpringAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAsyncApplication.class, args);
	}

}
