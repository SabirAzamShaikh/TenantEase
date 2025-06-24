package com.example.TenantEase.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class Message<T> {
private String ResponseMessage;
private HttpStatus status;
private T data;
}
