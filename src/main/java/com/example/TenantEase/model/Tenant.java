package com.example.TenantEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String adharNumber;
    private String roomNumber;
    private Long depositeAmount;
    private boolean isTenant;
    private int totalStayMonth;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id") // 👈 this is key
    private List<TenantRent> rents;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
    private Room room; // Room assigned to tenant
    @Column(nullable = false)
    private LocalDate createTime;
    private String createdBy;
    @Column(nullable = false)
    private Long rentAmount;
    private int rentPaymentDay;
}
