/**
 * 
 */
package com.invy.exception;

/**
 * @author manikanth
 *
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public final ResponseEntity<Object> handleAllExceptions(CustomException ex) {
    CustomExceptionSchema exceptionResponse =
        new CustomExceptionSchema(
            ex.getMessage(), ex.getDetails(), ex.getHint(), ex.getNextActions(), ex.getSupport());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}