package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.model.Team;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamService {

    Team  save(TeamDto teamDto, long captainId, MultipartFile file) throws IOException;

    Team assignAMember(long id, long memberId);

    Team assignMembers(List<Long> members, long id);

    Team findById(long id);


    List<Team> search(Pageable pageable, String search);

    Team removeTeamMember(long id, long mId);


    void delete(long id);

    List<User> findMembersByCaptainTc(String tc);



}
