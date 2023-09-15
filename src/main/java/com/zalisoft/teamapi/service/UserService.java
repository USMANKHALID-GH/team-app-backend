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

    void unassignRoleToUser(long id, long roleId);

    void updateByAdmin(long id, UserRegisterDto userRegisterDto);

    void update(UserRegisterDto userRegisterDto);

}
