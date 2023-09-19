package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.PrivilegeDto;
import com.zalisoft.teamapi.model.Privilege;

import java.util.List;

public  interface PrivilegeService {

    void save(PrivilegeDto privilegeDto);

    Privilege findById(long id);

    Privilege update(long id ,PrivilegeDto privilegeDto );

    void delete(long id);

    Privilege findByName(String name);

    boolean existByName(String name);

    List<Privilege>  findAll();
}
