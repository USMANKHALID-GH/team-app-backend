package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.ReportDto;
import com.zalisoft.teamapi.model.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDto, Report> {
}
