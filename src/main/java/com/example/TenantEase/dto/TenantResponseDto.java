package com.example.TenantEase.dto;

import com.example.TenantEase.model.TenantRent;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class TenantResponseDto implements Serializable {
    private Long tenantId;
    private String name;
    private String email;
    private String phoneNumber;
    private String adharNumber;
    private String roomNumber;
    private Long depositeAmount;
    private boolean istenant;
    private Long rentAmount;
    private int rentPaymentDay;
    private LocalDate createdDate;
    private List<TenantRent> rents;
}
