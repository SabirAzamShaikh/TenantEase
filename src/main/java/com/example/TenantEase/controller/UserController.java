package com.example.TenantEase.controller;

import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.UserRequestDto;
import com.example.TenantEase.model.User;
import com.example.TenantEase.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }


    @PostMapping("/register")
    public ResponseEntity<Message<User>> createUSer(@RequestBody UserRequestDto requestDto) {
        Message<User> message = new Message<>();
        try {
            requestDto.setPassword(encoder.encode(requestDto.getPassword()));
            message = userService.createUser(requestDto);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Error Occurs at CreateUser() in UserController");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam("email") String email,@RequestParam("password") String password){
        Message<String> result=userService.userLogin(email,password);
        return "";
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<Message<List<User>>> getAllUser(@RequestParam("page") int page, @RequestParam("size") int size) {
        Message<List<User>> message = new Message<>();
        try {
            message = userService.getAllUser(page, size);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Error Occurs at getAllUser() in UserController");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}
