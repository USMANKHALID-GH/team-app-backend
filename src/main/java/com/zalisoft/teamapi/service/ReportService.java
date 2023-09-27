package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.ReportDto;

public interface ReportService {

    void validateReportToBesave(ReportDto reportDto);

    ReportDto validateReportForUpdate(ReportDto reportDto);
}
