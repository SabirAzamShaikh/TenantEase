package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.UserRequestDto;
import com.example.TenantEase.model.User;

import java.util.List;

public interface UserService {
    public Message<User> createUser(UserRequestDto user);

    public Message<String> userLogin(String email,String password);

    public Message<List<User>> getAllUser(int page, int size);
}
