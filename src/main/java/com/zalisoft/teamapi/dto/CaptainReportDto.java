package com.zalisoft.teamapi.dto;


import lombok.Data;

import java.util.List;


@Data
public class CaptainReportDto extends BaseDto{

    private Long id;
    private UserShortDto user;
    private int minutes;
    private List<ReportDto> reports;

}
