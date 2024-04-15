package dogpath.server.dogpath.global.config;

import jakarta.persistence.EntityListeners;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public class JpaConfig {
}
