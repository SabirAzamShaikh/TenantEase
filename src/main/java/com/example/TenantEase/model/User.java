package com.example.TenantEase.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class User {
@Id
    private int userId;
    private String fullName;
    private String password;
    private String userType;
    private String email;
    private Timestamp createTime;
    private int roleId;
    private String contactNumber;

}
