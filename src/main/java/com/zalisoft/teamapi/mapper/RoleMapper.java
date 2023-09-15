package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.RoleDto;
import com.zalisoft.teamapi.model.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDto, Role> {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    Set<RoleDto> toDto(Set<Role> roleList);

    Set<Role> toEntity(Set<RoleDto> roleDtoList);
}
