package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.AuthRequest;
import com.zalisoft.teamapi.dto.AuthToken;
import com.zalisoft.teamapi.dto.UserRegisterDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.enums.UserType;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Role;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.UserRepository;
import com.zalisoft.teamapi.security.jwt.TokenProvider;
import com.zalisoft.teamapi.service.FileSystemService;
import com.zalisoft.teamapi.service.PermissionService;
import com.zalisoft.teamapi.service.RoleService;
import com.zalisoft.teamapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.zalisoft.teamapi.util.SecurityUtils.getCurrentUsername;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private AuthenticationManagerBuilder authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    @Autowired
    private FileSystemService fileSystemService;

    @Override
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new BusinessException(ResponseMessageEnum.BACK_USER_MSG_001)) ;
    }

    @Override
    public User register(UserRegisterDto userRegisterDto,  MultipartFile file) throws IOException {
        User user=new  User();
        if(StringUtils.isEmpty(userRegisterDto.getTc())){
            throw  new BusinessException(ResponseMessageEnum.BACK_USER_MSG_007);
        }
        if(StringUtils.isEmpty(userRegisterDto.getEmail())){
            throw  new BusinessException(ResponseMessageEnum. BACK_USER_MSG_005);
        }
        if(StringUtils.isEmpty(userRegisterDto.getFirstName())){
            throw  new BusinessException(ResponseMessageEnum.BACK_USER_MSG_008);

        }
        if(StringUtils.isEmpty(userRegisterDto.getLastName())){
            throw  new BusinessException(ResponseMessageEnum.BACK_USER_MSG_009);
        }
        if(StringUtils.isEmpty(userRegisterDto.getTitle().name())){
            throw  new BusinessException(ResponseMessageEnum.BACK_USER_MSG_010);

        }
        if (ObjectUtils.isEmpty(Integer.valueOf(userRegisterDto.getExperience()))){
            throw  new BusinessException(ResponseMessageEnum.BACK_USER_MSG_011);

        }

        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            throw  new BusinessException(ResponseMessageEnum.BACK_USER_MSG_003);
        }
        if(userRepository.existsByPhone(userRegisterDto.getPhone())){
            throw  new BusinessException(ResponseMessageEnum. BACK_USER_MSG_002);
        }

        String imageUrl=fileSystemService.saveImage(file);
        log.info("image url: {}", imageUrl);

        user.setActive(true);
        user.setRoles(Set.of(roleService.findByName(UserType.USER.name())));
        user.setEmail(userRegisterDto.getEmail());
        user.setLastName(userRegisterDto.getLastName());
        user.setFirstName(userRegisterDto.getFirstName());
        user.setTc(userRegisterDto.getTc());
        user.setAddress(userRegisterDto.getAddress());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setPhone(userRegisterDto.getPhone());
        user.setImage(userRegisterDto.getImage());
        user.setUserType(UserType.USER);
        user.setAddress(userRegisterDto.getAddress());
        user.setExperience(userRegisterDto.getExperience());
        user.setImage(imageUrl);
        user.setTitle(userRegisterDto.getTitle());
        return userRepository.save(user);



    }

    @Override
    public AuthToken login(AuthRequest authRequest) {
        AuthToken authToken;
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword());

            Authentication authentication=authenticationManager
                    .getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authToken=tokenProvider.createToken(authentication);
        }catch (AuthenticationException e){
            throw  new BusinessException(ResponseMessageEnum.BACK_USER_MSG_06);
        }

        return authToken;
    }

    @Override
    public List<User> findUserByRoles(Role role) {
        return userRepository.findUserByRoles(role);
    }

    @Override
    public void assignRoleToUser(long id, long roleId) {
        User user=findById(id);
        user.getRoles().add(roleService.findById(roleId));
        userRepository.save(user);
    }

    @Override
    public void unAssignRoleToUser(long id, long roleId) {
        User user=findById(id);
        user.getRoles().remove(roleService.findById(roleId));
        userRepository.save(user);

    }

    @Override
    public void updateByAdmin(long id, UserRegisterDto userRegisterDto,MultipartFile file) throws IOException {
        User user=findById(id);
        update(user,userRegisterDto,file);
    }

    @Override
    public void updateByCurrentUser(UserRegisterDto userRegisterDto, MultipartFile file) throws IOException {
        User user= findCurrentUser();
        update(user,userRegisterDto,file);

    }

    @Override
    public User findCurrentUser() {
        String current=getCurrentUsername();
        log.info("user :{}",current);
       return findOneByEmail(current);
    }

    @Override
    public void deleteByAdmin(long id) {
        User user=findById(id);
        fileSystemService.deleteImage(user.getImage());
        userRepository.delete(user);
    }

    @Override
    public List<User> findUserUnsentReportByCaptainTc(String tc) {
        List<User> userWithPermission=permissionService.findUserBetweenDeadlineAndStartingDate();
        List<User> userUnsentReport= userRepository.findUserUnsentReportByCaptainTc(tc);
        userUnsentReport.removeAll(userWithPermission);
        return  userUnsentReport;
    }

    @Override
    public User findByTc(String tc) {
        return userRepository.findByTc(tc)
                .orElseThrow(()->new BusinessException(ResponseMessageEnum.BACK_USER_MSG_001));
    }

    @Override
    public List<User> findAllByListOfId(List<Long> id) {
        return userRepository.findAllById(id);
    }

    @Override
    public boolean checkIfCaptain(long id) {
        return userRepository.checkIfCaptain(id).isPresent();
    }

    @Override
    public void changePassword(String newPassword, String oldPassword) {
       User user= findCurrentUser();
       log.info("user : {}",user.toString());
        if(passwordEncoder.matches(oldPassword,user.getPassword())){
             user.setPassword(passwordEncoder.encode(newPassword));
             userRepository.save(user);
        }

    }


    private void update(User user, UserRegisterDto userRegisterDto,MultipartFile file) throws IOException {
        if(StringUtils.isNotEmpty(userRegisterDto.getTitle().name())){
            user.setTitle(userRegisterDto.getTitle());
        }
        if(StringUtils.isNotEmpty(userRegisterDto.getImage())){
            user.setImage(userRegisterDto.getImage());
        }
        if(StringUtils.isNotEmpty(userRegisterDto.getPhone())){
            user.setPhone(userRegisterDto.getPhone());
        }
        if(ObjectUtils.isNotEmpty(userRegisterDto.getAddress())){
             user.setAddress(userRegisterDto.getAddress());
        }

        if(!ObjectUtils.isEmpty(file)){
            user.setImage(fileSystemService.saveImage(file));
        }
        userRepository.save(user);
    }


    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new BusinessException(ResponseMessageEnum.BACK_USER_MSG_001));
    }
}
