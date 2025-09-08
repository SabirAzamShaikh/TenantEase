package com.example.TenantEase.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

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
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String adharNumber;
    private String roomNumber;
    private String depositeAmount;
    private boolean isTenant;
    private int totalStayMonth;
    @OneToMany(cascade = CascadeType.ALL)
    private List<TenantRent> rents;
    @Column(nullable = false)
    private LocalDate createTime;
    private String createdBy;
    @Column(nullable = false)
    private Long rentAmount;
}
