package com.undefined.backendforfrontend.exception;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.undefined.backendforfrontend.dto.ErrorsDto;
import com.undefined.backendforfrontend.dto.ExceptionDto;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class BFFExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("MS Bff - Error: {}", ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsDto> handleFeignNotFoundExceptions(FeignException ex, WebRequest request) {
        log.error("MS Bff - Error: {}", ex.getMessage());
        var errors = new ErrorsDto(new ArrayList<>());
        if (ex.status() > 0) {
            var errorDetails = new ExceptionDto(new Date(), ex.status(), ex.getMessage());
            errors.addException(errorDetails);
            return new ResponseEntity<>(errors, HttpStatus.valueOf(ex.status()));
        } else {
            var errorDetails = new ExceptionDto(new Date(), HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
            errors.addException(errorDetails);
            return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @ExceptionHandler(CreateOrderException.class)
    public ResponseEntity<ErrorsDto> handleCardException(CreateOrderException ex) {
        var errors = new ErrorsDto(new ArrayList<>());
        var errorDetails = new ExceptionDto(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
