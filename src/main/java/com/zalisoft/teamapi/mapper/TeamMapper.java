package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.model.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDto, Team> {
}
