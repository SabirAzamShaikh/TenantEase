package com.example.TenantEase.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Data
@Accessors(chain = true)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    /**
     * Name of the permission.
     * - Example: "USER_CREATE", "MODULE_READ".
     * - Must always be in uppercase letters.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    /**
     * Code for the permission.
     * - Example: "ORG_BASEAPP_USER_CREATE"
     * - Must follow the pattern ORG_APP_MODULE_OPERATION.
     */
//    @Column(nullable = false, unique = true, length = 150)
//    private String code;

    /**
     * Description of the permission.
     */
    @Column(nullable = true, length = 255)
    private String description;

    /**
     * UUID of the user who created this permission.
     * - Ensures auditability of the permission's creation.
     */
    @Column(nullable = false, updatable = false, length = 36)
    private String createdBy;

    /**
     * Timestamp for when the permission was created.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * UUID of the user who last updated this permission.
     */
    @Column(nullable = true, length = 36)
    private String updatedBy;

    /**
     * Timestamp for the last update to this permission.
     */
    @Column(nullable = true)
    private LocalDateTime updatedAt;

}