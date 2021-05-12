package com.socialgame.alpha.exception;

import com.socialgame.alpha.dto.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends Throwable{


    @ExceptionHandler({ PlayerNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleException (Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;
        String cause= "PLAYER_NOT_FOUND";

        return ResponseEntity.status(status).headers(headers).body(ErrorResponse.build(exception, cause));
    }


//    @ExceptionHandler({ PlayerNotFoundException.class})
//    public final ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
//        HttpHeaders headers = new HttpHeaders();
//
//        if (ex instanceof PlayerNotFoundException) {
//            HttpStatus status = HttpStatus.NOT_FOUND;
//            PlayerNotFoundException unfe = (PlayerNotFoundException) ex;
//
//            return handleUserNotFoundException(unfe, headers, status, request);
//
//
//
//
//        } else if (ex instanceof ContentNotAllowedException) {
//            HttpStatus status = HttpStatus.BAD_REQUEST;
//            ContentNotAllowedException cnae = (ContentNotAllowedException) ex;
//
//            return handleContentNotAllowedException(cnae, headers, status, request);
//        } else {
//            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//            return handleExceptionInternal(ex, null, headers, status, request);
//        }
//    }

//    private ResponseEntity<ErrorResponse> handleUserNotFoundException(PlayerNotFoundException unfe, HttpHeaders headers, HttpStatus status, WebRequest request) {
//    }
}
