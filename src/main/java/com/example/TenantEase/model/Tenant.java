package com.example.TenantEase.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
    private int totalStayMonth;
    @OneToMany(cascade = CascadeType.ALL)
    private List<TenantRent> rents;
    private LocalDate createTime;
    private Long rentAmount;
}
