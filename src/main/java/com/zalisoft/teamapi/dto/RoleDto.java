package com.zalisoft.teamapi.dto;

import lombok.Data;

import java.util.Set;
@Data
public class RoleDto extends BaseDto{
    private Long id;
    private String name;
    private Set<PrivilegeDto> privileges;
}
