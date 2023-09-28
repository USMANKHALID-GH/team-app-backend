package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.CaptainReportDto;
import com.zalisoft.teamapi.dto.CautionDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Caution;
import com.zalisoft.teamapi.model.Parameter;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.CautionRepository;
import com.zalisoft.teamapi.service.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CuationServiceImpl implements CautionService {

    @Autowired
    private CautionRepository cautionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ParameterService parameterService;



    @Override
    public List<Caution> findAll() {
        return cautionRepository.findAll() ;
    }

    @Override
    public void sendCautionToMultipleUsers(List<Long> userList) {

        Caution caution=new Caution();
        Parameter parameter=parameterService.getCautionParameter();
        User user=userService.findCurrentUser();
        caution.setName(parameter.getValue());
        caution.setIssuedUserId(user.getId());
        caution.setUser( userService.findAllByListOfId(userList));
        caution.setMessage(parameter.getDescription());
        cautionRepository.save(caution);



    }

    @Override
    public void sendCautionToSingleUser(CautionDto cautionDto, long userId) {
        User issuedBy=userService.findCurrentUser();
        User to=userService.findById(userId);
        if(StringUtils.isEmpty(cautionDto.getName())){
            throw  new BusinessException(ResponseMessageEnum.BACK_CAUTION_MSG_002);
        }

        if(StringUtils.isEmpty(cautionDto.getMessage())){
            throw new BusinessException(ResponseMessageEnum.BACK_CAUTION_MSG_003);
        }
        Caution caution=new Caution();
        caution.setMessage(cautionDto.getMessage());
        caution.setIssuedUserId(issuedBy.getId());
        caution.setUser(List.of(to));
        caution.setName(cautionDto.getName());
        cautionRepository.save(caution);

    }


    @Override
    public List<Caution> findByCurrentUser() {
        return cautionRepository.findByCurrentUser(userService.findCurrentUser().getId());
    }

    @Override
    public void deleteByCaptain(long id) {
        cautionRepository.delete(findById(id));

    }

    @Override
    public List<User> countUsersWithMoreThanTheGivenOccurrences() {
        List<Long> users=cautionRepository.findUsersWhoExceedCautionLimit(parameterService.getCautionLimit());
        return  userService.findAllByListOfId(users);
    }

    @Override
    public Caution findById(long id) {
        return cautionRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum. BACK_CAUTION_MSG_001));
    }


    @Override
    public void deleteByUserId(List<Long> id) {
        cautionRepository.deleteByUserId(id);
    }


}
