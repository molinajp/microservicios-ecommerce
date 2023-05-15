package com.undefined.products.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.undefined.products.dto.ErrorsDTO;
import com.undefined.products.dto.ExceptionsDTO;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ProductsExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String LOG_ERR = "MS Products - Error: {}";

    // 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorsDTO> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(LOG_ERR, ex.getMessage());
        var errors = new ErrorsDTO(new ArrayList<>());
        var errorDetails = new ExceptionsDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorsDTO> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        log.error(LOG_ERR, ex.getMessage());
        var errors = new ErrorsDTO(new ArrayList<>());
        var errorDetails = new ExceptionsDTO(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsDTO> handleCallNotPermittedExceptionExceptions(CallNotPermittedException ex,
            WebRequest request) {
        log.error(LOG_ERR, ex.getMessage());
        var errors = new ErrorsDTO(new ArrayList<>());
        var errorDetails = new ExceptionsDTO(new Date(), HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
