package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.ParameterDto;
import com.zalisoft.teamapi.model.Parameter;

import java.util.List;

public interface ParameterService {

    void delete(long id);

    Parameter findByKey(String key);

    Parameter save(ParameterDto parameterDto);

    void update(long id, ParameterDto parameterDto);

    List<Parameter>  findAll();

    Parameter findById(long id);

    int getCautionLimit();

    String getMessageForLimitCaution();

    Parameter getCautionParameter();


}
