package com.zalisoft.teamapi.service;


import com.zalisoft.teamapi.dto.AuthRequest;
import com.zalisoft.teamapi.dto.AuthToken;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User findOneByEmail(String loginOrEmail);

    User register(UserRegisterDto userRegisterDto, MultipartFile file) throws IOException;

    AuthToken  login(AuthRequest authRequest);

    List<User> findUserByRoles(Role role);

    void assignRoleToUser(long id, long roleId);

    User findById(long id);

    void unAssignRoleToUser(long id, long roleId);

    void updateByAdmin(long id, UserRegisterDto userRegisterDto,MultipartFile file) throws IOException;

    void updateByCurrentUser(UserRegisterDto userRegisterDto, MultipartFile file) throws IOException;

    User findCurrentUser();


    void deleteByAdmin(long id);

    List<User> findUserUnsentReportByCaptainTc(String tc);

    User findByTc(String tc);

    List<User>  findAllByListOfId(List<Long> id);

    boolean checkIfCaptain(long id);

    void changePassword(String newPassword, String oldPassword);


}
