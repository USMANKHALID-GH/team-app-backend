package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.constant.ParameterConstant;
import com.zalisoft.teamapi.dto.ParameterDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Parameter;
import com.zalisoft.teamapi.repository.ParameterRepository;
import com.zalisoft.teamapi.service.ParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
@Slf4j
public class ParameterServiceImpl implements ParameterService {


    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public void delete(long id) {
        parameterRepository.delete(findById(id));
    }

    @Override
    public Parameter findByKey(String key) {
        return parameterRepository.findByKey(key)
                .orElseThrow(()->new BusinessException(ResponseMessageEnum. BACK_PARAMETER_MSG_001));
    }

    @Override
    public Parameter save(ParameterDto parameterDto) {

        if(StringUtils.isEmpty(parameterDto.getKey())){
            throw  new BusinessException(ResponseMessageEnum. BACK_PARAMETER_MSG_002);
        }
        if(StringUtils.isEmpty(parameterDto.getDescription())){
            throw new BusinessException(ResponseMessageEnum.BACK_PARAMETER_MSG_003);
        }
        if(StringUtils.isEmpty(parameterDto.getValue())){

        }
         Parameter parameter= new Parameter();
        parameter.setDescription(parameterDto.getDescription());
        parameter.setKey(parameterDto.getKey());
        parameter.setValue(parameterDto.getValue());
        if(StringUtils.isNotEmpty(parameterDto.getValue2())){
            parameter.setValue2(parameterDto.getValue2());
        }
        return  parameterRepository.save(parameter);

    }

    @Override
    public void update(long id, ParameterDto parameterDto) {
        Parameter parameter=findById(id);
        if(!StringUtils.isEmpty(parameterDto.getKey())){
            parameter.setKey(parameterDto.getKey());
        }
        if(!StringUtils.isEmpty(parameterDto.getDescription())){
            parameter.setDescription(parameterDto.getDescription());
        }
        if(!StringUtils.isEmpty(parameterDto.getValue())) {
            parameter.setValue(parameterDto.getValue());
        }
        if(!StringUtils.isEmpty(parameterDto.getValue2())) {
            parameter.setValue2(parameterDto.getValue2());
        }
      parameterRepository.save(parameter);
        }

    @Override
    public List<Parameter> findAll() {
        return parameterRepository.findAll();
    }

    @Override
    public Parameter findById(long id) {
        return parameterRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum.BACK_PARAMETER_MSG_004));
    }


    public int getCautionLimit(){
        Parameter parameter=findByKey(ParameterConstant.CAUTION_LIMIT);
        return  StringUtils.isNotEmpty(parameter.getValue()) ? Integer.parseInt(parameter.getValue()) : 3;
    }

    @Override
    public String getMessageForLimitCaution() {
          Parameter parameter=findByKey(ParameterConstant.SEND_CAUTION_TO_UNSENT_REPORT_BACKEND);
          log.info(" parameter.getDescription() {}", parameter.getDescription());
        return  StringUtils.isNotEmpty(parameter.getDescription()) ? parameter.getDescription() : "you have reached your limit: "+parameter.getValue();
    }

    public Parameter getCautionParameter(){
        return findByKey(ParameterConstant.SEND_CAUTION_TO_UNSENT_REPORT_BACKEND);
    }









}
