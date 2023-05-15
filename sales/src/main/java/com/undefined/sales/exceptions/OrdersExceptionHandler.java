package com.undefined.sales.exceptions;

import java.util.Date;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.undefined.sales.dto.ErrorDetails;
import com.undefined.sales.dto.Errors;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class OrdersExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_LOG_MESSAGE = "MS Orders - Error: {}";

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Errors> handleOrderNotFoundException(OrderException exception, WebRequest webRequest) {
        log.error(ERROR_LOG_MESSAGE, exception.getMessage());
        ErrorDetails errorDetails = new ErrorDetails();
        final Errors errors = new Errors();
        errorDetails.setCode(exception.getStatus());
        errorDetails.setDetails(exception.getDetail());
        errorDetails.setTimestamp(exception.getTimestamp());
        errors.setErrors(Set.of(errorDetails));

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(ERROR_LOG_MESSAGE, ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails();
        final Errors errors = new Errors();
        errorDetails.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.setDetails(ex.getMessage());
        errorDetails.setTimestamp(new Date());
        errors.setErrors(Set.of(errorDetails));
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ERROR_LOG_MESSAGE, ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails();
        final Errors errors = new Errors();
        errorDetails.setCode(status.value());
        errorDetails.setDetails(ex.getMessage());
        errorDetails.setTimestamp(new Date());
        errors.setErrors(Set.of(errorDetails));

        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler
    public ResponseEntity<Errors> handleCallNotPermittedExceptionExceptions(CallNotPermittedException ex,
            WebRequest request) {
        log.error(ERROR_LOG_MESSAGE, ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails();
        final Errors errors = new Errors();
        errorDetails.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        errorDetails.setDetails(ex.getMessage());
        errorDetails.setTimestamp(new Date());
        errors.setErrors(Set.of(errorDetails));
        return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
