package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.CautionDto;
import com.zalisoft.teamapi.dto.ParameterDto;
import com.zalisoft.teamapi.dto.UserDto;
import com.zalisoft.teamapi.mapper.CautionMapper;
import com.zalisoft.teamapi.mapper.UserDtoMapper;
import com.zalisoft.teamapi.service.CautionService;
import com.zalisoft.teamapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CautionController {

    @Autowired
    private CautionService service;

    @Autowired
    private CautionMapper mapper;

    @Autowired
    private UserDtoMapper userMapper;


    @GetMapping("/admin/caution")
    public ResponseEntity<List<CautionDto>> findAll(){
        return ResponseEntity.ok(mapper.toDto(service.findAll()));
    }


      @PostMapping("/admin/caution")
    public ResponseEntity<BaseResponseDto> save(@RequestParam("captainTc") String tc){
        service.save(tc);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Caution basarili bir sekilde gonderilmistir").build());}


    @GetMapping("/public/caution")
    public ResponseEntity<List<CautionDto>> findByCurrentUser(){
        return ResponseEntity.ok(mapper.toDto(service.findByCurrentUser()));}


    @DeleteMapping("/admin/caution/{id}")
    public ResponseEntity<BaseResponseDto> deleteByCaptain(@PathVariable long id){
        service.deleteByCaptain(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Caution Basarili bir sekilde silinmistir").build());}



    @GetMapping("/admin/caution/{id}")
    public ResponseEntity<CautionDto>  findById(@PathVariable  long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));}


    @GetMapping("/admin/caution/user_to_be_punished")
    public ResponseEntity<List<UserDto>> findUsersWhoExceedLimit(){
        return ResponseEntity.ok(userMapper.toDto(service.countUsersWithMoreThanTheGivenOccurrences()));}
}
