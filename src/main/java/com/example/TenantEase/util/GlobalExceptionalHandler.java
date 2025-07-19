package com.example.TenantEase.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionalHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception e) {
        logger.error("Unhandled Exception: ", e);
        return new ResponseEntity<>("Exception Occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> jwtExceptionHandler(JwtException e) {
        logger.warn("JWT Exception: ", e);
        return new ResponseEntity<>("JWT Exception Occurred: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> jwtTokenExpiredHandler(ExpiredJwtException e) {
        logger.warn("JWT Token Expired: ", e);
        return new ResponseEntity<>("JWT Token Expired: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
