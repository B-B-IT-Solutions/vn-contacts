package cz.prm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "cz.prm.*")
@SpringBootApplication
public class PrmApp {

  public static void main(String[] args) {
    SpringApplication.run(PrmApp.class, args);
  }

}
