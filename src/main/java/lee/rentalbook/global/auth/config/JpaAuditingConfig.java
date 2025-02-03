package lee.rentalbook.global.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @file JpaAuditingConfig.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    //엔티티에 대한 시간관리 자동화를 위한 설정
}
