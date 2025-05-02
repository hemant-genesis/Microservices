package com.order.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, request.getDescription(false));
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<Object> handlePaymentFailed(PaymentFailedException ex, WebRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getDescription(false));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        return buildResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
    }

    private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus status, String path) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("message", message);
        errorBody.put("path", path);
        return new ResponseEntity<>(errorBody, status);
    }
}

