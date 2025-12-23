package com.emprendeStore.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ErrorNegocio.class)
    public ResponseEntity<Object> handlerErrorBusiness(ErrorNegocio errorNegocio){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorNegocio.getMessage(), LocalDateTime.now()));
    }
}
