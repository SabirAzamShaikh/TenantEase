package com.example.TenantEase.controller;

import com.example.TenantEase.Repository.ImageDataRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TicketRequestDto;
import com.example.TenantEase.model.ImageData;
import com.example.TenantEase.model.Ticket;
import com.example.TenantEase.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    private final ImageDataRepository imageRepository;

    public TicketController(TicketService ticketService, ImageDataRepository imageRepository) {
        this.ticketService = ticketService;
        this.imageRepository = imageRepository;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Message<Ticket> createTicket(@ModelAttribute TicketRequestDto requestDto) {
        Message<Ticket> message = new Message<>();
        try {
            message = ticketService.createTicket(requestDto);
            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getImageByTicketId")
    public ResponseEntity<Message<List<String>>> getImageById(@RequestParam int ticketid) {
        Message<List<String>> message = new Message<>();
        try {
            message = ticketService.getImageByTicketId(ticketid);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        Optional<ImageData> dbImageData = imageRepository.findByName(fileName);
        if (dbImageData.isPresent()) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbImageData.get().getType())).body(dbImageData.get().getImageData()); // this is the byte[] (actual image)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
    }
}
