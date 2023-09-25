package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.DailyReportDto;
import com.zalisoft.teamapi.model.DailyReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<DailyReportDto, DailyReport> {
}
