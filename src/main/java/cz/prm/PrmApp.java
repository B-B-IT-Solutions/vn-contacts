package cz.prm;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@NoArgsConstructor(access = PRIVATE)
public class PrmApp {

  public static void main(String[] args) {
    SpringApplication.run(PrmApp.class, args);
  }

}
