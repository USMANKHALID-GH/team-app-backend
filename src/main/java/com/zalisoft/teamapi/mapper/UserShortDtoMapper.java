package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.UserShortDto;
import com.zalisoft.teamapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserShortDtoMapper extends EntityMapper<UserShortDto, User> {
}
