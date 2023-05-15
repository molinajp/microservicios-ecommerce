package com.undefined.clients.exceptions;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.undefined.clients.dto.ErrorsDto;
import com.undefined.clients.dto.ExceptionDto;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ClientsExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final String ERROR_LOG_MESSAGE = "MS Clients - Error: {}";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(ERROR_LOG_MESSAGE, ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleWebClientResponseExceptionExceptions(WebClientResponseException ex, WebRequest request) {
        log.error(ERROR_LOG_MESSAGE, ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
    
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleWebClientResponseExceptionExceptions(WebClientRequestException ex, WebRequest request) {
        log.error(ERROR_LOG_MESSAGE, ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleCallNotPermittedExceptionExceptions(CallNotPermittedException ex, WebRequest request) {
        log.error(ERROR_LOG_MESSAGE, ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
