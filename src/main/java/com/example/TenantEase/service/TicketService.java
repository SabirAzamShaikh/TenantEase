package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TicketRequestDto;
import com.example.TenantEase.model.Ticket;

import java.io.IOException;
import java.util.List;

public interface TicketService {
    Message<Ticket> createTicket(TicketRequestDto requestDto) throws IOException;

    Message<List<String>> getImageByTicketId(int ticketid);
}
