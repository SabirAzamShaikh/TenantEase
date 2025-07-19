package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.dto.TicketRequestDto;
import com.example.TenantEase.mapper.TicketMapper;
import com.example.TenantEase.model.Ticket;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class TicketMapperImpl implements TicketMapper {
    @Override
    public Ticket requestDtoToEntity(TicketRequestDto dto) {
        return new Ticket().setTicketTopic(dto.getTicketTopic()).setMessage(dto.getMessage()).setStatus("Pending").setCratedAt(LocalDate.now());
    }
}
