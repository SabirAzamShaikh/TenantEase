package com.example.TenantEase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;
    private String name;
    private String email;
    private String phoneNumber;
    private String adharNumber;
    private String roomNumber;
    private String depositeAmount;
    private boolean istenant;
}
