package com.zalisoft.teamapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class DailyReportDto extends BaseDto{

     private  Long id;
    private int minutes;
    private List<ReportDto>  reports;
    private boolean isCompleted;
    private  UserShortDto user;

}
