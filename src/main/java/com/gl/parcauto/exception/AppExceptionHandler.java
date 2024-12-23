package com.gl.parcauto.exception;

import com.gl.parcauto.dto.response.ErrorDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<ErrorDetails> createErrorResponse(HttpStatus status, Exception e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, status);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException e, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, e, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleConflict(DataIntegrityViolationException e, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, e, request);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> accessDeniedExceptionHandler(AccessDeniedException e, WebRequest webRequest) {
        return createErrorResponse(HttpStatus.UNAUTHORIZED, e, webRequest);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> methodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException e, WebRequest webRequest) {
        return createErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, e, webRequest);
    }
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDetails> blogApiExceptionHandler(AppException e, WebRequest request) {
        return createErrorResponse(e.getStatus(), e, request);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorDetails> authenticationNotFoundExceptionHandler(
            AuthenticationCredentialsNotFoundException e, WebRequest request) {
        return createErrorResponse(HttpStatus.UNAUTHORIZED, e, request);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> GlobalExceptionHandler(Exception e, WebRequest request) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }
}
