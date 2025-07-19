package com.example.TenantEase.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    private String ticketTopic;
    private String message;
    private LocalDate cratedAt;
    private String status;
    private LocalDate resolvedDate;
    @ElementCollection
    private List<String> imagePath;
}
