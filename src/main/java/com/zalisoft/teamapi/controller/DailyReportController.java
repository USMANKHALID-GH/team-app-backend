package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.DailyReportDto;
import com.zalisoft.teamapi.dto.UserDto;
import com.zalisoft.teamapi.dto.UserShortDto;
import com.zalisoft.teamapi.mapper.DailyReportMapper;
import com.zalisoft.teamapi.mapper.UserDtoMapper;
import com.zalisoft.teamapi.mapper.UserShortDtoMapper;
import com.zalisoft.teamapi.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DailyReportController {

    @Autowired
    private DailyReportService service;

    @Autowired
    private DailyReportMapper mapper;

    @Autowired
    private UserShortDtoMapper userDtoMapper;



    @GetMapping("/admin/task")
    public ResponseEntity<Page<DailyReportDto>> search(Pageable pageable
            , @RequestParam(name = "search", required = false)String search){
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.search(pageable,search))));
    }



    @GetMapping("/public/task/current")
    public ResponseEntity<List<DailyReportDto>> findReportByCurrentUser(){
        return ResponseEntity.ok((mapper.toDto(service.findReportByCurrentUser())));
    }


    @GetMapping("/admin/report/{id}")
    public ResponseEntity<DailyReportDto>  findById(@PathVariable long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }


    @GetMapping("/admin/report/day/{day}")
    public ResponseEntity<List<DailyReportDto>>  findReportByCaptains(@PathVariable int day){
        return ResponseEntity.ok(mapper.toDto(service.findReportByCaptains(day)));
    }


    @GetMapping("/admin/report/in-complete-task")
    public ResponseEntity<List<DailyReportDto>> findIncompleteReport(){
        return ResponseEntity.ok((mapper.toDto(service.findIncompleteReport())));
    }


    @GetMapping("/admin/report/captain/{tc}/unsent")
    public ResponseEntity<List<UserShortDto>> findUsersUnsentReport(@PathVariable String tc){
        return ResponseEntity.ok((userDtoMapper.toDto(service.findUsersUnsentReport(tc))));
    }


    @PostMapping("/public/report")
    public ResponseEntity<DailyReportDto> save(@RequestBody DailyReportDto dailyReportDto){
        return ResponseEntity.ok(mapper.toDto(service.save(dailyReportDto)));
    }


    @DeleteMapping("/public/report/{id}")
    public ResponseEntity<BaseResponseDto>  delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Report Basarili bir sekilde silinmistir").build());
    }


    @GetMapping("/admin/report/day-off-users")
    public ResponseEntity<List<UserShortDto>> findUsersOnDayOff(){
        return ResponseEntity.ok((userDtoMapper.toDto(service.findUsersOnDayOff())));
    }


    @PostMapping("/public/report/day-off")
    public ResponseEntity<BaseResponseDto>  setDayOff(){
        service. setDayOff();
        return ResponseEntity.ok(BaseResponseDto.builder().message("Day-Off basarili bir sekilde alinmis").build());
    }

    @PutMapping("/public/report/{id}")
    public ResponseEntity<BaseResponseDto>  updateByCurrentUser(@RequestBody DailyReportDto dailyReportDto, @PathVariable long id){
        service.update(dailyReportDto,id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Day-Off basarili bir sekilde alinmis").build());
    }


}
