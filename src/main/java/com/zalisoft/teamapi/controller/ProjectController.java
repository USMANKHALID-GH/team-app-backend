package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.ProjectDto;
import com.zalisoft.teamapi.enums.ProjectStatus;
import com.zalisoft.teamapi.mapper.ProjectMapper;
import com.zalisoft.teamapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private ProjectMapper mapper;


    @GetMapping("/admin/project/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable long id){
        return  ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }


    @PostMapping("/admin/project/user/{userId}")
    public ResponseEntity<BaseResponseDto> save(@PathVariable long userId, @RequestBody ProjectDto projectDto){
        service.save(projectDto,userId);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Project basarili bir sekilde kaydedilmistir").build());
    }


    @PutMapping("/admin/project/{id}/change-status")
    public ResponseEntity<ProjectDto> changeProjectStatus(@PathVariable long id, @RequestParam("status")ProjectStatus status){
        return  ResponseEntity.ok(mapper.toDto(service.updateProjectStatus(id,status)));
    }


    @PutMapping("/admin/project/{id}/extend-date")
    public ResponseEntity<BaseResponseDto> extendProjectDeadline(@PathVariable long id, @RequestParam("date")String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        LocalDate date1 = LocalDate.parse(date, formatter);
        service.extendProjectDeadline(date1,id);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Project son  tarih basarili bir sekilde uzatilmistir").build());
    }


    @GetMapping("/admin/project/un-finished")
    public ResponseEntity<List<ProjectDto>> findAllUnfinishedProject(){
        return  ResponseEntity.ok(mapper.toDto(service. findAllUnfinishedProject()));
    }


    @GetMapping("/admin/project/status/{status}")
    public ResponseEntity<List<ProjectDto>> findProjectByStatus(@PathVariable String status){
        return  ResponseEntity.ok(mapper.toDto(service.findProjectByStatus(status)));
    }


    @GetMapping("/admin/project")
    public ResponseEntity<Page<ProjectDto>> search(@RequestParam(value = "search", required = false) String search, Pageable pageable){
        return  ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.search(pageable,search).getContent())));
    }


    @DeleteMapping("/admin/project/{id}")
    public ResponseEntity<BaseResponseDto>  delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Project Basarili bir sekilde silinmistir").build());
    }



}
