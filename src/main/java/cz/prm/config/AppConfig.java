package cz.prm.config;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static cz.prm.security.SecurityContextUtils.getUser;
import static java.util.Optional.of;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.prm.domain.common.User;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutorFactoryBean;
import cz.prm.repositories.customisations.repositories.RefreshAwareRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditProvider")
@EnableJpaRepositories(basePackages = "cz.prm", repositoryBaseClass = RefreshAwareRepositoryImpl.class, repositoryFactoryBeanClass =
    PrmQuerydslPredicateExecutorFactoryBean.class)
public class AppConfig {

    @Bean
    public AuditorAware<User> auditProvider() {
        return () -> of(getUser());
    }

    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}