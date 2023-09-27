package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.PermissionDto;
import com.zalisoft.teamapi.mapper.PermissionMapper;
import com.zalisoft.teamapi.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PermissionController {
    @Autowired
    private PermissionService service;

    @Autowired
    private PermissionMapper mapper;

    @GetMapping("/public/permission/{id}")
    public ResponseEntity<PermissionDto> findById(@PathVariable long id){
        return  ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }


    @PostMapping("/public/permission")
    public ResponseEntity<BaseResponseDto> save(@RequestBody PermissionDto permissionDto){
        service.save(permissionDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Permission basarili bir sekilde kaydedilmistir").build());
    }


    @GetMapping("/admin/permission/captain")
    public ResponseEntity<List<PermissionDto>> findByCaptain(){
        return  ResponseEntity.ok(mapper.toDto(service.findByCaptain()));
    }


    @GetMapping("/admin/permission")
    public ResponseEntity<Page<PermissionDto>> findAll(Pageable pageable){
        return  ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.findAll(pageable).getContent())));
    }


    @PostMapping("/admin/permission/{id}/approve")
    public ResponseEntity<BaseResponseDto> approve(@PathVariable long id){
        service.approve(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Permission basarili bir sekilde kabul edilmis").build());
    }


    @PostMapping("/admin/permission/{id}/reject")
    public ResponseEntity<BaseResponseDto> reject(@PathVariable long id){
        service.reject(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Permission basarili bir sekilde reddedilmis").build());
    }

    @DeleteMapping("/public/permission/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable long id){
        service.deleteByCurrentUser(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Permission basarili bir sekilde silinmistir").build());}




}
