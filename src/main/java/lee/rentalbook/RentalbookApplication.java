package lee.rentalbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "lee.rentalbook") // 패키지 이름을 수정
public class RentalbookApplication {
//테스트만 해보면 될듯? -> global 쪽 ㄱㅊ으면 로그인도 잘돌아갈듯
	public static void main(String[] args) {
		SpringApplication.run(RentalbookApplication.class, args);
	}

}
