package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.PrivilegeDto;
import com.zalisoft.teamapi.dto.RoleDto;
import com.zalisoft.teamapi.mapper.RoleMapper;
import com.zalisoft.teamapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RoleController {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private RoleService service;

    @PostMapping("/role")
    public ResponseEntity<BaseResponseDto>  save(@RequestBody RoleDto roleDto){
        service.save(roleDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Role basirili bir Sekilde Kaydedilmistir").build());
    }


    @GetMapping("/role/{id}")
    public ResponseEntity<RoleDto>  findById(@PathVariable  long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<RoleDto>  update(@PathVariable  long id, @RequestBody RoleDto roleDto){
        return ResponseEntity.ok(mapper.toDto(service.update(id,roleDto)));
    }


    @DeleteMapping("/role/{id}")
    public ResponseEntity<BaseResponseDto>  delete(@PathVariable  long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Role basirili bir sekilde silinmistir").build());}


    @GetMapping("/role")
    public ResponseEntity<List<RoleDto>>  findAll(){
        return ResponseEntity.ok(mapper.toDto(service.findAll()));
    }


    @GetMapping("/role/{id}/privilege")
    public ResponseEntity<List<PrivilegeDto>>  findPrivilegeByRoleId(@PathVariable  long id){
        return ResponseEntity.ok(service.findPrivilegeByRoleId(id));
    }


}
