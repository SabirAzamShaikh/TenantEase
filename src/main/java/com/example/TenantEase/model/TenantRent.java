package com.example.TenantEase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
public class TenantRent {
    @Id
    private Long rentId;
    private int monthNumber;
    private Long dueOfThisMonth;
}
