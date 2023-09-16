package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.PrivilegeDto;
import com.zalisoft.teamapi.dto.RoleDto;
import com.zalisoft.teamapi.model.Privilege;
import com.zalisoft.teamapi.model.Role;

import java.util.List;

public interface RoleService {

    void save(RoleDto roleDto);

    Role findById(long id);

    Role update(long id, RoleDto roleDto);

//    Role addPrivilege(long id, long pId);

    void delete(long id);

    List<Role> findAll();

    List<PrivilegeDto>  findPrivilegeByRoleId(long id);

    Role findByName(String name);


}
