package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.ReportDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.service.ProjectService;
import com.zalisoft.teamapi.service.ReportService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ProjectService projectService;
    @Override
    public void validateReportToBesave(ReportDto reportDto) {

        if(StringUtils.isEmpty(reportDto.getDetails())){
            throw new BusinessException(ResponseMessageEnum.BACK_DAILY_REPORT_MSG_003);
        }
        if(!ObjectUtils.isEmpty(reportDto.getProjectId())){
            projectService.findById(reportDto.getProjectId());
        }

    }

    @Override
    public ReportDto validateReportForUpdate(ReportDto reportDto) {

        if(ObjectUtils.isNotEmpty(reportDto.getProjectId())){
            projectService.findById(reportDto.getProjectId());

        }
        if (ObjectUtils.isEmpty(reportDto.getDetails())){
            throw new BusinessException(ResponseMessageEnum.BACK_DAILY_REPORT_MSG_003);
        }
        return reportDto;
    }

}
