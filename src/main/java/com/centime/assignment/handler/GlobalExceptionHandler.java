package com.centime.assignment.handler;

import com.centime.assignment.exception.NoResultsFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        LOG.error("Invalid POST parameters", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            errors.put(fieldName, error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResultsFound.class)
    public ResponseEntity<Object> handleNoResultsFoundException(NoResultsFound ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(MESSAGE, ex.getLocalizedMessage());
        errorResponse.put(STATUS, ex.getHttpStatus().getReasonPhrase());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        if (violations.isEmpty()) {
            return new ResponseEntity<>("Required parameters are empty.", HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> errorResponse = new HashMap<>();
        List<String> violationList = new ArrayList<>();
        violations.stream().forEach(violation -> violationList.add(violation.getMessage()));
        errorResponse.put(MESSAGE, violationList);
        errorResponse.put(STATUS, HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
