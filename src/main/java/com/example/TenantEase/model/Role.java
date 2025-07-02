package com.example.TenantEase.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Data
@Accessors(chain = true)
public class Role {
    /**
     * Role Level:
     * - Used to define role hierarchy across the IAM ecosystem.
     * - Example usage:
     *   - 0 = (e.g., SUPER_ADMIN)
     *   - 1 = Admins (e.g., ORG_ADMIN)
     *   - 2 = Users (e.g., APP_USER, GUEST)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;


    /**
     * Description of the role.
     */
    @Column(nullable = true, length = 255)
    private String description;


    /**
     * Role-Permission Mapping:
     * - A role can have multiple permissions.
     * - Permissions are mapped using a join table `role_permission` to avoid a bidirectional mapping.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    /**
     * Audit fields:
     * - `createdBy` and `updatedBy` store the UUID of the user responsible for creation or update.
     */
    @Column(nullable = false, updatable = false, length = 36)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true, length = 36)
    private String updatedBy;

    @Column(nullable = true)
    private LocalDateTime updatedAt;
}
