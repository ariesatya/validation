package com.arie.validation.exception;

import com.arie.validation.dto.BaseResponse;
import com.arie.validation.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@SuppressWarnings("unused")
public class SomeExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<ErrorResponse<Object>>> handleValidationException(
        MethodArgumentTypeMismatchException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new BaseResponse<>(
                ErrorResponse.builder()
                    .error(exception.getMessage())
                    .path(request.getRequestURI())
                    .build())
                .badRequest("Validation request param failed")
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<ErrorResponse<Object>>> handleValidationException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
            error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new BaseResponse<>(
                ErrorResponse.builder()
                    .error(errors)
                    .path(request.getRequestURI())
                    .build())
                .badRequest("Validation request DTO failed")
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BaseResponse<ErrorResponse<Object>>> handleResponseStatusException(
        ResponseStatusException exception,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(exception.getStatusCode()).body(
            new BaseResponse<>(
                ErrorResponse.builder()
                    .error(exception.getReason())
                    .path(request.getRequestURI())
                    .build())
                .badRequest("Validation request failed")
        );
    }


    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<BaseResponse<ErrorResponse<Object>>> handleConstraintViolationException(
        HandlerMethodValidationException exception,
        HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        exception.getAllErrors().forEach(
            violation -> {
                DefaultMessageSourceResolvable resolvable = (DefaultMessageSourceResolvable) Objects.requireNonNull(violation.getArguments())[0];
                String fieldName = resolvable.getCode();
                String errorMessage = violation.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new BaseResponse<>(
                ErrorResponse.builder()
                    .error(errors)
                    .path(request.getRequestURI())
                    .build())
                .badRequest("Validation failed")
        );
    }
}
