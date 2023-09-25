package com.zalisoft.teamapi.dto;

import lombok.Data;



@Data
public class CaptainReportDto extends BaseDto{

    private Long id;
    private UserShortDto user;
    private Long hours;
    private Long numberOfDays;
    private ProjectShortDto  project;
    private String description;
}
