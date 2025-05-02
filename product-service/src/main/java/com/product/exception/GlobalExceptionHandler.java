package com.product.exception;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.product.dto.responses.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDbError(DatabaseException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMsg), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
