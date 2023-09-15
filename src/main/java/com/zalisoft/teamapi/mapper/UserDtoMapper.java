package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.UserDto;
import com.zalisoft.teamapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends EntityMapper<UserDto, User> {
}
