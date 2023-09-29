package com.zalisoft.teamapi.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.mapper.TeamMapper;

import com.zalisoft.teamapi.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.zalisoft.teamapi.util.General.convertToJson;


@RestController
@RequestMapping("/api")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService service;

    @Autowired
    private TeamMapper mapper;

    @PostMapping("/admin/team/captain/{id}")
    public ResponseEntity<TeamDto> save(@RequestParam("teamDto") String team,
                                        @PathVariable long id,
                                        @RequestParam("image")MultipartFile file) throws IOException {
        TeamDto teamDto=convertToJson(team,TeamDto.class);

        return ResponseEntity.ok(mapper.toDto(service.save(teamDto,id,file)));
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
