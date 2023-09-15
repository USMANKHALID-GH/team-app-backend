package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.ProjectDto;
import com.zalisoft.teamapi.dto.UserRegisterDto;
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
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private ProjectMapper mapper;

    @GetMapping("/super-admin/project/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable long id){
        return  ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PostMapping("/super-admin/project/user/{userId}")
    public ResponseEntity<BaseResponseDto> save(@PathVariable long userId, @RequestBody ProjectDto projectDto){
        service.save(projectDto,userId);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Project basarili bir sekilde kaydedilmistir").build());
    }

    @PutMapping("/admin/project/{id}/change-status")
    public ResponseEntity<ProjectDto> changeProjectStatus(@PathVariable long id, @RequestParam("status")ProjectStatus status){
        return  ResponseEntity.ok(mapper.toDto(service.updateProjectStatus(id,status)));
    }
    @PutMapping("/super-admin/project/{id}/extend-date")
    public ResponseEntity<BaseResponseDto> extendProjectDeadline(@PathVariable long id, @RequestParam("status")LocalDate date){
        service.extendProjectDeadline(date,id);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Project son  tarih basarili bir sekilde uzatilmistir").build());
    }

    @GetMapping("/super-admin/project/un-finished")
    public ResponseEntity<List<ProjectDto>> findUnfinishedProject(){
        return  ResponseEntity.ok(mapper.toDto(service.findUnfinishedProject()));
    }

    @GetMapping("/super-admin/project/status/{status}")
    public ResponseEntity<List<ProjectDto>> findProjectByStatus(@PathVariable ProjectStatus status){
        return  ResponseEntity.ok(mapper.toDto(service.findProjectByStatus(status)));
    }

    @GetMapping("/super-admin/{project}")
    public ResponseEntity<Page<ProjectDto>> search(@RequestParam("search") String search, Pageable pageable){
        return  ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.search(pageable,search).getContent())));
    }




}
