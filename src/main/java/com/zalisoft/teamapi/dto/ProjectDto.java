package com.zalisoft.teamapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDto  extends BaseDto{

    private String name;
    private LocalDate starting;
    private String description;
    private LocalDate deadline;
    private Double budget;
}
