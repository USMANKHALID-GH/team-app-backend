package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.model.Team;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {

    Team  save(TeamDto teamDto, long captainId);

    Team  assignMember(long id,long memberId);

    Team findById(long id);


    List<Team> search(Pageable pageable, String search);

    Team removeTeam(long id, long mId);


}
