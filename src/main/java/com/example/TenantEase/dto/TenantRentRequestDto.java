package com.example.TenantEase.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Accessors(chain = true)
public class TenantRentRequestDto {
    @NotNull(message = "Tenant ID cannot be null")
    private long tenantId;
    @NotNull(message = "You Should have to enter is either paid or not")
    private boolean isPaid;
    @NotNull(message = "Rent Amount cannot be null")
    @Size(min = 1)
    private long rentAmount;
    @NotNull(message = "Month Number cannot be null")
    private int monthNumber;
    @NotNull(message = "Month Number cannot be null")
    private Long year;
}
