package com.example.jwt.config.excetion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> handleApiException(APIException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(e.getMessage());
    }
}
