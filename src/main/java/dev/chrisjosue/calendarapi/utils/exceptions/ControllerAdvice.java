package dev.chrisjosue.calendarapi.utils.exceptions;

import dev.chrisjosue.calendarapi.dto.custom.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        return newErrorResponse(List.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException ex) {
        return newErrorResponse(List.of(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = MyBusinessException.class)
    public ResponseEntity<ErrorResponse> myBusinessException(MyBusinessException ex) {
        return newErrorResponse(List.of(ex.getMessage()), ex.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodArgumentException(MyBusinessException ex) {
        return newErrorResponse(List.of(ex.getMessage()), ex.getHttpStatus());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException ex) {
        List<String> exceptionList = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return newErrorResponse(exceptionList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        var getErrors = ex.getBindingResult().getAllErrors();
        List<String> exceptionList = getErrors
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return newErrorResponse(exceptionList, HttpStatus.BAD_REQUEST);
    }

    /*
    * Method to generate Custom Error Response
    */
    private ResponseEntity<ErrorResponse> newErrorResponse(List<String> errors, HttpStatus httpStatus) {
        ErrorResponse newExceptionResponse = ErrorResponse.builder()
                .errors(errors)
                .httpStatus(httpStatus)
                .build();
        return new ResponseEntity<>(newExceptionResponse, httpStatus);
    }
}
