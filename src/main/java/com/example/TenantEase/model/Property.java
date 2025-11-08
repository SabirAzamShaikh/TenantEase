package com.example.TenantEase.model;

import com.example.TenantEase.enums.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true  )
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId; // Unique property ID

    @Column(nullable = false)
    private String name; // Property name (e.g., "Sai Residency")

    @Enumerated(EnumType.STRING)
    private PropertyType type; // Type: HOUSE, FLAT, SHOP

    private String address; // Full property address

    private int totalRooms; // Total rooms in this property

    private String ownerName;//owner to who's the Property Belongs

    private  int totalFloors; //Total floors in this property
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Room> rooms; // List of rooms

    @ElementCollection
    private List<String> propertyImagePath;
}

