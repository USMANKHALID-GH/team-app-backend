package com.zalisoft.teamapi.dto;

import com.zalisoft.teamapi.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDto  extends BaseDto{
    private Long id;
    private String name;
    private LocalDate starting;
    private String description;
    private LocalDate deadline;
    private Double budget;
    private ProjectStatus status;
    private UserDto projectManager;
}
