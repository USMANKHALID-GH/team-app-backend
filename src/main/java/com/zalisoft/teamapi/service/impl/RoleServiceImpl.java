package com.zalisoft.teamapi.service.impl;


import com.zalisoft.teamapi.dto.PrivilegeDto;
import com.zalisoft.teamapi.dto.RoleDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.mapper.PrivilegeMapper;
import com.zalisoft.teamapi.model.Privilege;
import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.RoleRepository;
import com.zalisoft.teamapi.service.PrivilegeService;
import com.zalisoft.teamapi.service.RoleService;
import com.zalisoft.teamapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private UserService userService;




    @Override
    public void save(RoleDto roleDto) {
        if(StringUtils.isEmpty(roleDto.getName())){
            throw  new BusinessException(ResponseMessageEnum. BACK_ROLE_MSG_002);
        }
        isUnique(roleDto.getName());
        Role role= new Role();
        role.setName(roleDto.getName());
        Set<Privilege> privileges=privilegeMapper.toEntity(roleDto.getPrivileges());
        role.setPrivileges(privileges);
        roleRepository.save(role);

    }


    @Override
    public Role findById(long id) {
        return roleRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum.BACK_ROLE_MSG_001));}


    @Override
    public Role update(long id, RoleDto roleDto) {
        Role role=findById(id);
        if(StringUtils.isNotEmpty(roleDto.getName())){
            isUnique(roleDto.getName());
            role.setName(roleDto.getName());
        }
        if(!CollectionUtils.isEmpty(roleDto.getPrivileges())){
            role.setPrivileges(privilegeMapper.toEntity(roleDto.getPrivileges()));
        }
        return roleRepository.save(role);
    }

    @Override
    public Role addPrivilege(long id, long pId) {
        Role role=findById(id);
        log.info("Role : {}",role.toString());

        Privilege privilege=privilegeService.findById(pId);
        log.info("privilege: {}", privilege.toString());
        Set<Privilege> privilegeSet=role.getPrivileges();
        privilegeSet.add(privilege);

        role.setPrivileges(privilegeSet);


        roleRepository.save(role);
        return role;

    }

    @Override
    public Role removePrivilege(long id, long pId) {
        Role role=findById(id);
        Privilege privilege=privilegeService.findById(pId);
        Set<Privilege> privileges=role.getPrivileges();
        privileges.remove(privilege);
         return roleRepository.save(role);
    }


    @Override
    public void delete(long id) {
        Role role=findById(id);
        List<User> users= userService.findUserByRoles(role);
        if(!CollectionUtils.isEmpty(users)){
            throw  new BusinessException(ResponseMessageEnum.BACK_ROLE_MSG_003);
        }
        roleRepository.delete(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<PrivilegeDto> findPrivilegeByRoleId(long id) {
        return privilegeMapper.toDto(roleRepository.findPrivilegesByRoleId(findById(id).getId()));
    }

    @Override
    public Role findByName(String name) {
     return roleRepository.findByNameIgnoreCase(name);
    }


    private void isUnique(String name){
       if(roleRepository.existsByNameIgnoreCase(name)){
           throw  new BusinessException(ResponseMessageEnum.BACK_ROLE_MSG_004);
       }
    }


}
