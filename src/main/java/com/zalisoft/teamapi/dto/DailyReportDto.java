package com.zalisoft.teamapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class DailyReportDto extends BaseDto{

   private  Long id;
    private  int hours;
    private int minutes;
    private List<ProjectDto>  project;
    private String details;
    private boolean isCompleted;
    private  UserShortDto user;
    private String personLearning;
}
