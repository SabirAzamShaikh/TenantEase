package com.example.TenantEase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Entity
@Data
@Accessors(chain = true)
public class Tenant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;
    private String name;
    private String email;
    private String phoneNumber;
    private String adharNumber;
    private String roomNumber;
    private String depositeAmount;
    private boolean isTenant;
}
