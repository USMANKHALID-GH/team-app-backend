package com.zalisoft.teamapi.dto;

import com.zalisoft.teamapi.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskDto extends BaseDto {
    private  Long id;
    private String name;
    private UserDto user;
    private ProjectDto project;
    private TaskStatus status;
    private LocalDateTime beginning;
    private LocalDate deadline;
    private Long teamId;
    private String description;
}