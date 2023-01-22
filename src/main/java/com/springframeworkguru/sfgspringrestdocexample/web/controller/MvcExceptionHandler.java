package com.springframeworkguru.sfgspringrestdocexample.web.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException ex) {
        ArrayList<String> responses = new ArrayList<>(ex.getConstraintViolations().size());
        ex.getConstraintViolations().forEach(v -> responses.add(v.toString()));
        return new ResponseEntity<>(responses, HttpStatus.BAD_REQUEST);
    }
}
