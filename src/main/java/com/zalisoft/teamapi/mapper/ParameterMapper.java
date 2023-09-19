package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.ParameterDto;
import com.zalisoft.teamapi.mapper.EntityMapper;
import com.zalisoft.teamapi.model.Parameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParameterMapper extends EntityMapper<ParameterDto, Parameter> {
}
