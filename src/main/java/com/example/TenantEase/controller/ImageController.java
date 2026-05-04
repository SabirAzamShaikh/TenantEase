package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/getImages")
    public Message<Map<String, String>> getImages(@RequestBody List<String> imagePaths) {
        log.info("In ImageController - fetching images for paths: {}", imagePaths);
        try {
            return imageService.getImagesByPaths(imagePaths);
        } catch (Exception e) {
            log.error("Error in getImages: ", e);
            Message<Map<String, String>> errorMessage = new Message<>();
            errorMessage.setResponseMessage("Failed to fetch images: " + e.getMessage());
            errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return errorMessage;
        }
    }
}