package com.zalisoft.teamapi.controller;



import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.mapper.TeamMapper;

import com.zalisoft.teamapi.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    private TeamService service;

    @Autowired
    private TeamMapper mapper;

    @PostMapping("/admin/team/captain/{id}")
    public ResponseEntity<TeamDto> save(@RequestBody TeamDto teamDto, @PathVariable long id){
        return ResponseEntity.ok(mapper.toDto(service.save(teamDto,id)));
    }

    @PostMapping("/admin/team/{id}/member/{mId}")
    public ResponseEntity<TeamDto> assignAMember(@PathVariable long id,@PathVariable long mId){
        return ResponseEntity.ok(mapper.toDto(service.assignAMember(id,mId)));
    }


    @PostMapping("/admin/team/{id}/members")
    public ResponseEntity<TeamDto> assignMembers(@PathVariable long id,@RequestParam("members") List<Long> members){
        return ResponseEntity.ok(mapper.toDto(service.assignMembers(members,id)));
    }


    @PutMapping("/admin/team/{id}/remove-member/{mId}")
    public ResponseEntity<TeamDto> removeTeamMember(@PathVariable long id,@PathVariable long mId){
        return ResponseEntity.ok(mapper.toDto(service.removeTeamMember(id,mId)));
    }

    @GetMapping("/public/team/{id}")
    public ResponseEntity<TeamDto>  findById(@PathVariable  long id){
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @GetMapping("/admin/team")
    public ResponseEntity<Page<TeamDto>>  findAll(Pageable pageable
    , @RequestParam(name = "search", required = false)String search){
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(service.search(pageable,search))));
    }


    @DeleteMapping("/admin/team/{id}")
    public ResponseEntity<BaseResponseDto>  delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Team Basarili bir sekilde silinmistir").build());
    }


}
