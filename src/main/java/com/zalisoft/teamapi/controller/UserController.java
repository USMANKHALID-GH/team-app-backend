package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.mapper.UserRegisterMapper;
import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.service.RoleService;
import com.zalisoft.teamapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.zalisoft.teamapi.util.General.convertToJson;

@RestController
@Slf4j
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
    public ResponseEntity<BaseResponseDto> unAssignRoleToUser(@PathVariable long id,@PathVariable long roleId){
        service.unAssignRoleToUser(id,roleId);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Role basarili bir sekilde cikarilmistir").build());
    }


    @GetMapping("/admin/user/role/{roleId}")
    public ResponseEntity<List<UserRegisterDto>> findUserByRoles(@PathVariable long roleId){
       Role role=roleService.findById(roleId);
       return  ResponseEntity.ok(mapper.toDto(service.findUserByRoles(role)));
    }


    @DeleteMapping("/public/user/{id}")
    public ResponseEntity<BaseResponseDto>  delete(@PathVariable long id){
        service.deleteByAdmin(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("User Basarili bir sekilde silinmistir").build());
    }


    @PutMapping("/public/user/password")
    public ResponseEntity<BaseResponseDto> updatePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                                          @RequestParam(name = "newPassword") String newPassword){
        service.changePassword(newPassword,oldPassword);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("User password basarili bir sekilde gunlenmistir").build());
    }



    @PutMapping("/public/user")
    public ResponseEntity<BaseResponseDto> updateByCurrentUser(@RequestParam("userDto") String userDto,
                                                  @RequestParam(value = "image",required = false)MultipartFile file) throws IOException {
        service.updateByCurrentUser(convertToJson(userDto,UserRegisterDto.class),file);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("User basarili bir sekilde gunlenmistir").build());
    }


    @PutMapping("/public/user/{id}")
    public ResponseEntity<BaseResponseDto> updateByAdmin(
                                                  @RequestParam("userDto") String userDto,
                                                  @PathVariable long id,
                                                  @RequestParam(value = "image",required = false)MultipartFile file)
                                                  throws IOException {
        service.updateByAdmin(id,convertToJson(userDto,UserRegisterDto.class),file);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("User basarili bir sekilde gunlenmistir").build());
    }






}
