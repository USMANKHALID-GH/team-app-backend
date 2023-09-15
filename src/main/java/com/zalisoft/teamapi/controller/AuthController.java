package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.AuthRequest;
import com.zalisoft.teamapi.dto.AuthToken;
import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.mapper.UserRegisterMapper;
import com.zalisoft.teamapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public  ResponseEntity<UserRegisterDto>  register(@RequestBody UserRegisterDto userRegisterDto){
        return ResponseEntity.ok((mapper.toDto(service.register(userRegisterDto))));
    }



}
