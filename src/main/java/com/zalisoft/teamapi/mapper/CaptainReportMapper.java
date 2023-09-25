package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.CaptainReportDto;
import com.zalisoft.teamapi.model.CaptainReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaptainReportMapper extends EntityMapper<CaptainReportDto, CaptainReport> {
}
