package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.ImageDataRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.model.ImageData;
import com.example.TenantEase.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageDataRepository imageDataRepository;

    @Override
    public Message<Map<String, String>> getImagesByPaths(List<String> imagePaths) {
        Message<Map<String, String>> message = new Message<>();

        try {
            if (imagePaths == null || imagePaths.isEmpty()) {
                message.setResponseMessage("Image paths list cannot be empty");
                message.setStatus(HttpStatus.BAD_REQUEST);
                return message;
            }

            Map<String, String> imageMap = new LinkedHashMap<>();
            List<String> notFoundPaths = new ArrayList<>();

            for (String path : imagePaths) {
                Optional<ImageData> imageDataOpt = imageDataRepository.findByName(path);

                if (imageDataOpt.isPresent()) {
                    ImageData imageData = imageDataOpt.get();

                    // Encode image bytes to Base64 with data URI prefix for direct frontend use
                    // e.g. "data:image/png;base64,iVBORw0KGgo..."
                    String base64Image = "data:" + imageData.getType() + ";base64,"
                            + Base64.getEncoder().encodeToString(imageData.getImageData());

                    imageMap.put(path, base64Image);
                } else {
                    log.warn("Image not found for path: {}", path);
                    notFoundPaths.add(path);
                }
            }

            if (imageMap.isEmpty()) {
                message.setResponseMessage("No images found for the provided paths");
                message.setStatus(HttpStatus.NOT_FOUND);
                return message;
            }

            // Partial success — some found, some missing
            if (!notFoundPaths.isEmpty()) {
                message.setResponseMessage("Some images were not found: " + notFoundPaths);
            } else {
                message.setResponseMessage("Images fetched successfully");
            }

            message.setStatus(HttpStatus.OK);
            message.setData(imageMap);

        } catch (Exception e) {
            log.error("Error fetching images: ", e);
            message.setResponseMessage("Failed to fetch images: " + e.getMessage());
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return message;
    }
}