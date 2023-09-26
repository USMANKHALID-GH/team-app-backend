package com.zalisoft.teamapi.service;


import com.zalisoft.teamapi.dto.AuthRequest;
import com.zalisoft.teamapi.dto.AuthToken;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.model.User;

import java.util.List;

public interface UserService {

    User findOneByEmail(String loginOrEmail);

    User register(UserRegisterDto userRegisterDto);

    AuthToken  login(AuthRequest authRequest);

    List<User> findUserByRoles(Role role);

    void assignRoleToUser(long id, long roleId);

    User findById(long id);

    void unAssignRoleToUser(long id, long roleId);

    void updateByAdmin(long id, UserRegisterDto userRegisterDto);

    void update(UserRegisterDto userRegisterDto);

    User findCurrentUser();


    void deleteByAdmin(long id);

    List<User> findUserUnsentReportByCaptainTc(String tc);

    User findByTc(String tc);

    List<User>  findAllByListOfId(List<Long> id);

    boolean checkIfCaptain(long id);

    void changePassword(String newPassword, String oldPassword);


}
