package com.example.TenantEase.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Accessors(chain = true)
public class TicketRequestDto {
    private String ticketTopic;
    private String message;
    private long tenantId;
    private List<MultipartFile> image;
}
