package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.PrivilegeDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Privilege;
import com.zalisoft.teamapi.repository.PrivilegeRepository;
import com.zalisoft.teamapi.service.PrivilegeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public void save(PrivilegeDto privilegeDto) {
        if(StringUtils.isEmpty(privilegeDto.getName())){
            throw new BusinessException(ResponseMessageEnum. BACK_PRIVILEGE_MSG_003);
        }
        if(existByName(privilegeDto.getName())){
            throw new BusinessException(ResponseMessageEnum. BACK_PRIVILEGE_MSG_002);
        }
        Privilege privilege=new Privilege();
        privilege.setName(privilegeDto.getName());
        privilegeRepository.save(privilege);
    }


    @Override
    public Privilege findById(long id) {
        return privilegeRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum. BACK_PRIVILEGE_MSG_002));
    }


    @Override
    public Privilege update(long id, PrivilegeDto privilegeDto) {

        Privilege privilege=findById(id);
        if(StringUtils.isNotEmpty(privilegeDto.getName()) ||
                !existByName(privilegeDto.getName())){

            privilege.setName(privilegeDto.getName());
        }

        return  privilegeRepository.save(privilege);
    }



    @Override
    public void delete(long id) {
       privilegeRepository.deleteById(id);
    }


    @Override
    public Privilege findByName(String name) {
        return privilegeRepository.findByNameIgnoreCase(name)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum. BACK_PRIVILEGE_MSG_001));
    }


    public  boolean  existByName(String name){
       return privilegeRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public List<Privilege> findAll() {
        return privilegeRepository.findAll();
    }
}
