package com.example.TenantEase.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class Message<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ResponseMessage;
    private HttpStatus status;
    private T data;
}
