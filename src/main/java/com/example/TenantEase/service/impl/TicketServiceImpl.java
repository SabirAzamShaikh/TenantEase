package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.ImageDataRepository;
import com.example.TenantEase.Repository.TenantRepository;
import com.example.TenantEase.Repository.TicketRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TicketRequestDto;
import com.example.TenantEase.mapper.TicketMapper;
import com.example.TenantEase.model.ImageData;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.model.Ticket;
import com.example.TenantEase.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {
    private final TenantRepository tenantRepository;
    private final TicketRepository ticketRepository;
    private final ImageDataRepository imageRepository;
    private final TicketMapper ticketMapper;

    public TicketServiceImpl(TenantRepository tenantRepository, TicketRepository ticketRepository, ImageDataRepository imageRepository, TicketMapper ticketMapper) {
        this.tenantRepository = tenantRepository;
        this.ticketRepository = ticketRepository;
        this.imageRepository = imageRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public Message<Ticket> createTicket(TicketRequestDto requestDto) throws IOException {
        Message<Ticket> message = new Message<>();
        log.info("Data Arrived in TicketServiceImpl CreateTicket() {}", requestDto);
        try {
            List<String> imagePath = new ArrayList<>();
            String uuid = "";
            Tenant tenant = tenantRepository.findById(requestDto.getTenantId()).orElse(null);
            if (tenant == null) {
                message.setStatus(HttpStatus.NOT_FOUND);
                message.setResponseMessage("Tenant Not Found With ID: " + requestDto.getTenantId());
                return message;
            }
            List<MultipartFile> file = requestDto.getImage();
            for (MultipartFile f : file) {
                ImageData imageData = new ImageData();
                uuid = UUID.randomUUID().toString() + "_" + f.getName();
                imageData.setName(uuid).setType(f.getContentType()).setImageData(f.getBytes());
                imageRepository.save(imageData);
                imagePath.add(uuid);
            }
            Ticket ticket = ticketMapper.requestDtoToEntity(requestDto);
            ticket.setImagePath(imagePath);
            message.setData(ticketRepository.save(ticket));
            message.setResponseMessage("Ticket Created SuccessFully");
            message.setStatus(HttpStatus.CREATED);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs at createTicket() in ticketServiceImpl While saving Ticket ");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
    public Message<List<String>> getImageByTicketId(int ticketId) {
        Message<List<String>> message = new Message<>();

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            message.setStatus(HttpStatus.NOT_FOUND);
            message.setResponseMessage("Ticket not found with ID: " + ticketId);
            return message;
        }

        // Optional: construct full URLs for each image
        List<String> imageUrls = ticket.getImagePath();
    message.setData(imageUrls);
    message.setStatus(HttpStatus.OK);
    message.setResponseMessage("Image Fetched Succesfully");
    return message;
    }
}
