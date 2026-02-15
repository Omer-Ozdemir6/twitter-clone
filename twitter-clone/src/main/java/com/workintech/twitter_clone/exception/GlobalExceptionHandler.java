package com.workintech.twitter_clone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TwitterException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(TwitterException exception){
        TwitterErrorResponse response = new TwitterErrorResponse(exception.getHttpStatus().value(), exception.getMessage(), System.currentTimeMillis(), LocalDateTime.now());
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(MethodArgumentTypeMismatchException exception){
        TwitterErrorResponse response = new TwitterErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TwitterErrorResponse> handleException(Exception exception){
        TwitterErrorResponse response = new TwitterErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
