package com.zalisoft.teamapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileSystemService {

    String saveImage(MultipartFile file) throws IOException;

    void deleteImage(String imageUrl);
}
