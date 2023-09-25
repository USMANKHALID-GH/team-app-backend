package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.CautionDto;
import com.zalisoft.teamapi.model.Caution;
import com.zalisoft.teamapi.model.User;

import java.util.List;

public interface CautionService {

    List<Caution>  findAll();

    void sendCautionToDailyReport(List<Long> userList);

    void sendPersonalCautionToUser(CautionDto cautionDto, long userId);



    List<Caution>  findByCurrentUser();

    void deleteByCaptain(long id);

    List<User> countUsersWithMoreThanTheGivenOccurrences();

    Caution findById(long id);

    void deleteByUserId(List<Long> id);
 }
