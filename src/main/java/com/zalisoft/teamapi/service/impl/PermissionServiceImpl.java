package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.PermissionDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Permission;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.PermissionRepository;
import com.zalisoft.teamapi.service.PermissionService;
import com.zalisoft.teamapi.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserService userService;

    @Override
    public void save(PermissionDto permissionDto) {

        if(StringUtils.isEmpty(permissionDto.getReason())){
            throw new BusinessException(ResponseMessageEnum.BACK_PERMISSION_MSG_001);
        }
        if(ObjectUtils.isEmpty(permissionDto.getStarting())){
            throw  new BusinessException(ResponseMessageEnum.BACK_PERMISSION_MSG_002);
        }
        if(ObjectUtils.isEmpty(permissionDto.getExpiryDate())){
            throw  new BusinessException(ResponseMessageEnum.BACK_PERMISSION_MSG_003);
        }
        User user=userService.findCurrentUser();
        Permission permission= new Permission();
        permission.setReason(permissionDto.getReason());
       permission.setStarting(permissionDto.getStarting());
       permission.setExpiryDate(permissionDto.getExpiryDate());
       permission.setIssuedBy(user);
       permissionRepository.save(permission);


    }

    @Override
    public Permission findById(long id) {
//        yet to do
        return permissionRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum. BACK_PERMISSION_MSG_004));
    }

    @Override
    public List<Permission> findByCaptain() {
        return permissionRepository.findPermissionByCaptain(userService.findCurrentUser().getId());
    }

    @Override
    public Page<Permission> findAll(Pageable pageable) {

        return permissionRepository.findAll(pageable);
    }

    @Override
    public void approve(long id) {
        User user=userService.findCurrentUser();
        Permission permission=findById(id);
        if(permissionRepository.checkToApprovePermission(user.getId(),permission.getIssuedBy().getId(),permission.getId()).isPresent()){
            permission.setApproved(true);
            permission.setApprovedBy(user);
            permissionRepository.save(permission);
        }
        else throw new BusinessException(ResponseMessageEnum.BACK_PERMISSION_MSG_005);




    }

    @Override
    public void reject(long id) {
        User user= userService.findCurrentUser();
        Permission permission=findById(id);
        if(permissionRepository.checkToRejectPermission(id,user.getId()).isPresent()){
            permission.setApproved(false);
            permissionRepository.save(permission);
        }
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_PERMISSION_MSG_005);

    }

    @Override
    public List<User> findUserBetweenDeadlineAndStartingDate() {
        LocalDate date= LocalDate.now();
        return permissionRepository.findUserBetweenDeadlineAndStartingDate(date);
    }

    @Override
    public void deleteByCurrentUser(long id) {
        User user=userService.findCurrentUser();
        Permission permission=findById(id);
        if(permission.getIssuedBy().equals(user)){
            permissionRepository.delete(permission);
        }
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_PERMISSION_MSG_006);
    }
}
