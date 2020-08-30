package com.example.address.controller;

import com.example.address.exception.AddressNotFoundException;
import com.example.address.exception.GeographicalCoordinateException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> notFoundHandler(AddressNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeographicalCoordinateException.class)
    public ResponseEntity<?> geographicalCoordinateExceptionHandler(GeographicalCoordinateException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<?> nullPointerHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
