package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.RoomRepository;
import com.example.TenantEase.Repository.TenantRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.mapper.TenantMapper;
import com.example.TenantEase.model.Room;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.model.TenantRent;
import com.example.TenantEase.service.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final EmailSenderService emailService;
    private final RoomRepository roomRepository;

    @Override
//    @CacheEvict(value = "tenant", allEntries = true)
    @Transactional
    public Message<TenantResponseDto> addTenant(TenantRequestDto requestDto, Long roomId) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not Found with ID" + roomId));

            Tenant tenant = tenantMapper.requestDtoToEntity(requestDto);

            // Calculate the due date for the current month using rentDueDay from request
            int rentDueDay = requestDto.getRentPaymentDay(); // assuming you've renamed the field
            LocalDate dueDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), rentDueDay);

            // Create the initial rent record
            TenantRent tenantRent = new TenantRent().setYear(LocalDate.now().getYear()).setMonthNumber(LocalDate.now().getMonthValue()).setDueOfThisMonth(requestDto.getTenantRent()).setPaid(false).setDueDate(dueDate);

            // Assign rent list
            tenant.setRents(new ArrayList<>());
            tenant.getRents().add(tenantRent);

            tenant.setRoom(room);
            // Save tenant and automatically save rent (if cascade = ALL)
            Tenant savedTenant = tenantRepository.save(tenant);


            if (!room.isAvailable()) {
                message.setResponseMessage("Room is been Occupied By tenant. Can't add tenant");
                message.setStatus(HttpStatus.BAD_REQUEST);
                return message;
            }
            if (room.getTenants() == null) {
                room.setTenants(new ArrayList<>());
            }

            room.getTenants().add(savedTenant);
            roomRepository.save(room);

            log.info("Saved Tenant Details {}", savedTenant.getTenantId());
            emailService.sendEmailToTenant(savedTenant.getEmail(), "SuccessFully Added As a tenant ", savedTenant);
            message.setResponseMessage("Tenant Added SuccessFully");
            message.setStatus(HttpStatus.CREATED);
            message.setData(tenantMapper.EntityToResponseDto(savedTenant));
            return message;
        } catch (Exception e) {
            log.info("Error Occured while Saving Tenant Details {} {}",e, e.getMessage());
            message.setResponseMessage("Some Error Occurs While Saving the Tenant");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
//    @Cacheable(value = "tenant")
    public Message<List<TenantResponseDto>> getAllTenant() {
        Message<List<TenantResponseDto>> message = new Message<>();
        try {
            List<Tenant> ls = tenantRepository.findAll();
            List<TenantResponseDto> response = ls.stream().map(tenantMapper::EntityToResponseDto).toList();
            message.setData(response);
            message.setResponseMessage("Tenant Response Data Fetch SuccessFully");
            message.setStatus(HttpStatus.OK);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs While Fetching Data in getAllTenant() in TenantServiceImpl");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
    public Message<List<TenantResponseDto>> getAllTenantByOwner(String ownerName) {
        Message<List<TenantResponseDto>> message = new Message<>();
        try {
            List<Tenant> ls = tenantRepository.findAllByCreatedBy(ownerName);
            List<TenantResponseDto> response = ls.stream().map(tenantMapper::EntityToResponseDto).toList();
            message.setData(response);
            message.setResponseMessage("Tenant Response Data Fetch SuccessFully");
            message.setStatus(HttpStatus.OK);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs While Fetching Data in getAllTenant() in TenantServiceImpl");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
    public Message<TenantResponseDto> getTenantById(long id) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            Tenant tenant = tenantRepository.findById(id).orElse(null);
            if (tenant != null) {
                message.setStatus(HttpStatus.OK);
                message.setResponseMessage("Tenant Find SuccessFully");
                message.setData(tenantMapper.EntityToResponseDto(tenant));
                return message;
            }
            message.setStatus(HttpStatus.NOT_FOUND);
            message.setResponseMessage("Tenant Not Find With Id :" + id);
            return message;
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Error Occurs At getTenantById() in TenantServiceImpl");
            return message;
        }
    }

    @Override
    public Message<TenantResponseDto> getTenantByEmail(String email) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            Tenant tenant = tenantRepository.findByEmail(email).orElse(null);
            if (tenant != null) {
                message.setStatus(HttpStatus.OK);
                message.setResponseMessage("Tenant Found Successfully");
                message.setData(tenantMapper.EntityToResponseDto(tenant));
                return message;
            }
            message.setStatus(HttpStatus.NOT_FOUND);
            message.setResponseMessage("Tenant Not Found With Email: " + email);
            return message;
        } catch (Exception e) {
            log.error("Error Occurs At getTenantByEmail() in TenantServiceImpl: {}", e.getMessage());
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Error Occurs While Fetching Tenant By Email");
            return message;
        }
    }
}
