package com.pedro.budget.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(Exception exception, WebRequest webRequest) {
        System.out.println(">>> " + exception.getClass().getName());
        exception.printStackTrace();

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Bad Request",
                exception.getMessage(), webRequest.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrors> handleValidationError(MethodArgumentNotValidException exception,
            WebRequest webRequest) {
        ApiErrors apiErrors = new ApiErrors(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), "Campo(s) inválidos",
                webRequest.getDescription(false).replace("uri=", ""),
                exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                        .toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrors> handleConstraintViolation(
            ConstraintViolationException exception,
            WebRequest webRequest) {
        ApiErrors apiErrors = new ApiErrors(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), "Campo(s) inválidos",
                webRequest.getDescription(false).replace("uri=", ""),
                exception.getConstraintViolations()
                        .stream()
                        .map(v -> Map.of("field", v.getPropertyPath().toString(), "message", v.getMessage()))
                        .toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrors);
    }

}
