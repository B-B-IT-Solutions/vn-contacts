package cz.prm.config;

import static java.util.Optional.of;

import cz.prm.domain.contact.Contact;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutorFactoryBean;
import cz.prm.repositories.customisations.repositories.RefreshAwareRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditProvider")
@EnableJpaRepositories(basePackages = "cz.prm", repositoryBaseClass = RefreshAwareRepositoryImpl.class, repositoryFactoryBeanClass = PrmQuerydslPredicateExecutorFactoryBean.class)
public class AppConfig {

   @Bean
   public AuditorAware<Contact> auditProvider() {
      var user = new Contact();
      user.setEmail("emai@email.com");
      return () -> of(user);
   }

}