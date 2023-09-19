package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.CautionDto;
import com.zalisoft.teamapi.model.Caution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CautionMapper extends EntityMapper<CautionDto , Caution> {
}
