package com.example.address.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeographicalCoordinateException extends RuntimeException {
    private HttpStatus httpStatus;
    public GeographicalCoordinateException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
