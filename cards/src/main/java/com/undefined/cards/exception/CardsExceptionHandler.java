package com.undefined.cards.exception;

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
import org.springframework.ws.client.WebServiceIOException;

import com.undefined.cards.dto.ErrorsDto;
import com.undefined.cards.dto.ExceptionDto;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CardsExceptionHandler extends ResponseEntityExceptionHandler {

    // Define a constant en vez de un literal, final(no se puede modificar)
    // static(siempre el mismo valor en todas las clases)
    private static final String LOG_ERR = "MS Cards - Error: {}";

    // 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(LOG_ERR, ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 503
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleWebServiceIOException(WebServiceIOException ex, WebRequest request) {
        log.error(LOG_ERR, ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        log.error(LOG_ERR, ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

}