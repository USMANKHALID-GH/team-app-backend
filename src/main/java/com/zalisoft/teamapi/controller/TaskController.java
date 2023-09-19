package com.zalisoft.teamapi.controller;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.TaskDto;
import com.zalisoft.teamapi.enums.TaskStatus;
import com.zalisoft.teamapi.mapper.TaskMapper;
import com.zalisoft.teamapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private TaskMapper mapper;

    @PostMapping("/admin/task")
    public ResponseEntity<TaskDto> save(@RequestBody TaskDto taskDto, @RequestParam("userId")long userId, @RequestParam("teamId") long teamId,@RequestParam("projectId") long projectId){
        return ResponseEntity.ok(mapper.toDto(service.save(taskDto,userId,teamId,projectId)));
    }

    @GetMapping("/public/task/{id}")
    public ResponseEntity<TaskDto>  findById(@PathVariable  long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @GetMapping("/public/task/current-user")
    public ResponseEntity<List<TaskDto>>  findByCurrentUser(){
        return ResponseEntity.ok(mapper.toDto(service.findByCurrentUser()));
    }

    @PutMapping("/admin/task/{id}/status")
    public ResponseEntity<TaskDto>  updateTaskStatusByAdmin(@PathVariable long id,@RequestParam("status") TaskStatus status){
        return ResponseEntity.ok(mapper.toDto(service. updateTaskStatusByAdmin(id ,status)));
    }

    @PutMapping("/public/task/{id}/status")
    public ResponseEntity<TaskDto>   updateTaskStatusByUser(@PathVariable long id, @RequestParam("status") TaskStatus status){
        return ResponseEntity.ok(mapper.toDto(service. updateTaskStatusByUser(id,status)));
    }


    @DeleteMapping("/admin/task/{id}")
    public ResponseEntity<BaseResponseDto>  delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Task Basarili bir sekilde silinmistir").build());
    }



    @GetMapping("/public/task/project/{pId}/status/{status}")
    public ResponseEntity<Page<TaskDto>>   findTaskByStatus(@PathVariable String status, @PathVariable long pId, Pageable pageable){
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.findTaskByStatus(status,pId,pageable).getContent())));
    }


    @PutMapping("/public/task")
    public ResponseEntity<Page<TaskDto>> search(@RequestParam(name = "search",required = false) String search, Pageable pageable){
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.search(search,pageable).getContent())));
    }

}
