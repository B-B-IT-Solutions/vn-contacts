package cz.prm.controllers.advice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ExceptionHandlerAdviceTest {

   private ExceptionHandlerAdvice advice;

   @BeforeEach
   void setUp() {
      advice = new ExceptionHandlerAdvice();
   }

   @Test
   void entityNotFoundException() {
      var result = advice.entityNotFoundException();
      assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
   }
}