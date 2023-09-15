package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
@Mapper(componentModel = "spring")
public interface UserRegisterMapper extends EntityMapper<UserRegisterDto, User> {


        @Mapping(target = "password", ignore = true)
        UserRegisterDto toDto(User user);

        User toEntity(UserRegisterDto userDto);

        @Mapping(target = "password", ignore = true)
        Set<UserRegisterDto> toDto(Set<User> userList);

        Set<User> toEntity(Set<UserRegisterDto> userDtoList);
    }

