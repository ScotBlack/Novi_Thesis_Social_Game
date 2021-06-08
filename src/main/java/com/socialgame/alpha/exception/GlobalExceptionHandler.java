package com.socialgame.alpha.exception;

import com.socialgame.alpha.dto.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends Throwable{


    @ExceptionHandler(value = { IllegalArgumentException.class })
    public final ResponseEntity<ErrorResponse> handleIllegalArgumentException (Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(ErrorResponse.build(exception, status.toString()));
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public final ResponseEntity<ErrorResponse> handleEntityNotFoundException (Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(ErrorResponse.build(exception, status.toString()));
    }
}
