package cz.prm.controllers.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

   @ExceptionHandler(value = EntityNotFoundException.class)
   public ResponseEntity<Object> entityNotFoundException() {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
}
