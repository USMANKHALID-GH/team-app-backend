package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Team;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.TeamRepository;
import com.zalisoft.teamapi.service.TeamService;
import com.zalisoft.teamapi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserService userService;

    @Override
    public Team save(TeamDto teamDto, long captainId) {
        if(StringUtils.isEmpty(teamDto.getName())){
            throw new BusinessException("");
        }

        User captain =userService.findById(captainId);

        Team team= new Team();
        team.setName(teamDto.getName());
        team.setCaptain(captain);
        team.setImage(teamDto.getName());
        return teamRepository.save(team);

    }

    @Override
    public Team assignMember(long id,long memberId) {
        User captain=userService.findCurrentUser();
        Team team= findById(id);
        if(captain.getId().equals(team.getCaptain().getId())){
            team.setMembers(List.of(userService.findById(memberId)));
            teamRepository.save(team);
            return team;
        }
        else
            throw  new BusinessException("");

    }


    @Override
    public Team findById(long id) {
        return teamRepository.findById(id)
                .orElseThrow(()->new BusinessException(""));
    }


    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }


    @Override
    public Team removeTeam(long id, long memberId) {
        User captain=userService.findCurrentUser();
        Team team=findById(id);
        if(captain.getId().equals(team.getCaptain().getId())){
            team.getMembers().remove(userService.findById(memberId));
            return team;
        }
       else
           throw  new BusinessException("");
    }

}
