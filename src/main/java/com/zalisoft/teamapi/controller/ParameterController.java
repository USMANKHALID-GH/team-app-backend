package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.ParameterDto;
import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.mapper.ParameterMapper;
import com.zalisoft.teamapi.model.Parameter;
import com.zalisoft.teamapi.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParameterController {

    @Autowired
    private ParameterService service;
    @Autowired
    private ParameterMapper mapper;


    @DeleteMapping("/admin/parameter/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Parameter Basarili bir sekilde silinmistir").build());
    }


    @GetMapping("/admin/parameter/key/{key}")
    public ResponseEntity<ParameterDto>  findByKey(@PathVariable  String key){
        return ResponseEntity.ok(mapper.toDto(service.findByKey(key)));
    }


    @PostMapping("/admin/parameter")
    public ResponseEntity<ParameterDto> save(@RequestBody ParameterDto parameterDto){
        return ResponseEntity.ok(mapper.toDto(service.save(parameterDto)));
    }


    @PutMapping("/admin/parameter/{id}")
    public ResponseEntity<BaseResponseDto> update(@PathVariable long id ,@RequestBody ParameterDto parameterDto){
        service.update(id,parameterDto);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Parameter Basarili bir sekilde gunlenmistir").build());
    }


    @GetMapping("/admin/parameter")
    public ResponseEntity<List<ParameterDto>>  findAll(){
        return ResponseEntity.ok(mapper.toDto(service.findAll()));
    }


    @GetMapping("/admin/parameter/{id}")
    public ResponseEntity<ParameterDto>  findById(@PathVariable  long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }
}
