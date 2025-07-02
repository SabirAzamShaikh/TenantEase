package com.example.TenantEase.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
public class User {
    @Id
    private int userId;
    private String fullName;
    private String password;
    private String userType;
    private String email;
    private String contactNumber;
    @Column(nullable = false, updatable = false, length = 36)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true, length = 36)
    private String updatedBy;

    @Column(nullable = true)
    private LocalDateTime updatedAt;
    @Column(nullable = false, length = 20)
    private String status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
