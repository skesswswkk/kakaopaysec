package com.example.stockranking.common.exceptionhandler;

import com.example.stockranking.common.response.ApiErrorResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ApiErrorResponse> handleCallNotPermittedException() {
        return ApiErrorResponse.toResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, "server error.");
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException exception) {
        return ApiErrorResponse.toResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ApiErrorResponse> handleIllegalException(Exception exception) {
        return ApiErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiErrorResponse> handleBindException(BindException exception) {
        FieldError fieldError = exception.getFieldErrors().get(0);
        String message = fieldError.getField() + " " + fieldError.getDefaultMessage();

        return ApiErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, message);
    }
}
