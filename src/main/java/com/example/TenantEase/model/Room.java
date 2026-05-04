package com.example.TenantEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String roomNumber;             // e.g., "Room 101"
    private String description;          // Detailed description
    private String roomType;             // 1RK, 1BHK, 2BHK, etc.
    private double rentAmount;           // Monthly rent
    private double depositAmount;        // Security deposit
    private boolean available;           // Availability (true for free, false for occupied)
    private String furnishingType;       // Furnished/Semi/Unfurnished
    private int occupancyLimit;          // Max number of occupants
    private int floor;                   // Floor or level of room
    private boolean attachedBath;        // True/False for attached bathroom
    private String amenities;            // Free-text or CSV (e.g., "AC,WiFi,Parking")
    @ElementCollection
    private List<String> roomImagePath;// Comma-separated string or separate entity/list
    private LocalDateTime createdAt;     // Created timestamp
    private LocalDateTime updatedAt;     // Updated timestamp
    private String createdBy;             // User who created the room entry
    @Column(nullable = false, length = 20)
    private String status;               // e.g., "available", "maintenance", "occupied"
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Tenant> tenants; // Tenants living in this room
    //POINT TO NOTE ONE TO MANY BECAUSE IN PG IN ONE ROOM THERE CAN BE MULTIPLE TENANT
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property; // The property this room belongs to
}
