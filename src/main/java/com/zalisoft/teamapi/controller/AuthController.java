package com.zalisoft.teamapi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalisoft.teamapi.dto.AuthRequest;
import com.zalisoft.teamapi.dto.AuthToken;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.mapper.UserRegisterMapper;
import com.zalisoft.teamapi.service.FileSystemService;
import com.zalisoft.teamapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.zalisoft.teamapi.util.General.convertToJson;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService service;
    @Autowired
    private UserRegisterMapper mapper;


    @PostMapping("/login")
    public ResponseEntity<AuthToken>  login(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(service.login(authRequest));

    }

    @PostMapping("/register")
    public  ResponseEntity<UserRegisterDto>  register(@RequestParam(value = "userDto") String userDto
                                                      ,@RequestParam(name = "image",required = false) MultipartFile file) throws IOException {
        UserRegisterDto userRegisterDto=convertToJson(userDto, UserRegisterDto.class);

        return ResponseEntity.ok((mapper.toDto(service.register(userRegisterDto,file))));
    }





}
