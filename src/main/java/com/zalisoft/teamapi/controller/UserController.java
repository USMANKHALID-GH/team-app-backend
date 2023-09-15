package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.mapper.UserRegisterMapper;
import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.service.RoleService;
import com.zalisoft.teamapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRegisterMapper mapper;

    @Autowired
    private RoleService roleService;


    @GetMapping("/public/user/{id}")
    public ResponseEntity<UserRegisterDto> findById(@PathVariable long id){
        return  ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }


    @GetMapping("/public/user/user-email/{email}")
    public ResponseEntity<UserRegisterDto> findByEmail(@PathVariable String email){
        return  ResponseEntity.ok(mapper.toDto(service.findOneByEmail(email)));
    }


    @PutMapping("/admin/user/{id}/assign-role/{roleId}")
    public ResponseEntity<BaseResponseDto> assignRoleToUser(@PathVariable long id,@PathVariable long roleId){
        service.assignRoleToUser(id,roleId);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Role basarili bir sekilde atanmistir").build());
    }


    @PutMapping("/admin/user/{id}/unassign-role/{roleId}")
    public ResponseEntity<BaseResponseDto> unassignRoleToUser(@PathVariable long id,@PathVariable long roleId){
        service.unassignRoleToUser(id,roleId);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Role basarili bir sekilde cikarilmistir").build());
    }


    @GetMapping("/admin/user/role/{roleId}")
    public ResponseEntity<List<UserRegisterDto>> findUserByRoles(@PathVariable long roleId){
       Role role=roleService.findById(roleId);
       return  ResponseEntity.ok(mapper.toDto(service.findUserByRoles(role)));
    }








}
