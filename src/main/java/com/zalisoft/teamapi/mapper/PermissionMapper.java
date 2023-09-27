package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.PermissionDto;
import com.zalisoft.teamapi.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends EntityMapper<PermissionDto, Permission> {
}
