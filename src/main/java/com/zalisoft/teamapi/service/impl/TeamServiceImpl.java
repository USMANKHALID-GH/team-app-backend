package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Team;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.TeamRepository;
import com.zalisoft.teamapi.service.TeamService;
import com.zalisoft.teamapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserService userService;

    @Override
    public Team save(TeamDto teamDto, long captainId) {
        if(StringUtils.isEmpty(teamDto.getName())){
            throw new BusinessException(ResponseMessageEnum.BACK_TEAM_MSG_001);
        }

        User captain =userService.findById(captainId);

        Team team= new Team();
        team.setName(teamDto.getName());
        team.setCaptain(captain);
        team.setImage(teamDto.getName());
        return teamRepository.save(team);

    }


    @Override
    public Team assignAMember(long id, long memberId) {
        User captain=userService.findCurrentUser();
        Team team= findById(id);
        if(captain.getId().equals(team.getCaptain().getId())){
            team.getMembers().add(userService.findById(id));
             return teamRepository.save(team);
        }
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_TEAM_MSG_002);}

    @Override
    public Team assignMembers(List<Long> members,long id) {
        Team team=findById(id);
        User captain=userService.findCurrentUser();
        List<User> memberList=userService.findAllByListOfId(members);
        if(captain.getId().equals(team.getCaptain().getId())){
            team.getMembers().addAll(memberList);
            return teamRepository.save(team);
        }
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_TEAM_MSG_002);

    }


    @Override
    public Team findById(long id) {
        return teamRepository.findById(id)
                .orElseThrow(()->new BusinessException(ResponseMessageEnum.BACK_TEAM_MSG_003));
    }


    @Override
    public List<Team> search(Pageable pageable, String search) {
        return teamRepository.search(search,pageable).getContent();
    }


    @Override
    public Team removeTeamMember(long id, long memberId) {
        User captain=userService.findCurrentUser();
        Team team=findById(id);
        if(captain.getId().equals(team.getCaptain().getId())){
            team.getMembers().remove(userService.findById(memberId));
            teamRepository.save(team);
            return team;
        }
       else
           throw  new BusinessException(ResponseMessageEnum.BACK_TEAM_MSG_004);}


    @Override
    public void delete(long id) {
        teamRepository.deleteById(findById(id).getId());}

    @Override
    public List<User> findMembersByCaptainTc(String tc) {
        return teamRepository.findMembersByCaptainTc(tc)
                .orElseThrow(()->new BusinessException(ResponseMessageEnum.BACK_TEAM_MSG_003));
    }


}
