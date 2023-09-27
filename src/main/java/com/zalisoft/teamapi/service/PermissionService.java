package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.PermissionDto;
import com.zalisoft.teamapi.model.Permission;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PermissionService {

    void save(PermissionDto permissionDto);

    Permission findById(long id);

    List<Permission>  findByCaptain();

    Page<Permission>   findAll(Pageable pageable);

    void approve(long id);

    void reject(long id);

    List<User>  findUserBetweenDeadlineAndStartingDate();


    void deleteByCurrentUser(long id);


}
