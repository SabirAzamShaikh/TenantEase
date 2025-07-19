package com.example.TenantEase.mapper;

import com.example.TenantEase.dto.TicketRequestDto;
import com.example.TenantEase.model.Ticket;

public interface TicketMapper {
public Ticket requestDtoToEntity(TicketRequestDto dto);
}
