package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class FileSystemController {
    @Autowired
    private FileSystemService  fileSystemService;

    @PostMapping("/image")
    public ResponseEntity<BaseResponseDto> saveImage(@RequestParam("image")MultipartFile file) throws IOException {
        return  ResponseEntity.ok(BaseResponseDto.builder().message(fileSystemService.saveImage(file)).build());
    }


    @DeleteMapping("/image")
    public ResponseEntity<BaseResponseDto> deleteImage(@RequestParam("image") String file) throws IOException {
        fileSystemService.deleteImage(file);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Image Basarili bir sekilde silinmistir").build());
    }




}
