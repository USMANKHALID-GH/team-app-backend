package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Caution;
import com.zalisoft.teamapi.model.Parameter;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.CautionRepository;
import com.zalisoft.teamapi.service.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
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

    @Autowired
    private TeamService teamService;

    @Autowired
    private ReportService reportService;

    @Override
    public List<Caution> findAll() {
        return cautionRepository.findAll() ;
    }

    @Override
    public void save(List<Long> userList) {

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
    public List<Caution> findByCurrentUser() {
        return cautionRepository.findByCurrentUser(userService.findCurrentUser().getId());
    }

    @Override
    public void deleteByCaptain(long id) {
        cautionRepository.delete(findById(id));

    }

    @Override
    public List<User> countUsersWithMoreThanTheGivenOccurrences() {
        return cautionRepository.countUsersWithMoreThan3Caution(Long.parseLong(String.valueOf(parameterService.getCautionLimit())));
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
