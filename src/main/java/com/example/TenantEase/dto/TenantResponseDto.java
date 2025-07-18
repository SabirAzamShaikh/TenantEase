package com.example.TenantEase.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TenantResponseDto implements Serializable {
    private Long tenantId;
    private String name;
    private String email;
    private String phoneNumber;
    private String adharNumber;
    private String roomNumber;
    private String depositeAmount;
    private boolean istenant;
}
