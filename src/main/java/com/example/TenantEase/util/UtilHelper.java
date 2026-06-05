package com.example.TenantEase.util;

import com.example.TenantEase.Repository.ImageDataRepository;
import com.example.TenantEase.model.ImageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Component
@Data
public class UtilHelper {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB per file
    private final ImageDataRepository imageRepository;

    public List<String> imageSaver(List<MultipartFile> file) throws IOException {
        String uuid;
        List<String> imagePath = new ArrayList<>();
        if (file == null || file.isEmpty()) {
           throw  new RuntimeException("Property images are required");
        }

        for (MultipartFile f : file) {
            // Validate file size
            if (f.getSize() > MAX_FILE_SIZE) {
                throw  new RuntimeException("File size exceeds 5MB limit: " + f.getOriginalFilename());
            }

            // Validate file type
            if (!isValidImageType(f.getContentType())) {
                throw  new RuntimeException("Invalid file type. Only image files are allowed: " + f.getOriginalFilename());
            }

            ImageData imageData = new ImageData();
            uuid = UUID.randomUUID().toString() + "_" + f.getOriginalFilename();
            imageData.setName(uuid).setType(f.getContentType()).setImageData(f.getBytes());
            imageRepository.save(imageData);
            imagePath.add(uuid);
        }
        return imagePath;
    }
        /**
         * Validates if the file type is a valid image format
         */
        private boolean isValidImageType (String contentType){
            if (contentType == null) {
                return false;
            }
            return contentType.startsWith("image/");

        }


    public void deleteImages(List<String> imageNames) {

        if (imageNames == null || imageNames.isEmpty()) {
            return;
        }

        imageNames.forEach(imageRepository::deleteByName);
    }
    }
