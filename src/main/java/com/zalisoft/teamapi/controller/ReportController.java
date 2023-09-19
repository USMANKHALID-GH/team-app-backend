package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.ReportDto;
import com.zalisoft.teamapi.dto.TaskDto;
import com.zalisoft.teamapi.dto.UserDto;
import com.zalisoft.teamapi.mapper.ReportMapper;
import com.zalisoft.teamapi.mapper.UserDtoMapper;
import com.zalisoft.teamapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService service;

    @Autowired
    private ReportMapper mapper;

    @Autowired
    private UserDtoMapper userDtoMapper;


    @GetMapping("/admin/task")
    public ResponseEntity<Page<ReportDto>> search(Pageable pageable
            , @RequestParam(name = "search", required = false)String search){
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.search(pageable,search))));
    }



    @GetMapping("/public/task/current")
    public ResponseEntity<List<ReportDto>> findReportByCurrentUser(){
        return ResponseEntity.ok((mapper.toDto(service.findReportByCurrentUser())));
    }


    @GetMapping("/admin/report/{id}")
    public ResponseEntity<ReportDto>  findById(@PathVariable long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }


    @GetMapping("/admin/report/day/{day}")
    public ResponseEntity<List<ReportDto>>  findReportByCaptains(@PathVariable int day){
        return ResponseEntity.ok(mapper.toDto(service.findReportByCaptains(day)));
    }


    @GetMapping("/admin/report/in-complete-task")
    public ResponseEntity<List<ReportDto>> findIncompleteReport(){
        return ResponseEntity.ok((mapper.toDto(service.findIncompleteReport())));
    }


    @GetMapping("/admin/report/captain/{tc}/unsent")
    public ResponseEntity<List<UserDto>> findUserUnsentReport(@PathVariable String tc){
        return ResponseEntity.ok((userDtoMapper.toDto(service.findUserUnsentReport(tc))));
    }


    @PostMapping("/admin/report")
    public ResponseEntity<ReportDto> save(@RequestBody ReportDto reportDto,
                                          @RequestParam("userId")long userId,
                                          @RequestParam("teamId") long teamId,
                                          @RequestParam(value = "projectId",required = false) List<Long> projectId){
        return ResponseEntity.ok(mapper.toDto(service.save(reportDto, projectId,userId,teamId)));
    }


    @DeleteMapping("/public/report/{id}")
    public ResponseEntity<BaseResponseDto>  delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Report Basarili bir sekilde silinmistir").build());
    }



}
