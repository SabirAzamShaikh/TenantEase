package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.RoleRepository;
import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.UserRequestDto;
import com.example.TenantEase.mapper.UserMapper;
import com.example.TenantEase.model.Role;
import com.example.TenantEase.model.User;
import com.example.TenantEase.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager manager;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, AuthenticationManager manager, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.manager = manager;
        this.encoder = encoder;
    }

    @Override
    public Message<User> createUser(UserRequestDto user) {
        Message<User> message = new Message<>();
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                message.setResponseMessage("Email Already Existed! Try With Another Email ");
                message.setStatus(HttpStatus.CONFLICT);
                return message;
            }
            List<Role> roles = roleRepository.findAllById(user.getRoleIds());
            if (roles.size() != user.getRoleIds().size()) {
                message.setResponseMessage("Role ");
                message.setStatus(HttpStatus.CONFLICT);
                return message;
            }
            User requestDtoToEntity = userMapper.RequestDtoToEntity(user, roles);
            User savedUser = userRepository.save(requestDtoToEntity);
            message.setStatus(HttpStatus.CREATED);
            message.setData(savedUser);
            message.setResponseMessage("User Created SuccessFully and Login Credentials are send on Your Email");
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs While Creating User in createUser() in UserServiceImpl");
            log.error("Error Occurs While Creating User in createUser() in UserServiceImpl {}", e.getMessage());
            message.setStatus(HttpStatus.CONFLICT);
            return message;
        }
    }

    @Override
    public Message<String> userLogin(String email, String password) {
        Message<String> message = new Message<>();
        try {
            // Check if user exists
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found With Email " + email));
            log.debug("Running Till here T1");
            // Authenticate with raw password
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            log.debug("Running Till here T2");

            // Check authentication status
            if (authentication.isAuthenticated()) {
                message.setStatus(HttpStatus.OK);
                message.setResponseMessage("User is Authenticated");
                message.setData("AUTHENTICATED");
                return message;
            } else {
                message.setStatus(HttpStatus.UNAUTHORIZED);
                message.setResponseMessage("Authentication failed");
                message.setData("UNAUTHENTICATED");
                return message;
            }
        } catch (UsernameNotFoundException e) {
            message.setStatus(HttpStatus.UNAUTHORIZED);
            message.setResponseMessage("User not found: " + e.getMessage());
            return message;
        } catch (BadCredentialsException e) {
            message.setStatus(HttpStatus.UNAUTHORIZED);
            message.setResponseMessage("Invalid credentials");
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error occurred while logging in: " + e.getMessage());
            log.error("Error occurs while user login: {}", e.getMessage(), e);
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
    public Message<List<User>> getAllUser(int page, int size) {
        Message<List<User>> message = new Message<>();
        try {
            if (page < 0 && size < 0) {
                message.setResponseMessage("Page And Size should be greater the zero");
                message.setStatus(HttpStatus.BAD_REQUEST);
                return message;
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<User> pagedUsers = userRepository.findAll(pageable);
            message.setStatus(HttpStatus.OK);
            message.setResponseMessage("Data Fetched SuccessFully");
            message.setData(pagedUsers.getContent());
            return message;
        } catch (Exception e) {
            log.error("Error occurs while fetching users in getAllUser(): {}", e.getMessage(), e); // use logger for this
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }
}
