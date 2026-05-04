package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;

import java.util.List;
import java.util.Map;

public interface ImageService {

    /**
     * Fetches images by their stored path/name keys.
     * Returns a map of imagePath -> Base64 encoded image string.
     */
    Message<Map<String, String>> getImagesByPaths(List<String> imagePaths);
}