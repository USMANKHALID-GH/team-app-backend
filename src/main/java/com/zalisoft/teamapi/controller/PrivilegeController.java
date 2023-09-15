package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.PrivilegeDto;
import com.zalisoft.teamapi.mapper.PrivilegeMapper;
import com.zalisoft.teamapi.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PrivilegeController {

    @Autowired
    private PrivilegeMapper mapper;

    @Autowired
    private PrivilegeService service;

    @PostMapping("/privilege")
    public ResponseEntity<BaseResponseDto> save(@RequestBody PrivilegeDto privilegeDto){
        service.save(privilegeDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Privilege basarili bir sekilde kaydedilmistir").build());
    }


    @GetMapping("/privilege/{id}")
    public  ResponseEntity<PrivilegeDto>  findById(@PathVariable long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/privilege/{id}")
    public  ResponseEntity<PrivilegeDto>  update(@PathVariable long id,@RequestBody PrivilegeDto privilegeDto){
        return ResponseEntity.ok(mapper.toDto(service.update(id, privilegeDto)));
    }


    @DeleteMapping("/privilege/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Privilege basarili bir sekilde silinmistir").build());}


    @GetMapping("/privilege/name/{name}")
    public ResponseEntity<PrivilegeDto> findByName(@PathVariable String name){
        return ResponseEntity.ok(mapper.toDto(service.findByName(name)));
    }








}
