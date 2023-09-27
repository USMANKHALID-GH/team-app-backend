package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.CaptainReportDto;
import com.zalisoft.teamapi.dto.PermissionDto;
import com.zalisoft.teamapi.mapper.CaptainReportMapper;
import com.zalisoft.teamapi.service.CaptainReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class CaptainReportController {
    @Autowired
    private CaptainReportService service;

    @Autowired
    private CaptainReportMapper mapper;



    @GetMapping("/captain-report/{id}")
    public ResponseEntity<CaptainReportDto> findById(@PathVariable long id){
        return  ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }


    @PostMapping("/captain-report")
    public ResponseEntity<BaseResponseDto> save(@RequestBody CaptainReportDto captainReportDto){
        service.save(captainReportDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Captain report basarili bir sekilde kaydedilmistir").build());
    }



    @DeleteMapping("/captain-report/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Captain report basarili bir sekilde silinmistir").build());}



    @PutMapping("/captain-report/{id}")
    public ResponseEntity<BaseResponseDto> update(@RequestBody CaptainReportDto captainReportDto ,@PathVariable long id){
        service.update(id,captainReportDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Captain report basarili bir sekilde gunlenmistir").build());}



    @GetMapping("/captain-report")
    public ResponseEntity<Page<CaptainReportDto>> search(@RequestParam(value = "search", required = false) String search, Pageable pageable){
        return  ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.search(pageable,search).getContent())));
    }

}
