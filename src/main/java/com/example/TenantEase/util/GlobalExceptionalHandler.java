//package com.example.TenantEase.util;
//
//import com.example.TenantEase.dto.Message;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionalHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> genericExceptionhandler(Exception e) {
//        return new ResponseEntity<>("Exception Occurs " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//
//}
